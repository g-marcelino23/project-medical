package com.adriano.crudsProjeto.repository;

import com.adriano.crudsProjeto.data.model.Exame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExameRepository extends JpaRepository<Exame, Long> {
}
