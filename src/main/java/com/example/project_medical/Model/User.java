package com.example.project_medical.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class User {
    private String name;
    @Id
    private String cpf;
    public User(){}
    public User(String name, String cpf) {
        this.name = name;
        this.cpf = cpf;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
