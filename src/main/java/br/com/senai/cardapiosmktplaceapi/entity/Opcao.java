package br.com.senai.cardapiosmktplaceapi.entity;

import java.math.BigDecimal;

import br.com.senai.cardapiosmktplaceapi.entity.enums.Confimacao;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded =  true)
@Table(name = "opcoes")
@Entity(name = "Opcao")
public class Opcao {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Integer id ;
	
	@Size
	@NotBlank(message = "O nome da opão é obrigatorio")
	@Column(name = "nome")
	private String nome;
	
	@Column(name = "descricao")
	private String descricao;
	
	@Enumerated(value = EnumType.STRING)
	@NotNull(message = "O status não pode ser nulo")
	@Column(name = "status")
	private Status status;
	
	@Enumerated(value = EnumType.STRING)
	@NotNull(message = "O indicador não pode ser nulo")
	@Column(name = "promocao")
	private Confimacao promocao;
	
	@DecimalMin(value = "0.0" ,inclusive = false ,message = "o percentual não pode "
			+ "												ser inferior a 0.01%")
	@DecimalMax(value = "100.0%",inclusive = false ,message = "o percentual não pode "
			+ "												ser superior a 100.0%")
	@Digits(integer = 2,fraction = 2,message = "O percentual"
											 + " de desconto deve possuir 'nn.nn''")
	@Column(name = "percentual_desconto")
	private BigDecimal percenrualDeDesconto;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_categoria")
	@NotNull(message = "A categoria é obrigatoria")
	private Categoria categoria;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_restaurante")
	@NotNull(message = "O restaurante é obrigatorio")
	private Restaurante restaurante;
	

	public Opcao() {
		this.status = Status.A;
	}
	
	@Transient
	public boolean isPersistido() {
		return getId() != null && getId() > 0;
	}
	
	@Transient
	public boolean isAtivo() {
		return getStatus() == Status.A;
	}
	
	@Transient
	public boolean isEmPromocao () {
		return getPromocao() == Confimacao.S;
	}
	
	
	
	
	
	
	

}
