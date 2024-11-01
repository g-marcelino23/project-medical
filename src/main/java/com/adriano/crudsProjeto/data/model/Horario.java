package com.adriano.crudsProjeto.data.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Data
public class Horario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    @Column(nullable = false)
    private LocalDateTime dataHorario;
    private Boolean disponivel = true;
    private Long idAgenda;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Horario horario = (Horario) o;
        return Objects.equals(dataHorario, horario.dataHorario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dataHorario);
    }
}
