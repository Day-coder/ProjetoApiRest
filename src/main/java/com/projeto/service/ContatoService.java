package com.projeto.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto.entities.Contato;
import com.projeto.repository.ContatoRepository;
import com.projeto.service.dto.ContatoDto;
import com.projeto.service.exceptions.EmailException;
import com.projeto.service.exceptions.MinhaExcecao;

@Service
public class ContatoService {
	@Autowired
	ContatoRepository repo;

	public ContatoDto salvar(Contato contato) {
//		if (!contato.getEmail().contains("@")) {
//			throw new EmailException("Email inválido");
//		}
		//return repo.save(contato);
	
		Contato ct= repo.save(contato);
		ContatoDto contatoDTO= new ContatoDto(ct.getId(), ct.getNome(),ct.getEmail() , ct.getFone());
	//ContatoDto contatoDTO= new ContatoDto(ct);
		return contatoDTO;
	}

	public List<ContatoDto> consultarContatos() {
		List<Contato> contatos = (List<Contato>) repo.findAll();
		List<ContatoDto> contatosDto= new ArrayList<>();
		for(Contato ct : contatos) {
			contatosDto.add(new ContatoDto(ct));
		}
		return contatosDto;

	}

	public Contato consultarContatoPorId(Long idcontato) {
		Optional<Contato> op = repo.findById(idcontato);
		Contato ct = op.orElseThrow(() -> new EntityNotFoundException("Contato não encontrado"));
		return ct;
	}
	
	private ContatoDto consultaPorId(Long idcontato) {
		Optional<Contato> op = repo.findById(idcontato);
		Contato ct = op.orElseThrow(() -> new EntityNotFoundException("Contato não encontrado"));
		return new ContatoDto(ct);
	}

	public void excluirContato(Long id) {
//	Contato ct= consultarContatoPorId(id);//primeiro consulta
//	repo.delete(ct);// deleta
		// testando excecao
//		Optional<Contato> op = repo.findById(id);
//		Contato ct = op.orElseThrow(() -> new MinhaExcecao("O contato não existe"));
//		repo.delete(ct);
		 repo.deleteById(id);
	}

	public Contato alterarContato(Long idcontato, Contato contato) {
		Contato ct = consultarContatoPorId(idcontato);
		ct.setEmail(contato.getEmail());
		ct.setNome(contato.getNome());
		ct.setFone(contato.getFone());
		return repo.save(ct);
	}
	
	public List<ContatoDto>consultaPorEmail(String email){
		return ContatoDto.converteParaDto(repo.findByEmail(email));
	}

}
