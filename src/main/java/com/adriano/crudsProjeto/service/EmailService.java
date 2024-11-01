package com.adriano.crudsProjeto.service;

import com.adriano.crudsProjeto.data.model.Consulta;
import com.adriano.crudsProjeto.data.model.Email;
import com.adriano.crudsProjeto.exceptions.CommonsExceptionsConsulta;
import com.adriano.crudsProjeto.exceptions.CommonsExceptionsMedicos;
import com.adriano.crudsProjeto.exceptions.CommonsExceptionsPacientes;
import com.adriano.crudsProjeto.repository.ConsultaRepository;
import com.adriano.crudsProjeto.repository.MedicoRepository;
import com.adriano.crudsProjeto.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    ConsultaRepository consultaRepository;
    @Autowired
    MedicoRepository medicoRepository;
    @Autowired
    PacienteRepository pacienteRepository;
   private final JavaMailSender mailSender;
   public EmailService(JavaMailSender mailSender){
       this.mailSender = mailSender;
   }

   private void sendEmail(Email email){
        var message = new SimpleMailMessage();
       message.setTo(email.to());
       message.setSubject(email.subject());
       message.setText(email.body());
       mailSender.send(message);

   }

    public void mandarLembrete(long idConsulta, String bodyEmail, String subject) {
        var consulta = consultaRepository.findById(idConsulta);
        if(consulta.isEmpty()){
            throw new CommonsExceptionsConsulta(HttpStatus.BAD_REQUEST,
                    "não existe nenhuma consulta com esse id no banco",
                    "consulta.service.mandarLembrete"
            );
        }
        var medico = medicoRepository.findById(consulta.get().getIdMedico());
        if(medico.isEmpty()){
            throw new CommonsExceptionsMedicos(HttpStatus.BAD_REQUEST,
                    "não existe nenhum médico com esse id no banco",
                    "consulta.service.mandarLembrete"
            );
        }

        var paciente = pacienteRepository.findById(consulta.get().getIdPaciente());
        if(paciente.isEmpty()){
            throw new CommonsExceptionsPacientes(HttpStatus.BAD_REQUEST,
                    "não existe nenhum paciente com esse id no banco",
                    "consulta.service.mandarLembrete"
            );
        }
        Email email = new Email(paciente.get().getEmail(), subject, bodyEmail);
        sendEmail(email);
    }
}
