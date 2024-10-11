package com.adriano.crudsProjeto.service;

import com.adriano.crudsProjeto.data.dto.PacienteDTO;
import com.adriano.crudsProjeto.data.model.Paciente;
import com.adriano.crudsProjeto.dozer.DozerConverter;
import com.adriano.crudsProjeto.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {
    @Autowired
    PacienteRepository repository;
    //create paciente
    public PacienteDTO savePaciente(PacienteDTO paciente){
        if(paciente.getCpf().length()>11){
            System.out.println("CPF inválido");
            return null;
        }
        if(paciente.getContact().length()>11){
            System.out.println("Contato inválido");
            return null;
        }
        var entity = DozerConverter.parseObject(paciente, Paciente.class);
        var entityDTO = repository.save(entity);
        return DozerConverter.parseObject(entityDTO, PacienteDTO.class);
    }
    //read paciente
    public List<PacienteDTO> listAll(){
        return DozerConverter.parseListObjects(
                repository.findAll(), PacienteDTO.class
        );
    }
    //update paciente
    public PacienteDTO updatePaciente(long id, PacienteDTO paciente) {
        var newPaciente = DozerConverter.parseObject(paciente, Paciente.class);
        Paciente oldPaciente = repository.findById(id).get();
        oldPaciente.setName(newPaciente.getName());
        oldPaciente.setAdress(newPaciente.getAdress());
        oldPaciente.setContact(newPaciente.getContact());
        oldPaciente.setBirthDate(newPaciente.getBirthDate());
        repository.save(oldPaciente);
        return DozerConverter.parseObject(oldPaciente, PacienteDTO.class);
    }

    //delete Paciente
    public void deletePaciente(long id){
        repository.deleteById(id);
    }

    //pesquisando por um paciente baseado no ID dele
    public PacienteDTO findPaciente(long id){
        Paciente paciente = repository.findById(id).get();
        return DozerConverter.parseObject(paciente, PacienteDTO.class);
    }

}
