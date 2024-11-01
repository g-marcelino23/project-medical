package com.adriano.crudsProjeto.data.dto;

import com.adriano.crudsProjeto.data.model.Consulta;
import lombok.Data;

@Data
public class ExameDTO {
    private String nome_medico;
    private String crm_medico;
    private String nome_paciente;
    private String cpf_paciente;
    private String conteudo;
    private Long idConsulta;
}
