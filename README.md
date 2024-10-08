# Sistema de Gestão de Consultório Médico

## Descrição do Projeto
O **Sistema de Gestão de Consultório Médico** tem como objetivo automatizar e simplificar a administração de um consultório médico, permitindo o gerenciamento eficiente de **pacientes**, **médicos**, **consultas**, **exames** e **agendamentos**. A aplicação também inclui uma funcionalidade extra de **envio automático de lembretes** para os pacientes sobre suas consultas agendadas.

Atualmente, o projeto está em andamento e novas funcionalidades estão sendo implementadas.

## Funcionalidades Principais
O sistema inclui as seguintes funcionalidades principais:

1. **Cadastro e Gerenciamento de Pacientes**: Permite o cadastro, edição, visualização e exclusão dos dados dos pacientes, como nome, CPF, data de nascimento, telefone e endereço.
   
2. **Cadastro e Gerenciamento de Médicos**: Permite o cadastro e manutenção dos dados dos médicos, como nome, CRM, especialidade e telefone.

3. **Agendamento de Consultas**: 
   - Possibilita a marcação de consultas entre pacientes e médicos, verificando a disponibilidade do médico em horários específicos.
   - Se o horário solicitado estiver indisponível, o sistema sugere dias alternativos no mês atual em que o médico tem disponibilidade.
   
4. **Gestão de Exames**: Cadastro e gerenciamento dos exames realizados, associados a uma consulta específica. Inclui o registro do nome do exame, data de realização e resultado.

5. **Agenda de Consultas**: Exibição dos horários disponíveis e ocupados para cada médico, com possibilidade de marcar, editar ou cancelar consultas.

6. **Funcionalidade Extra - Envio Automático de Lembretes**:
   - O sistema envia **lembretes automáticos** por e-mail ou SMS para os pacientes 24 horas antes de suas consultas.
   - Esse processo é gerenciado por uma rotina agendada (Scheduler), que verifica diariamente as consultas marcadas para o próximo dia e dispara os lembretes.
   - A implementação desta funcionalidade tem como objetivo reduzir faltas e garantir que os pacientes estejam cientes de suas consultas.

## Funcionamento do Sistema

- **Paciente seleciona um horário**: O paciente pode ver os horários disponíveis de um médico e marcar uma consulta. Caso o horário esteja ocupado, o sistema sugere alternativas dentro do mesmo mês.
  
- **Consulta confirmada**: Após a confirmação da consulta, o sistema salva as informações no banco de dados e atualiza a agenda do médico.
  
- **Envio de lembrete**: Automaticamente, 24 horas antes da consulta, o paciente recebe um lembrete por e-mail ou SMS com os detalhes da consulta.
  
- **Gerenciamento de consultas e exames**: O consultório pode gerenciar a agenda e os exames realizados, mantendo um histórico de atendimento.

## Tecnologias Utilizadas

O projeto está sendo desenvolvido utilizando as seguintes tecnologias:

- **Linguagem**: Java
- **Framework de Backend**: Spring Boot
- **Spring Data JPA** para integração com banco de dados
- **Spring MVC** para roteamento e controle das requisições
- **Spring Security** (a ser implementado) para autenticação e controle de acesso
- **Spring Scheduler** para agendamento de tarefas (envio de lembretes)
- **Banco de Dados**: PostgreSQL, gerenciado via **JPA** e com **Migrations** utilizando Flyway
- **Documentação da API**: Swagger para documentar todas as rotas e endpoints
- **Ferramentas de Notificação**: Integração com APIs de envio de SMS (como **Twilio**) para os lembretes automáticos
- **Gerenciamento de Dependências**: Maven
- **Versionamento de Código**: Git/GitHub para controle de versão e colaboração
- **React**: framework para a interface
- **Axios**: para consumir a API's REST
- **Bootstrap**: parPara estilização e responsividade
- **React Router**: Para navegação entre páginas


