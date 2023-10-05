package br.com.senai.cardapiosmktplaceapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Notificacao {
	
	@Size(max = 100 , message = "O e-mail não deve conter mais de 100 caracteres")
	@Email(message = "O e-mail é invalido")
	@NotBlank(message = "O destinatario é obrigatório")
	private String destinatario;

	@Size(max = 100 , message = "O título não deve conter mais de 100 caracteres")
	@NotBlank(message = "A menssagem é obrigatoria")
	private String titulo; 
	
	@NotBlank(message = "Amensagem é obrigatorio")
	private String messagem;
}
