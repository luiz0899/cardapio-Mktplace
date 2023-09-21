package br.com.senai.cardapiosmktplaceapi.entity;

import java.math.BigDecimal;

import br.com.senai.cardapiosmktplaceapi.entity.composity.OpcaoDoCardapioId;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Confimacao;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "opcoes_cardapios")
@Entity(name = "OpcaoCardapio")
public class OpcaoCardapio {
	
	@EmbeddedId
	@EqualsAndHashCode.Include
	@NotNull (message = "o id do cardapio é obrigatorio")
	private  OpcaoDoCardapioId id ;
	
	@DecimalMin(value = "0.0" ,inclusive = false ,message = "o percentual não pode "
			+ "	ser inferior a 0.00")
	@Digits(integer = 9,fraction = 2,message = "O percentual"
											 + " de desconto deve possuir 'NNNNNNNNN.NN'")
	@NotNull(message = "O preço é obrigatorio")
	@Column(name = "preco")
	private BigDecimal preco ;

	@Enumerated(value = EnumType.STRING)
	@NotNull(message = "O status não pode ser nulo")
	@Column(name = "status")
	private Status status;
	
	@Enumerated(value = EnumType.STRING)
	@NotNull(message = "O status não pode ser nulo")
	@Column(name = "recomendado")
	private Confimacao recomendado;
	
	@ToString.Exclude
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_opcao")
	@MapsId("idDaOpcao")
	@NotNull(message = "A opção é obrigatoria")
	private Opcao opcao;
	
	@ToString.Exclude
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("idDoCardapio")
	@JoinColumn(name = "id_cardapio")
	@NotNull(message = "O cardapio é obrigatoria")
	private Cardapio cardapio;
	
	@ToString.Exclude
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_secao")
	@NotNull(message = "A Seção é obrigatoria")
	private Secao secao;
	
	public OpcaoCardapio() {
		this.status = Status.A;		
	}
	
	@Transient
	public boolean isPersistido() {
		return getId() != null ;
	}
	
	@Transient
	public boolean isAtivo() {
		return getStatus() == Status.A;
	}
	
	
	

}
