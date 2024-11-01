package com.adriano.crudsProjeto.service;

import com.adriano.crudsProjeto.data.dto.ExameDTO;
import com.adriano.crudsProjeto.data.dto.ExameLowDTO;
import com.adriano.crudsProjeto.data.model.Exame;
import com.adriano.crudsProjeto.dozer.DozerConverter;
import com.adriano.crudsProjeto.exceptions.CommonsExceptionsAgenda;
import com.adriano.crudsProjeto.exceptions.CommonsExceptionsExame;
import com.adriano.crudsProjeto.repository.ConsultaRepository;
import com.adriano.crudsProjeto.repository.ExameRepository;
import com.adriano.crudsProjeto.repository.MedicoRepository;
import com.adriano.crudsProjeto.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExameService {
    @Autowired
    ExameRepository exameRepository;
    @Autowired
    ConsultaRepository consultaRepository;
    @Autowired
    PacienteRepository pacienteRepository;
    @Autowired
    MedicoRepository medicoRepository;

    public ExameDTO saveExame(ExameLowDTO exame){
        var consulta = consultaRepository.findById(exame.getIdConsulta());
        if(consulta.isEmpty()){
            throw new CommonsExceptionsExame(HttpStatus.BAD_REQUEST,
                    "não existe nenhuma consulta com esse id",
                    "exame.service.saveExame"
            );
        }
        var paciente = pacienteRepository.findById(consulta.get().getIdPaciente());
        if(paciente.isEmpty()){
            throw new CommonsExceptionsExame(HttpStatus.BAD_REQUEST,
                    "não existe nenhum paciente com esse id",
                    "exame.service.saveExame"
            );
        }
        var medico = medicoRepository.findById(consulta.get().getIdMedico());
        if(medico.isEmpty()){
            throw new CommonsExceptionsExame(HttpStatus.BAD_REQUEST,
                    "não existe nenhum médico com esse id",
                    "exame.service.saveExame"
            );
        }
        ExameDTO exameDTO = new ExameDTO();
        exameDTO.setConteudo(exame.getConteudo());
        exameDTO.setIdConsulta(exame.getIdConsulta());
        exameDTO.setCpf_paciente(paciente.get().getCpf());
        exameDTO.setNome_paciente(paciente.get().getName());
        exameDTO.setCrm_medico(medico.get().getCrm());
        exameDTO.setNome_medico(medico.get().getName());

        Exame exameEntity =  DozerConverter.parseObject(exameDTO, Exame.class);
        exameRepository.save(exameEntity);

        return DozerConverter.parseObject(exameEntity, ExameDTO.class);

    }

    public List<ExameDTO> listAll(){
        return DozerConverter.parseListObjects(exameRepository.findAll(), ExameDTO.class);
    }

    public ExameDTO updateExame(long idOldExame, ExameDTO newExame){
        var oldExame = exameRepository.findById(idOldExame);
        Exame newExameData = DozerConverter.parseObject(newExame, Exame.class);
        if(oldExame.isEmpty()){
            throw new CommonsExceptionsAgenda(HttpStatus.BAD_REQUEST,
                    "o exame que você buscou não foi encontrado no banco",
                    "exame.service.updateExame"
                    );
        }
        oldExame.get().setId(newExameData.getId());
        oldExame.get().setCpf_paciente(newExameData.getCpf_paciente());
        oldExame.get().setNome_paciente(newExameData.getNome_paciente());
        oldExame.get().setCrm_medico(newExameData.getCrm_medico());
        oldExame.get().setNome_medico(newExame.getNome_medico());
        oldExame.get().setConteudo(newExameData.getConteudo());
        exameRepository.save(oldExame.get());
        return DozerConverter.parseObject(oldExame.get(), ExameDTO.class);
    }

//    public ExameDTO findExameById(long idExame){
//        var exame = exameRepository.findById(idExame);
//        if(exame.isEmpty()){
//            throw new CommonsExceptionsExame(HttpStatus.BAD_REQUEST,
//                    "não existe exame com esse id",
//                    "exame.service.findExameById"
//                    );
//        }
//        return DozerConverter.parseObject(exame, ExameDTO.class);
//    }

    public void deleteExame(long idExame){
        var exame = exameRepository.findById(idExame);
        if(exameRepository.findAll().stream().anyMatch(exame1 -> exame1.getId() == idExame)){
            exameRepository.deleteById(idExame);
        }else{
            throw new CommonsExceptionsExame(HttpStatus.BAD_REQUEST,
                    "não existe nenhum exame com o id passado",
                    "exame.service.deleteExame"
            );
        }
    }
}
