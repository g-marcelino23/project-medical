package com.adriano.crudsProjeto.service;

import com.adriano.crudsProjeto.data.dto.AgendaResponseDTO;
import com.adriano.crudsProjeto.data.dto.MedicoDTO;
import com.adriano.crudsProjeto.data.model.Medico;
import com.adriano.crudsProjeto.dozer.DozerConverter;
import com.adriano.crudsProjeto.exceptions.CommonsExceptionsAgenda;
import com.adriano.crudsProjeto.exceptions.CommonsExceptionsMedicos;
import com.adriano.crudsProjeto.repository.AgendaRepository;
import com.adriano.crudsProjeto.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicoService {
    @Autowired
    MedicoRepository medicoRepository;
    @Autowired
    AgendaRepository agendaRepository;
    //create medico
    public MedicoDTO saveMedico(MedicoDTO medico){
        if(medico.getCrm().length() != 9){
            throw new CommonsExceptionsMedicos(
                    HttpStatus.BAD_REQUEST,
                    "você digitou um crm ou com mais de 9 caracteres ou com menos",
                    "crudMedico.service.badRequest"
            );
        }

        if(medico.getContact().length() != 11){
            throw new CommonsExceptionsMedicos(
                    HttpStatus.BAD_REQUEST,
                    "você digitou um telefone ou com mais de 11 caracteres ou com menos",
                    "crudMedico.service.badRequest"
            );
        }
        if(medico.getName().length()>150){
            throw new CommonsExceptionsMedicos(HttpStatus.BAD_REQUEST,
                    "você passou do limite de caracteres permitidos para nomes",
                    "crudMedico.service.badRequest");
        }
        var entity = DozerConverter.parseObject(medico, Medico.class);
        var entityDTO = medicoRepository.save(entity);
        return DozerConverter.parseObject(entityDTO, MedicoDTO.class);
    }


    //read medico
    public List<MedicoDTO> listAll(){
        return DozerConverter.parseListObjects(
                medicoRepository.findAll(), MedicoDTO.class
        );
    }


    //update medico
    public MedicoDTO updateMedico(long idOldMedico, MedicoDTO medicoDTO) {
        var newMedico = DozerConverter.parseObject(medicoDTO, Medico.class);
        Medico oldMedico = medicoRepository.findById(idOldMedico).get();
        oldMedico.setName(newMedico.getName());
        oldMedico.setContact(newMedico.getContact());
        oldMedico.setEspecialidade(newMedico.getEspecialidade());
        oldMedico.setCrm(newMedico.getCrm());
        medicoRepository.save(oldMedico);
        return DozerConverter.parseObject(oldMedico, MedicoDTO.class);
    }

    //delete medico
    public void deleteMedico(long id){
        medicoRepository.deleteById(id);
    }


    //encontrar medico por id
    public MedicoDTO findMedico(long id){
        var entity = medicoRepository.findById(id);
        if(entity.isEmpty()){
            throw new CommonsExceptionsMedicos(HttpStatus.NOT_FOUND,
                    "Médico não encontrado no banco",
                    "crud.medico.notfound");
        }
        return DozerConverter.parseObject(entity.get(), MedicoDTO.class);
    }

    //associar uma Agenda ao médico
    public void addAgendaMedico(long idAgenda, long idMedico){
        var agenda = agendaRepository.findById(idAgenda);
        if(agenda.isEmpty()){
            throw new CommonsExceptionsMedicos(HttpStatus.BAD_REQUEST,
                    "não existe nenhuma agenda com esse id no banco",
                    "medico.service.agendaMedico"
                    );
        }

        var medico = medicoRepository.findById(idMedico);
        if(medico.isEmpty()){
            throw new CommonsExceptionsMedicos(HttpStatus.BAD_REQUEST,
                    "não existe nenhum médico com esse id no banco",
                    "medico.service.agendaMedico"
            );
        }
        medico.get().setIdAgenda(agenda.get().getId());
        medicoRepository.save(medico.get());
    }

    //recuperar uma agenda que está associada a um médico
    public AgendaResponseDTO agendaMedico(long idMedico){
        var medico = medicoRepository.findById(idMedico);
        if(medico.isEmpty()){
            throw new CommonsExceptionsMedicos(HttpStatus.BAD_REQUEST,
                    "não existe nenhum médico com esse id",
                    "medico.service.agendaMedico"
                    );
        }
        var agendaMedico = agendaRepository.findById(medico.get().getIdAgenda());
        if(agendaMedico.isEmpty()){
            throw new CommonsExceptionsAgenda(HttpStatus.BAD_REQUEST,
                    "não existe nehuma agenda com esse id",
                    "medico.service.agendaMedico"
            );
        }
        return DozerConverter.parseObject(agendaMedico.get(), AgendaResponseDTO.class);
    }
}
