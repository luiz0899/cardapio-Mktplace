package br.com.senai.cardapiosmktplaceapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;

import br.com.senai.cardapiosmktplaceapi.entity.Categoria;
import br.com.senai.cardapiosmktplaceapi.entity.Opcao;
import br.com.senai.cardapiosmktplaceapi.entity.Restaurante;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;
import br.com.senai.cardapiosmktplaceapi.repository.OpcoesRepository;
import br.com.senai.cardapiosmktplaceapi.service.OpcaoService;

@Service
public class OpcaoServiceImpl implements OpcaoService {
	
	@Autowired
	private OpcoesRepository repository;
	
	@Override
	public Opcao salvar(Opcao opcao) {
		
		Opcao outraOpcao = repository.buscarPor(opcao.getNome());
			 
				 Preconditions.checkArgument(opcao.equals(outraOpcao),
						 "Essa opção ja existe no sistema , escolha outro nome.");

		Opcao opcaoSalva = repository.save(opcao);
	
		return opcaoSalva ;
		
	}

	@Override
	public Opcao excluirPor( Integer id) {
				
		Opcao opcao = repository.buscarPor(id);
		
		Preconditions.checkNotNull(opcao.getCategoria() ,
				"Foi encontrado uma categoria vinculada a opção. ");
		Preconditions.checkNotNull(opcao ,
				"opcção não encontase nula. ");
		Preconditions.checkArgument(opcao.isAtivo(),
				"A opcao está inativo");
	
		this.repository.deleteById(id);
		
		return null;
	}

	@Override
	public Page<Opcao> ListarPor( String nome,Categoria categoria, Restaurante restaurante, Pageable pageable) {
		
		boolean isValida = categoria == null && restaurante == null;

		if(isValida) {
			throw new IllegalArgumentException(
					"O restaurante ou a categoria tem que estar validos ");
		}
		
		return repository.listarPor(nome, categoria, restaurante, pageable);
	}

	@Override
	public Opcao buscarPor(Integer id) {
		
		Opcao opcao = repository.buscarPor(id);
		
		Preconditions.checkNotNull(opcao,
				"Não foi encontrado opcao para o id informado");
		Preconditions.checkArgument(opcao.isAtivo(),
				"O opcao está inativo");

		return null;
	}

	@Override
	public Opcao AtualizarStatusPorId( Integer id, Status status ) {
		
		
		Opcao opcao = repository.buscarPor(id);
		Preconditions.checkNotNull(opcao,
				"O id não esta vinculado a um resturante " );
		Preconditions.checkArgument(opcao.getStatus() != status,
				"O status ja está salvo para o restaurante");
		this.repository.atualizarPor(id,status);
		
		return null;
	}

}
