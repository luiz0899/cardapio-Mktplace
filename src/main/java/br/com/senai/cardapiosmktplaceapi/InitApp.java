package br.com.senai.cardapiosmktplaceapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import br.com.senai.cardapiosmktplaceapi.entity.Categoria;
import br.com.senai.cardapiosmktplaceapi.entity.Opcao;
import br.com.senai.cardapiosmktplaceapi.repository.CategoriasRepository;
import br.com.senai.cardapiosmktplaceapi.repository.OpcoesRepository;

@SpringBootApplication
public class InitApp {
	
	@Autowired
	private CategoriasRepository repository;
	
	private OpcoesRepository opcoesRepository;
	
	public static void main(String[] args) {
	
		SpringApplication.run(InitApp.class,args);
			
	}
	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx ) {
		
		return args -> {
			
		Opcao opcao = opcoesRepository.buscarPor(40);
		System.out.println(opcao);
			
		};
	}
	
}