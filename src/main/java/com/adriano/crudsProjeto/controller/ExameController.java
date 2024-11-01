package com.adriano.crudsProjeto.controller;

import com.adriano.crudsProjeto.data.dto.ExameDTO;
import com.adriano.crudsProjeto.data.dto.ExameLowDTO;
import com.adriano.crudsProjeto.service.ExameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exame/crud")
public class ExameController {
    @Autowired
    ExameService exameService;
    @PostMapping(value = "/criar/exame", consumes = "application/json")
    public ExameDTO addExame(@RequestBody ExameLowDTO exame){
        return exameService.saveExame(exame);
    }

    @GetMapping("/get/todos/exames")
    public List<ExameDTO> allExames(){
        return exameService.listAll();
    }

    @DeleteMapping("/delete/exame/{idExame}")
    public void deleteExameById(@PathVariable long idExame){
        exameService.deleteExame(idExame);
    }

    @PatchMapping("/update/exame/{idExame}")
    public ExameDTO updateDataExame(@PathVariable long idExame, @RequestBody ExameDTO exame){
        return exameService.updateExame(idExame, exame);
    }
}
