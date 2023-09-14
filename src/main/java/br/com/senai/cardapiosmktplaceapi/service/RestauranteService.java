package br.com.senai.cardapiosmktplaceapi.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import br.com.senai.cardapiosmktplaceapi.entity.Categoria;
import br.com.senai.cardapiosmktplaceapi.entity.Restaurante;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Validated
public interface RestauranteService {
	
	public Restaurante salvar( 
		
		@NotNull(message = "o restaurante não pode ser nulo")
		Restaurante restaurante);
		
	public void atualizarStatusPor(
			@NotNull(message = "o id é obrigatorio")
			@Positive(message = "o id dev se positivo")
			Integer id ,
			@NotNull(message = "o status é obrigatorio")
			Status status
			);
	
	public Page<Restaurante> listarPor(
			@NotBlank(message = "O nome é obrigatorio")
			@Size(min = 3 ,max = 250 ,message = "O nome para listagem deve "
					+ "conter entre 3 e 250 caracteres ")
			String nome ,
			@NotNull(message = " A categoria é obrigatoria")
			Categoria categoria,
			Pageable pageable 
			);
	
	public Categoria buscarPor(
			@NotNull(message = "o id para busca é obrigatorio")
			@Positive(message = "o id para busca deve ser positivo")
			Integer id
			);
	
	public Restaurante excluirPor(
			@Positive(message = "o id deve ser positivo")
			@NotNull(message = "O id é obrigatorio")
			Integer id
			);
	

}
