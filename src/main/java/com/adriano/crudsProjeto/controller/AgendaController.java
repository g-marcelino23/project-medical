package com.adriano.crudsProjeto.controller;

import com.adriano.crudsProjeto.data.dto.AgendaLowDTO;
import com.adriano.crudsProjeto.data.dto.AgendaResponseDTO;
import com.adriano.crudsProjeto.data.dto.HorarioDTO;
import com.adriano.crudsProjeto.data.model.Agenda;
import com.adriano.crudsProjeto.service.AgendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agenda/crud")
public class AgendaController {
    @Autowired
    AgendaService agendaService;

    @PostMapping("/criar")
    public AgendaResponseDTO createAgenda(AgendaLowDTO agendaLowDTO){
        return agendaService.saveAgenda(agendaLowDTO);
    }

    @GetMapping("/get/todos")
    public List<AgendaResponseDTO> listAll(){
        return agendaService.listAll();
    }

    @GetMapping("/get/agenda/{idAgenda}")
    public AgendaResponseDTO findAgenda(@PathVariable long idAgenda){
        return agendaService.findAgenda(idAgenda);
    }

    @PatchMapping("/atualizar/agenda/{idAgenda}")
    public AgendaResponseDTO updateAgenda(@PathVariable long idAgenda, @RequestBody AgendaLowDTO agenda){
        return agendaService.updateAgenda(idAgenda, agenda);
    }

    @DeleteMapping("/deletar/agenda/{idAgenda}")
    public void deleteAgenda(@PathVariable long idAgenda){
        agendaService.deleteAgenda(idAgenda);
    }


    @GetMapping("/horarios/disponiveis/{idAgenda}")
    public List<HorarioDTO> horariosDisponiveis(@PathVariable long idAgenda){
        return agendaService.horariosDisponiveis(idAgenda);
    }
}
