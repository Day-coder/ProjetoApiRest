package com.projeto.service;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.projeto.entities.Contato;
import com.projeto.repository.ContatoRepository;
import com.projeto.service.dto.ContatoDto;

@ExtendWith(SpringExtension.class)
public class ContatoServiceTestes {
	private Long idExistente;
	private Long idNaoExistente;
	private Contato contatoValido;
	private Contato contatoInvalido;
	
	@BeforeEach
	void setup() {
		idExistente=1l;
		idNaoExistente= 1000l;
		contatoValido= new Contato("maria", "maria@gmail.com", "45678");
		contatoInvalido= new  Contato("maria", null, null);
	Mockito.when(repository.save(contatoValido)).thenReturn(contatoValido);
	Mockito.doThrow(IllegalArgumentException.class).when(repository).save(contatoInvalido);
	
	Mockito.doNothing().when(repository).deleteById(idExistente);
	Mockito.doThrow(EntityNotFoundException.class).when(repository).deleteById(idNaoExistente);
	
	Mockito.when(repository.findById(idExistente)).thenReturn(Optional.of(new Contato()));
	Mockito.doThrow(EntityNotFoundException.class).when(repository).findById(idNaoExistente);
	
	}

	@InjectMocks
	ContatoService service;
	
	@Mock
	ContatoRepository repository;
	
	@Test
	public void retornaContatoDtoQuandoSalvarContato() {
		ContatoDto contatoDto= service.salvar(contatoValido);
		Assertions.assertNotNull(contatoDto);
		Mockito.verify(repository).save(contatoValido);
	}
	
	@Test
	public void retornaErroAoCadastrar() {
		Assertions.assertThrows(IllegalArgumentException.class, ()-> service.salvar(contatoInvalido));
	Mockito.verify(repository).save(contatoInvalido);
	}
	
	
	@Test
	public void retornaNadaAoExcluirComIdExistente() {
		Assertions.assertDoesNotThrow(()->{
			service.excluirContato(idExistente);
		});
		Mockito.verify(repository, Mockito.times(1)).deleteById(idExistente);
	}
	
	@Test
	public void lancaEntidadeNaoEncontradaAoExcluirIdNaoExistente() {
		Assertions.assertThrows(EntityNotFoundException.class,()->{
			service.excluirContato(idNaoExistente);
		});
		Mockito.verify(repository, Mockito.times(1)).deleteById(idNaoExistente);
	}
	
	@Test
	public void retornaContatoQuandoPesquisaPorId() {
		Contato ct= service.consultarContatoPorId(idExistente);
		Assertions.assertNotNull(ct);
		Mockito.verify(repository).findById(idExistente);
		
	}
	
	@Test
	public void lancaEntidadeNaoEncontradaAoConsultarIdNaoExistente() {
		Assertions.assertThrows(EntityNotFoundException.class,()->{
			service.consultarContatoPorId(idNaoExistente);
		});
		Mockito.verify(repository, Mockito.times(1)).findById(idNaoExistente);
	
	}
	
}
