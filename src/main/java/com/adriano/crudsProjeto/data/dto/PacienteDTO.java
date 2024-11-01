package com.adriano.crudsProjeto.data.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
@Data
public class PacienteDTO {
    private Long id;
    private String name;
    private String cpf;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate birthDate;
    private String adress;
    private String email;
}
