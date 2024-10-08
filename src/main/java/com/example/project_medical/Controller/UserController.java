package com.example.project_medical.Controller;

import com.example.project_medical.Model.User;
import com.example.project_medical.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/crud")
public class UserController {
    @Autowired
    UserService service;
    @PostMapping("/salvar")
    public User salvarUser(@RequestBody User user){
        return service.salvarUsuario(user);
    }
    @GetMapping("/listar")
    public List listarUsers(){
        return service.listarUsuarios();
    }
   @DeleteMapping("/deletar/{cpf}")
    public void deletarUser(@PathVariable String cpf){
        service.DeleteUser(cpf);
    }
    @PutMapping("/atualizar/{cpf}")
    public void atualizarUser(@RequestBody User user, @PathVariable String cpf){
        service.UpdateUser(user, cpf);
    }
}
