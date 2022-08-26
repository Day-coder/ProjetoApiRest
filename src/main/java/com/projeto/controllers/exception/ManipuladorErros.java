package com.projeto.controllers.exception;

import java.time.Instant;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.projeto.service.exceptions.EmailException;
import com.projeto.service.exceptions.MinhaExcecao;

@ControllerAdvice //qualquer exceção do service vai ser capturada por essa exceção
public class ManipuladorErros {

	@ExceptionHandler(EntityNotFoundException.class)//pega a exceção lançada no service, o expetionHandler so pode ser anotada em métodos
	public ResponseEntity<ErroPadrao> entidadeNaoEncontrada(EntityNotFoundException e, HttpServletRequest req){// esse método vai personaizar a exceção
		ErroPadrao erro= new ErroPadrao();
		erro.setTimestamp(Instant.now());
		erro.setStatus(HttpStatus.NOT_FOUND.value());
		erro.setError("Recurso não encontrado");
		erro.setMessage(e.getMessage());
		erro.setPath(req.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
	}
	
	@ExceptionHandler(MinhaExcecao.class)//pega a exceção lançada no service, o expetionHandler so pode ser anotada em métodos
	public ResponseEntity<ErroPadrao> minhaExcecao(MinhaExcecao e, HttpServletRequest req){// esse método vai personaizar a exceção
		ErroPadrao erro= new ErroPadrao();
		erro.setTimestamp(Instant.now());
		erro.setStatus(HttpStatus.BAD_REQUEST.value());
		erro.setError("Sem conteúdo");
		erro.setMessage(e.getMessage());
		erro.setPath(req.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
	
	@ExceptionHandler(EmailException.class)//pega a exceção lançada no service, o expetionHandler so pode ser anotada em métodos
	public ResponseEntity<ErroPadrao> emailExcecao(EmailException e, HttpServletRequest req){// esse método vai personaizar a exceção
		ErroPadrao erro= new ErroPadrao();
		erro.setTimestamp(Instant.now());
		erro.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
		erro.setError("Formato de email inválido");
		erro.setMessage(e.getMessage());
		erro.setPath(req.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(erro);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)//pega a exceção lançada no service, o expetionHandler so pode ser anotada em métodos
	public ResponseEntity<ErroPadrao> entidadeNaoValidada(MethodArgumentNotValidException e, HttpServletRequest req){// esse método vai personaizar a exceção
		ErroPadrao erro= new ErroPadrao();
		erro.setTimestamp(Instant.now());
		erro.setStatus(HttpStatus.BAD_REQUEST.value());
		erro.setError("Recurso não encontrado");
		erro.setMessage(e.getMessage());
		erro.setPath(req.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
}
}
