package com.adriano.crudsProjeto.data.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
public class Paciente {
    //SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(nullable = false, length = 11)
    private String cpf;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    private String adress;
    @Column(length = 11)
    private String contact;

}
