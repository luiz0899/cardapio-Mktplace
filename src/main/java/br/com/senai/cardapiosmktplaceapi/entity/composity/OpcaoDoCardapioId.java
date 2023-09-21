package br.com.senai.cardapiosmktplaceapi.entity.composity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Embeddable
@AllArgsConstructor
public class OpcaoDoCardapioId {

	@Column(name = "id_cardapio")
	private Integer idDoCardapio;
	
	@Column(name = "id_opcao")
	private Integer idDaOpcao;
	
	
	
}
