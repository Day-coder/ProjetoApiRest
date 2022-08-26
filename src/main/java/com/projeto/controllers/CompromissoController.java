package com.projeto.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.entities.Compromisso;
import com.projeto.repository.CompromissoRepository;
import com.projeto.service.CompromissoService;

@RestController
@RequestMapping("/")
public class CompromissoController {
	@Autowired
	CompromissoService service;
	
	@GetMapping("/compromissos")
	public ResponseEntity<List<Compromisso>> consultaCompromissos(){
		return ResponseEntity.ok(service.mostrarTodosCompromissos());
	}
	
	@PostMapping("/compromissos")
	public ResponseEntity<Compromisso> criaCompromisso(@RequestBody Compromisso compromisso){
		Compromisso comp= service.salvarCompromisso(compromisso);
		return ResponseEntity.status(HttpStatus.CREATED).body(comp);
	}
	
	
	
	
	
	
	
	
	
	
	
	
//	@Autowired
//	CompromissoRepository repo;
//
//	@GetMapping("/compromissos/contato/{nome}")
//	public ResponseEntity<List<Compromisso>> consultaCompromissoPeloNome(@PathVariable("nome") String nome){
//		return ResponseEntity.ok(repo.consultaCompromisso(nome));
//		
//	}
//	
//	@GetMapping("/compromissos")
//	public ResponseEntity<List<Compromisso>> consultaCompromissos() {
//		return ResponseEntity.ok(repo.findAll());
//	}
//	
//	@PostMapping("/compromissos")
//	public ResponseEntity<Compromisso> slavarCompromisso(@RequestBody Compromisso compromisso){
//		return ResponseEntity.ok(repo.save(compromisso));
//	}

}
