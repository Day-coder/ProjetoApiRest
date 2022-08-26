package com.projeto.service;



import java.util.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.projeto.entities.Compromisso;
import com.projeto.entities.Contato;
import com.projeto.repository.CompromissoRepository;


@ExtendWith(SpringExtension.class)
public class CompromissoServiceTestes {
	private Long idExistente;
	private Long idInexistente;
	private Contato contatoValido;
	private Contato contatoInvalido;
	
	private Compromisso compValido;
	private Compromisso compInvalido;
	
	@BeforeEach
	void setup()  {
		idExistente= 1l;
		idInexistente= 1000l;

//		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date d= new Date();
	
		contatoValido= new Contato("Day", "12345678912345", "day@gmail.com");
		//contatoInvalido= new Contato("day", "123", null);
		
		compValido= new Compromisso();
		//compInvalido= new Compromisso(contatoInvalido);

	Mockito.when(repository.save(compValido)).thenReturn(compValido);

		Mockito.doThrow(IllegalArgumentException.class).when(repository).save(compInvalido);
	}
	@InjectMocks
	CompromissoService service;
	
	@Mock
	CompromissoRepository repository;
	
	@Test
	public void retornaCompromissoQuandoSalvarComp() {
		Compromisso comp= service.salvarCompromisso(compValido);
		Assertions.assertNotNull(comp);
		Mockito.verify(repository).save(compValido);
	}
	
	@Test
	public void retornaExcecaoQuandoCompForInvalido() {
		Assertions.assertThrows(IllegalArgumentException.class, ()-> service.salvarCompromisso(compInvalido));
		Mockito.verify(repository.save(compInvalido));
	}
	


}
