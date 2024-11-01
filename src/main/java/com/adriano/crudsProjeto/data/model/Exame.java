package com.adriano.crudsProjeto.data.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class Exame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome_paciente;
    @Column(nullable = false, length = 11)
    private String cpf_paciente;
    private String nome_medico;
    @Column(nullable = false, length = 9)
    private String crm_medico;
    private String conteudo;
    private Long idConsulta;
}
