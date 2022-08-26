package com.projeto.entities;

import java.time.Instant;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class Contato {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToMany(mappedBy = "contato", cascade = CascadeType.REMOVE)
	private List<Compromisso> compromissos;
	
	
	@NotBlank(message= "Nome obrigatório")
	@Column(length = 40, nullable = false)
	private String nome;
	
	@Size(max= 14, min=14 ,message="O telefone tem que ter 14 caracteres")
	private String fone;
	
	@NotBlank(message="Email obrigatório")
	@Email(message= "Email inválido")
	private String email;

	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private Instant createdAt;
	
	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private Instant updateAt;
	
	public Contato(String nome, String fone, String email) {
		super();
		this.nome = nome;
		this.fone = fone;
		this.email = email;
	}

	public Contato() {

	}

	public Instant getCreatedAt() {
		return createdAt;
	}
@PrePersist //
	public void setCreatedAt() {
		this.createdAt = Instant.now();
	}

	public Instant getUpdateAt() {
		return updateAt;
	}
@PreUpdate
	public void setUpdateAt() {
		this.updateAt = Instant.now();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getFone() {
		return fone;
	}

	public void setFone(String fone) {
		this.fone = fone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
