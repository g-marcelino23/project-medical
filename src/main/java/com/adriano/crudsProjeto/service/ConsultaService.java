package com.adriano.crudsProjeto.service;

import com.adriano.crudsProjeto.data.dto.*;
import com.adriano.crudsProjeto.data.model.*;
import com.adriano.crudsProjeto.dozer.DozerConverter;
import com.adriano.crudsProjeto.exceptions.*;
import com.adriano.crudsProjeto.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.boot.archive.scan.spi.PackageInfoArchiveEntryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class ConsultaService {
    @Autowired
    ConsultaRepository consultaRepository;
    @Autowired
    MedicoRepository medicoRepository;
    @Autowired
    ExameRepository exameRepository;
    @Autowired
    AgendaRepository agendaRepository;
    @Autowired
    HorarioRepository horarioRepository;
    @Autowired
    EmailService emailService;
    @Autowired
    PacienteRepository pacienteRepository;
    public ConsultaDTO saveConsulta(ConsultaDTO consultaDTO) {
        // Converter DTO para entidade Consulta
        var entity = DozerConverter.parseObject(consultaDTO, Consulta.class);
        var dataConsulta = entity.getDataConsulta();
        var medico = medicoRepository.findById(entity.getIdMedico());
        if (medico.isEmpty()) {
            throw new CommonsExceptionsMedicos(HttpStatus.BAD_REQUEST,
                    "não existe nenhum médico com o id passado",
                    "consulta.service.saveConsulta"
            );
        }
        var horariosAgenda = horarioRepository.findAll().stream().filter(horario -> Objects.equals(horario.getIdAgenda(), medico.get().getIdAgenda())).toList();
        if (horariosAgenda.isEmpty()) {
            throw new CommonsExceptionsAgenda(HttpStatus.BAD_REQUEST,
                    "não existe nehuma agenda com o id passado",
                    "consulta.service.saveAgenda"
            );
        }
        if (horariosAgenda.stream().anyMatch(horario -> horario.getDataHorario().equals(dataConsulta))) {
            var horarioAgenda = horariosAgenda.stream().filter(horario -> horario.getDataHorario().equals(dataConsulta)).findFirst();
            consultaRepository.save(entity);
            horarioAgenda.get().setDisponivel(false);
            horarioRepository.save(horarioAgenda.get());

        } else {
            throw new CommonsExceptionsConsulta(HttpStatus.BAD_REQUEST,
                    "o horário escolhido não corresponde com nenhum horário da agenda do médico",
                    "consulta.service.saveConsulta"
            );
        }
        var paciente = pacienteRepository.findById(entity.getIdPaciente());
        if(paciente.isEmpty()){
            throw new CommonsExceptionsPacientes(HttpStatus.BAD_REQUEST,
                    "não existe nehum paciente com esse id no banco",
                    "consulta.service.saveConsulta"
                    );
        }
        String bodyEmail = "Olá sr(a) "+paciente.get().getName()+" sua consulta com o dr(a) "+medico.get().getName()+ " "+ medico.get().getEspecialidade()+" foi marcada com sucesso!";
        String subjectEmail = "Consulta marcada com sucesso";
        emailService.mandarLembrete(entity.getId(),bodyEmail, subjectEmail);
        return DozerConverter.parseObject(entity, ConsultaDTO.class);
    }

    public void deleteConsulta(long idConsulta) {
        var consulta = consultaRepository.findById(idConsulta);
        if (consulta.isEmpty()) {
            throw new CommonsExceptionsConsulta(HttpStatus.BAD_REQUEST,
                    "não existe nenhuma consulta com o id passado",
                    "consulta.service.deleteConsulta"
            );
        } else {
            var medico = medicoRepository.findById(consulta.get().getIdMedico());
            if (medico.isEmpty()) {
                throw new CommonsExceptionsMedicos(HttpStatus.BAD_REQUEST,
                        "não existe nenhum médico com esse id",
                        "consulta.service.deleteConsulta"
                );
            }
            var agenda = agendaRepository.findById(medico.get().getIdAgenda());
            if (agenda.isEmpty()) {
                throw new CommonsExceptionsAgenda(HttpStatus.BAD_REQUEST,
                        "não existe nenhuma agenda com esse id",
                        "consulta.service.deleteConsulta"
                );
            }
            var horarioConsulta = horarioRepository.findAll().stream().filter(horario -> horario.getIdAgenda().equals(agenda.get().getId())).findFirst();
            if (horarioConsulta.isEmpty()) {
                throw new CommonsExceptionsHorario(HttpStatus.BAD_REQUEST,
                        "não existe nenhum horário com esse id",
                        "consulta.service.deleteConsulta"
                );
            }
            horarioConsulta.get().setDisponivel(true);
            horarioRepository.save(horarioConsulta.get());
        }

        var paciente = pacienteRepository.findById(consulta.get().getIdPaciente());
        if (paciente.isEmpty()) {
            throw new CommonsExceptionsPacientes(HttpStatus.BAD_REQUEST,
                    "não existe nenhum paciente com esse id",
                    "consulta.service.deleteConsulta"
            );
        }

        var medico = medicoRepository.findById(consulta.get().getIdMedico());
        if (medico.isEmpty()) {
            throw new CommonsExceptionsMedicos(HttpStatus.BAD_REQUEST,
                    "não existe nenhum médico com esse id",
                    "consulta.service.deleteConsulta"
            );
        }
        String bodyEmail = "Olá sr(a) "+ paciente.get().getName()+" sua consulta com o dr(a) "+ medico.get().getName()+" do dia"+ consulta.get().getDataConsulta()+" foi desmarcada com sucesso!";
        String subjectEmail = "Sua consulta foi desmarcada com sucesso!";
        emailService.mandarLembrete(idConsulta, bodyEmail, subjectEmail);
        var listExames = exameRepository.findAll().stream().filter(exame -> exame.getIdConsulta() == idConsulta).toList();
        listExames.forEach(exame -> exameRepository.deleteById(exame.getId()));
        consultaRepository.deleteById(idConsulta);

    }

    public List<ConsultaDTO> listAll() {
        return DozerConverter.parseListObjects(consultaRepository.findAll(), ConsultaDTO.class);
    }

    public ConsultaDTO findConsulta(long id) {
        var entity = consultaRepository.findById(id);
        return DozerConverter.parseObject(entity.get(), ConsultaDTO.class);
    }

    public ConsultaDTO updateConsulta(long idOldConsulta, ConsultaDTO consulta) {
        Consulta newConsulta = DozerConverter.parseObject(consulta, Consulta.class);
        Consulta oldConsulta = consultaRepository.findById(idOldConsulta).get();
        oldConsulta.setDataConsulta(newConsulta.getDataConsulta());
        oldConsulta.setIdMedico(newConsulta.getIdMedico());
        oldConsulta.setIdPaciente(newConsulta.getIdPaciente());
        oldConsulta.setMotivo(newConsulta.getMotivo());
        consultaRepository.save(oldConsulta);
        return DozerConverter.parseObject(oldConsulta, ConsultaDTO.class);
    }

    public List<ConsultaDTO> consultasPaciente(long idPaciente) {

        var consultasPaciente = consultaRepository.findAll().stream().filter(consulta -> consulta.getIdPaciente() == idPaciente).toList();
        if (consultasPaciente.isEmpty()) {
            throw new CommonsExceptionsConsulta(HttpStatus.BAD_REQUEST,
                    "não existe nenhum cliente com o id passado",
                    "consulta.service.consultasPaciente"
            );
        }
        return DozerConverter.parseListObjects(consultasPaciente, ConsultaDTO.class);
    }

    public List<ExameDTO> examesConsulta(long idConsulta) {
        var consulta = consultaRepository.findById(idConsulta);
        if (consulta.isEmpty()) {
            throw new CommonsExceptionsConsulta(HttpStatus.BAD_REQUEST,
                    "não existe nenhuma consulta no banco com esse id",
                    "consulta.service.examesConsulta"
            );
        }
        var examesLista = exameRepository.findAll().stream().filter(exame -> exame.getIdConsulta() == idConsulta).toList();
        return DozerConverter.parseListObjects(examesLista, ExameDTO.class);
    }

    public void consultasAmanha(){
        var diaDataAtual = LocalDateTime.now().getDayOfYear();

        var ConsultasAmanha = consultaRepository.findAll().stream().filter(consulta -> consulta.getDataConsulta().getDayOfYear() - diaDataAtual == 1).toList();
        for(Consulta c: ConsultasAmanha){
            var paciente = pacienteRepository.findById(c.getIdPaciente());
            if(paciente.isEmpty()){
                throw new CommonsExceptionsPacientes(HttpStatus.BAD_REQUEST,
                        "não existe nenhum paciente com esse id no banco",
                        "consulta.service.consultasAmanha"
                        );
            }
            var medico = medicoRepository.findById(c.getIdMedico());
            if(medico.isEmpty()){
                throw new CommonsExceptionsMedicos(HttpStatus.BAD_REQUEST,
                        "não existe nenhum médico com esse id no banco",
                        "consulta.service.consultasAmanha"
                        );
            }
            String bodyEmail = "Olá sr(a) "+paciente.get().getName()+" sua consulta com o dr(a) "+medico.get().getName()+" está próxima! Já é para amanhã. Data da consulta: "+ c.getDataConsulta();
            String subject = "Sua consulta está próxima!";
            emailService.mandarLembrete(c.getId(), bodyEmail, subject);
        }
        //return DozerConverter.parseListObjects(ConsultasAmanha, ConsultaDTO.class);
    }

}
