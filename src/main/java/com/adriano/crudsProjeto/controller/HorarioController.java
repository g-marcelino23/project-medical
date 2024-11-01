package com.adriano.crudsProjeto.controller;

import com.adriano.crudsProjeto.data.dto.HorarioDTO;
import com.adriano.crudsProjeto.service.HorarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/horario/crud")
public class HorarioController {
    @Autowired
    HorarioService horarioService;
    @PostMapping("/criar")
    public HorarioDTO createHorario(@RequestBody HorarioDTO horario){
        return horarioService.saveHorario(horario);
    }

    @GetMapping("/get/todos")
    public List<HorarioDTO> listAll(){
        return horarioService.listAll();
    }

    @GetMapping("/get/horario/{idHorario}")
    public HorarioDTO findHorario(@PathVariable long idHorario){
        return horarioService.findHorario(idHorario);
    }

    @PatchMapping("/atualizar/{idHorario}")
    public HorarioDTO updateHorario(@PathVariable long idHorario, @RequestBody HorarioDTO horario){
        return horarioService.updateHorario(idHorario, horario);
    }

    @DeleteMapping("/deletar/{idHorario}")
    public void deleteHorario(@PathVariable long idHorario){
        horarioService.deleteHorario(idHorario);
    }

}
