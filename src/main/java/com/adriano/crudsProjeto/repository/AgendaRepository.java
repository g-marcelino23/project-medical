package com.adriano.crudsProjeto.repository;

import com.adriano.crudsProjeto.data.model.Agenda;
import com.adriano.crudsProjeto.data.model.Horario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AgendaRepository extends JpaRepository<Agenda,Long> {
}
