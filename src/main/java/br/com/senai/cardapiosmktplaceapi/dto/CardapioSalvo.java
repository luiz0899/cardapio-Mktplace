package br.com.senai.cardapiosmktplaceapi.dto;

import br.com.senai.cardapiosmktplaceapi.entity.Restaurante;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CardapioSalvo {

	@NotNull(message = "O id é obrigatorio")
	@Positive(message = "O id do cardapio deve ser positivo")
	private Integer id ;
	

	@Size(max = 100, message = "o nome não pode conter mais que 100 caracteres")
	@NotBlank(message = "O nome é obrigatorio")
	private String nome;
	
	@NotBlank(message = "A descrição é obrigatoria")
	private String descricao ;
	
	@NotNull(message = "O restaurante é obrigatorio")
	private Restaurante restaurante ;
	
	
	@NotNull(message = "O status é obrigatorio ")
	private Status status;
	
	
}
