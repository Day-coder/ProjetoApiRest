package com.projeto.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.projeto.entities.Pessoa;
import com.projeto.service.PessoaService;

@SpringBootTest
@AutoConfigureMockMvc //representa toda a camado web
public class PessoaControllerTestes {
	private Long idExistente;
	private Long idNaoExistente;
	private Pessoa pExistente;
	private Pessoa pNovo;
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private PessoaService  service;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@BeforeEach
	void setup() {
		idExistente= 1L;
		idNaoExistente= 100L;
		pNovo= new Pessoa();
		pExistente= new Pessoa("Day");
		pExistente.setId(1L);
		
		Mockito.when(service.consultar(idExistente)).thenReturn(pExistente);
	Mockito.doThrow(EntityNotFoundException.class).when(service).consultar(idNaoExistente);
	Mockito.when(service.salvar(any())).thenReturn(pExistente);
	Mockito.when(service.alterar(eq(idExistente), any())).thenReturn(pExistente);
	Mockito.when(service.alterar(eq(idNaoExistente), any())).thenThrow(EntityNotFoundException.class);
	}
	
	@Test
	public void deveRetornarPessoaQuandoConsultaIdExistente() throws Exception {
		ResultActions result= mockMvc.perform(get("/pessoa/{idpessoa}", idExistente).accept(MediaType.APPLICATION_JSON));
	result.andExpect(status().isOk());
	}
	
	@Test
	public void deveRetornar404QuandoConsultaIdNaoExistente() throws Exception {
		ResultActions result= mockMvc.perform(get("/pessoa/{idpessoa}", idNaoExistente).accept(MediaType.APPLICATION_JSON));
	result.andExpect(status().isNotFound());
	}
	
	@Test
	public void deveRetornar204SalvarComSucesso() throws Exception {
		String jsonBody= objectMapper.writeValueAsString(pNovo);
		ResultActions result = mockMvc.perform(post("/pessoa")
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON));
				result.andExpect(status().isCreated());
	}
	
	@Test
	public void deveRetornarOkQuandoAlterarComSucesso() throws Exception  {
		String jsonBody= objectMapper.writeValueAsString(pExistente);
		ResultActions result= mockMvc.perform(put("/pessoa/{idpessoa}", idExistente)
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		result.andExpect(status().isOk());
	}
	
	@Test
	public void deveRetornar404QuandoAlterarContatoInexistente() throws Exception {
		String jsonBody= objectMapper.writeValueAsString(pNovo);
	ResultActions result= mockMvc.perform(put("/pessoa/{idpessoa}", idNaoExistente)
			.content(jsonBody)
			.contentType(MediaType .APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON));
	result.andExpect(status().isNotFound());
	}
	
	@Test
	public void retornaListaQuandoConsultaTodosComSucesso() throws Exception {
		ResultActions result= mockMvc.perform(get("/pessoa"));
		result.andExpect(status().isOk());
		Mockito.when(service.consultarTodos()).thenReturn(new ArrayList<>());
	}
}
