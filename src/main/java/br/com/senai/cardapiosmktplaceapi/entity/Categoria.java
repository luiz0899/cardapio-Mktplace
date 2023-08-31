package br.com.senai.cardapiosmktplaceapi.entity;

import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;
import br.com.senai.cardapiosmktplaceapi.entity.enums.TipoDeCategoria;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "categorias")
@Entity(name = "Categoria")

public class Categoria {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id ;
	
	@Size(max = 100,message = "O nome da categoria não deve conter mais de 100 caracteres. ")
	@NotBlank(message = "O nome da categoria é obrigatorio")
	@Column(name = "nome")
	private String nome ;
	
	@NotNull(message = "O tipo da categoria é obrigatorio.")
	@Enumerated(value = EnumType.STRING)
	@Column(name = "tipo")
	private TipoDeCategoria tipo;

	@NotNull(message = "O status da categoria é obrigatorio.")
	@Enumerated(value = EnumType.STRING)
	@Column(name = "status")
	private Status status; 
	
}
