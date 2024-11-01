package com.adriano.crudsProjeto.service;

import com.adriano.crudsProjeto.data.dto.AgendaLowDTO;
import com.adriano.crudsProjeto.data.dto.AgendaResponseDTO;
import com.adriano.crudsProjeto.data.dto.HorarioDTO;
import com.adriano.crudsProjeto.data.model.Agenda;
import com.adriano.crudsProjeto.data.model.Horario;
import com.adriano.crudsProjeto.dozer.DozerConverter;
import com.adriano.crudsProjeto.exceptions.CommonsExceptionsAgenda;
import com.adriano.crudsProjeto.exceptions.CommonsExceptionsHorario;
import com.adriano.crudsProjeto.exceptions.CommonsExceptionsMedicos;
import com.adriano.crudsProjeto.repository.AgendaRepository;
import com.adriano.crudsProjeto.repository.HorarioRepository;
import com.adriano.crudsProjeto.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AgendaService {
    @Autowired
    AgendaRepository agendaRepository;
    @Autowired
    HorarioRepository horarioRepository;
    @Autowired
    MedicoRepository medicoRepository;

    public AgendaResponseDTO saveAgenda(AgendaLowDTO agendaLowDTO){
        Agenda agenda = new Agenda();
        var medico = medicoRepository.findById(agendaLowDTO.getIdMedico());
        if(medico.isEmpty()){
            throw new CommonsExceptionsMedicos(HttpStatus.BAD_REQUEST,
                    "não existe nenhum médico com esse id",
                    "agenda.service.saveAgenda"
            );
        }
        agenda.setCrmMedico(medico.get().getCrm());
        agenda.setNomeMedico(medico.get().getName());
        agendaRepository.save(agenda);
        return DozerConverter.parseObject(agenda, AgendaResponseDTO.class);
    }

    public List<AgendaResponseDTO> listAll(){
        return DozerConverter.parseListObjects(agendaRepository.findAll(), AgendaResponseDTO.class);
    }

    public AgendaResponseDTO findAgenda(long idAgenda){
        if(agendaRepository.findById(idAgenda).isEmpty()){
            throw new CommonsExceptionsAgenda(HttpStatus.BAD_REQUEST,
                    "não existe nenhuma agenda com esse id no banco",
                    "agenda.service.findAgenda"
                    );
        }
        return DozerConverter.parseObject(agendaRepository.findById(idAgenda).get(), AgendaResponseDTO.class);
    }

    public AgendaResponseDTO updateAgenda(Long idAgenda, AgendaLowDTO agenda){
        var oldAgenda = agendaRepository.findById(idAgenda);
        if(oldAgenda.isEmpty()){
            throw new CommonsExceptionsAgenda(HttpStatus.BAD_REQUEST,
                    "não existe nenhuma agenda com o id passado",
                    "agenda.service.updateAgenda"
            );
        }
        var medico = medicoRepository.findById(agenda.getIdMedico());
        if(medico.isEmpty()){
            throw new CommonsExceptionsAgenda(HttpStatus.BAD_REQUEST,
                    "não existe nenhum médico com o id passado",
                    "agenda.service.updateAgenda"
                    );
        }
        oldAgenda.get().setNomeMedico(medico.get().getName());
        oldAgenda.get().setCrmMedico(medico.get().getCrm());
        return DozerConverter.parseObject(oldAgenda, AgendaResponseDTO.class);
    }

    public void deleteAgenda(long idAgenda){
        if(agendaRepository.findAll().stream().anyMatch(agenda -> agenda.getId()==idAgenda)){
            agendaRepository.deleteById(idAgenda);
        }else{
            throw new CommonsExceptionsAgenda(HttpStatus.BAD_REQUEST,
                    "não existe nenhuma agenda com esse id no banco",
                    "agenda.service.deleteAgenda"
            );
        }

    }

    public List<HorarioDTO> horariosDisponiveis(long idAgenda){
       var entity = agendaRepository.findById(idAgenda);
       if(entity.isEmpty()){
           throw new CommonsExceptionsAgenda(HttpStatus.BAD_REQUEST,
                   "não existe nenhuma agenda com esse id no banco",
                   "agenda.service.horariosDisponiveis"
                   );
       }
       List<Horario> listHorarios = horarioRepository.findAll().stream().filter(horario -> horario.getIdAgenda() ==entity.get().getId()).toList();
       List<Horario> listHorariosDisponiveis = listHorarios.stream().filter(Horario::getDisponivel).toList();
        if(listHorariosDisponiveis.isEmpty()){
            throw new CommonsExceptionsAgenda(HttpStatus.BAD_REQUEST,
                    "não há horários disponíveis nessa agenda",
                    "agenda.service.horariosDisponiveis"
            );
        }
       return DozerConverter.parseListObjects(listHorariosDisponiveis, HorarioDTO.class);
    }
}

