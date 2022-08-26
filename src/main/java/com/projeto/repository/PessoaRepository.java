package com.projeto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto.entities.Contato;
import com.projeto.entities.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

}
