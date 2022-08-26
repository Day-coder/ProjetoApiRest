package com.projeto.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.swing.text.html.parser.Entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto.entities.Compromisso;
import com.projeto.repository.CompromissoRepository;

@Service
public class CompromissoService {

	@Autowired
	CompromissoRepository repository;
	
	public Compromisso salvarCompromisso(Compromisso compromisso) {
		return repository.save(compromisso);
	}
	
	public List<Compromisso> mostrarTodosCompromissos(){
		List<Compromisso> compromissos= repository.findAll();
		return compromissos;
	}
	
	public Compromisso mostrarUmCompromisso(Long id) {
		Optional<Compromisso> op= repository.findById(id);
		Compromisso compromisso= op.orElseThrow(()-> new EntityNotFoundException("Não encontrado"));
		return compromisso;
		
	}
	
	public void deletaCompromisso(Long id) {
		repository.deleteById(id);
	}
	
	public Compromisso atualizaCompromisso(Long id, Compromisso compromisso) {
		Compromisso comp= mostrarUmCompromisso(id);
		comp.setContato(compromisso.getContato());
		comp.setData(compromisso.getData());
		comp.setLocal(compromisso.getLocal());
		return repository.save(comp);
		
	}
}
