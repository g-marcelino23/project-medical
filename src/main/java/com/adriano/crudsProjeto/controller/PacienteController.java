package com.adriano.crudsProjeto.controller;

import com.adriano.crudsProjeto.data.dto.PacienteDTO;
import com.adriano.crudsProjeto.dozer.DozerConverter;
import com.adriano.crudsProjeto.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/paciente/crud")
public class PacienteController {
    @Autowired
    PacienteService service;

    @PostMapping("/addPaciente")
    public PacienteDTO addPaciente(@RequestBody PacienteDTO paciente){
        return service.savePaciente(paciente);
    }

    @GetMapping("/getAllPacientes")
    public List<PacienteDTO> allPacientes(){
        return service.listAll();
    }

    @DeleteMapping("/deletePaciente/{id}")
    public void deletePaciente(@PathVariable long id){
        service.deletePaciente(id);
    }

    @PatchMapping("/updatePaciente/{id}")
    public PacienteDTO updatePaciente(@PathVariable long id, @RequestBody PacienteDTO newPaciente){
        return service.updatePaciente(id, newPaciente);
    }

    @GetMapping("/getPaciente/{id}")
    public PacienteDTO getPaciente(@PathVariable long id){
        return service.findPaciente(id);
    }
}
