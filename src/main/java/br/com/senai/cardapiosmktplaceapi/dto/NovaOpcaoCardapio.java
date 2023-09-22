package br.com.senai.cardapiosmktplaceapi.dto;

import java.math.BigDecimal;

import br.com.senai.cardapiosmktplaceapi.entity.Secao;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Confimacao;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NovaOpcaoCardapio {
	
	@NotNull(message = "o id da opção é óbrigatorio")
	@Positive(message = "o id da opção deve ser positivo")
	private Integer idDaOpcao;
	
	@DecimalMin(value = "0.0", inclusive = false ,
			message = "O preço não pode ser duperior a R$0.1 ") 
 	@Digits(integer = 9,fraction = 2,message = "O preço deve possuir o formator NNNNNNNNN.NN" )
	private BigDecimal preco;
	
	@NotNull(message = "o indicador não pode ser nulo")
	private Confimacao recomendado; 
	
	@NotNull(message = "A seção é obrigatoria")
	private Secao secao ;
	
	

}
