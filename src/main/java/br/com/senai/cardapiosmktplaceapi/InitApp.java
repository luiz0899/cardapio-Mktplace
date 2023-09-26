package br.com.senai.cardapiosmktplaceapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.datatype.hibernate5.jakarta.Hibernate5JakartaModule;

import br.com.senai.cardapiosmktplaceapi.entity.Categoria;
import br.com.senai.cardapiosmktplaceapi.entity.Opcao;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;
import br.com.senai.cardapiosmktplaceapi.repository.CategoriasRepository;
import br.com.senai.cardapiosmktplaceapi.repository.OpcoesRepository;

@SpringBootApplication
public class InitApp {
	
	@Autowired
	private CategoriasRepository repository;
	
	@Autowired
	private OpcoesRepository opcoesRepository;
	
	public static void main(String[] args) {
	
		SpringApplication.run(InitApp.class,args);
			
	}
	
	@Bean
	public Hibernate5JakartaModule jsonHibernate5JakartaModule ( ) {
		return new Hibernate5JakartaModule();
	}
	
	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx ) {
		
		return args -> {
		/*
			System.out.println("subiu");
			Status status = Status.I ;
			Integer id = 300; 
			
		this.opcoesRepository.atualizarPor(id,status);
		System.out.println("aq");*/
			/*aquiii*/
		};
	}
	
}