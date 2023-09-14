package br.com.senai.cardapiosmktplaceapi.dto;

import java.util.ArrayList;
import java.util.List;

import br.com.senai.cardapiosmktplaceapi.entity.Restaurante;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NovoCardapio {
	
	@Size(max = 100, message = "o nome não pode conter mais que 100 caracteres")
	@NotBlank(message = "O nome é obrigatorio")
	private String nome;
	
	@NotBlank(message = "A descrição é obrigatoria")
	private String descricao ;
	
	@NotNull(message = "O restaurante é obrigatorio")
	private Restaurante restaurante ;

	@Size(min = 1,message = "Ocardápio deve possuir ao menos 1 opção")
	private List<NovaOpcaoCardapio> opcoes ; 
	
	public NovoCardapio() {
		this.opcoes = new ArrayList<>();
	}
	
	

}
