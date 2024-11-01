package com.adriano.crudsProjeto.data.dto;

import com.adriano.crudsProjeto.data.model.Exame;
import com.adriano.crudsProjeto.data.model.Medico;
import com.adriano.crudsProjeto.data.model.Paciente;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.swing.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
public class ConsultaDTO {
    private Long id;
    private Long idPaciente;
    private Long idMedico;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataConsulta;
    private String motivo;
}
