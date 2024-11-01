package com.adriano.crudsProjeto.service;

import com.adriano.crudsProjeto.data.dto.HorarioDTO;
import com.adriano.crudsProjeto.data.model.Horario;
import com.adriano.crudsProjeto.dozer.DozerConverter;
import com.adriano.crudsProjeto.exceptions.CommonsExceptionsHorario;
import com.adriano.crudsProjeto.repository.HorarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HorarioService {
    @Autowired
    private HorarioRepository horarioRepository;

    public HorarioDTO saveHorario(HorarioDTO horario){
        Horario entity = DozerConverter.parseObject(horario, Horario.class);
        if(entity.getDataHorario()==null){
            throw new CommonsExceptionsHorario(HttpStatus.BAD_REQUEST,
                    "o Horário não pode ser nulo",
                    "horario.service.saveHorario"
                    );
        }
        horarioRepository.save(entity);
        return DozerConverter.parseObject(entity, HorarioDTO.class);
    }

    public List<HorarioDTO> listAll(){
        return DozerConverter.parseListObjects(horarioRepository.findAll(), HorarioDTO.class);
    }

    public HorarioDTO findHorario(long idHorario){
        var entity = horarioRepository.findById(idHorario);
        if(entity.isEmpty()){
            throw new CommonsExceptionsHorario(HttpStatus.BAD_REQUEST,
                    "Não existe nenhum horário com esse id",
                    "horario.service.findHorario"
                    );
        }
        return DozerConverter.parseObject(entity.get(), HorarioDTO.class);
    }

    public HorarioDTO updateHorario(long idHorario, HorarioDTO horario){
        var oldHorario = horarioRepository.findById(idHorario);
        if(oldHorario.isEmpty()){
            throw new CommonsExceptionsHorario(HttpStatus.BAD_REQUEST,
                    "não existe horário com esse id no banco",
                    "horario.service.updateHorario"
                    );
        }
        var oldHorarioCheck = oldHorario.get();
        var newHorario = DozerConverter.parseObject(horario, Horario.class);
        oldHorarioCheck.setDataHorario(newHorario.getDataHorario());
        oldHorarioCheck.setDisponivel(newHorario.getDisponivel());
        return DozerConverter.parseObject(oldHorario, HorarioDTO.class);
    }

    public void deleteHorario(Long idHorario){
        if(horarioRepository.findAll().stream().anyMatch(horarios->horarios.getId() == idHorario)){
            horarioRepository.deleteById(idHorario);
        }else{
            throw new CommonsExceptionsHorario(HttpStatus.BAD_REQUEST,
                    "não existe horário com esse id no banco",
                    "horario.service.deleteHorario"
                    );
        }

    }
    

}
