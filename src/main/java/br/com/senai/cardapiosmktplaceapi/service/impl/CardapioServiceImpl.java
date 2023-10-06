package br.com.senai.cardapiosmktplaceapi.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;

import br.com.senai.cardapiosmktplaceapi.dto.CardapioSalvo;
import br.com.senai.cardapiosmktplaceapi.dto.NovaOpcaoCardapio;
import br.com.senai.cardapiosmktplaceapi.dto.NovoCardapio;
import br.com.senai.cardapiosmktplaceapi.entity.Cardapio;
import br.com.senai.cardapiosmktplaceapi.entity.Opcao;
import br.com.senai.cardapiosmktplaceapi.entity.OpcaoCardapio;
import br.com.senai.cardapiosmktplaceapi.entity.Restaurante;
import br.com.senai.cardapiosmktplaceapi.entity.Secao;
import br.com.senai.cardapiosmktplaceapi.entity.composity.OpcaoDoCardapioId;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;
import br.com.senai.cardapiosmktplaceapi.repository.CardapiosRepository;
import br.com.senai.cardapiosmktplaceapi.repository.OpcoesRepository;
import br.com.senai.cardapiosmktplaceapi.repository.RestaurantesRepository;
import br.com.senai.cardapiosmktplaceapi.repository.SecaoRespository;
import br.com.senai.cardapiosmktplaceapi.service.CardapioService;
import br.com.senai.cardapiosmktplaceapi.service.RestauranteService;

@Service
public class CardapioServiceImpl implements CardapioService {

	@Autowired
	private OpcoesRepository opcoesRepository ;
	
	@Autowired
	private SecaoRespository secaoRespository ;
	
	@Autowired
	private CardapiosRepository repository ;
	
	@Autowired
	private RestaurantesRepository restaurantesRepository ;
	
	@Autowired
	@Qualifier("restauranteServiceImpl")
	private RestauranteService restauranteService ;
	
	private Restaurante getRestaurantePor(NovoCardapio novoCardapio ) {
		
		Preconditions.checkNotNull(novoCardapio.getRestaurante()
				," O restaurante é obrigatório");
		Restaurante restaurante = restaurantesRepository.buscarPor(
				novoCardapio.getRestaurante().getId());
		Preconditions.checkNotNull(restaurante , "O restaurante "
				+ novoCardapio.getRestaurante().getId() +"não foi salvo ");
		Preconditions.checkArgument(restaurante.isAtivo() ,
				" O restaurante está inativo");
		return restaurante ;
	}
	
	private Secao getSecaoPor(NovaOpcaoCardapio novaOpcao) {
		
		Preconditions.checkNotNull(novaOpcao.getSecao() , 
				"A seção da opção é obrigatoria ");
		Secao secao = secaoRespository.findById(novaOpcao.getSecao().getId()).get();
		Preconditions.checkNotNull(secao.isAtiva() , " A seção deve estar ativa ");
		
		return secao ;
	}
		
	private Opcao getOpcaoPor ( Integer idDaOpcao , Restaurante restaurante) {
		
		Opcao opcao = opcoesRepository.buscarPor(idDaOpcao);
		Preconditions.checkNotNull(opcao,"não existe opção vinculada ao id "
				+ idDaOpcao + "'");
		Preconditions.checkArgument(opcao.isAtivo(),"A opção"
				+ opcao.getId() + "está inativa ");
		Preconditions.checkArgument(opcao.getRestaurante().equals(restaurante),
				"A opção " + idDaOpcao + " não pertence ao cardápio do restaurante");
		
		return opcao ;
	}
	
	private void validarDuplicidadeEm(List<NovaOpcaoCardapio> opcaoCardapios){
		
		for (NovaOpcaoCardapio novaOpcao: opcaoCardapios ) {
			
			int qtdeDeOpcaoOcorrencias = 0 ;
			for (NovaOpcaoCardapio outraOpcao : opcaoCardapios) {
				if (novaOpcao.getIdDaOpcao().equals(outraOpcao.getIdDaOpcao())) {
					qtdeDeOpcaoOcorrencias++;
				}
			}
			Preconditions.checkArgument(qtdeDeOpcaoOcorrencias==1,
					"Aopção " + novaOpcao.getIdDaOpcao() +
					"está duplicata no cardapios");
		}
	}
	
	@Override
	public Cardapio inserir( NovoCardapio novoCardapio) {
		
		Restaurante restaurante = getRestaurantePor(novoCardapio);
		Cardapio cardapio = new Cardapio();
		cardapio.setNome((novoCardapio.getNome()));
		cardapio.setDescricao(novoCardapio.getDescricao());
		cardapio.setRestaurante(restaurante);
		Cardapio cardapioSalvo = repository.save(cardapio);
		this.validarDuplicidadeEm(novoCardapio.getOpcoes());
		for (NovaOpcaoCardapio novaOpcao : novoCardapio.getOpcoes()) {
			Opcao opcao = getOpcaoPor(novaOpcao.getIdDaOpcao(), restaurante);
			Secao secao = getSecaoPor(novaOpcao);
			OpcaoDoCardapioId id = new OpcaoDoCardapioId(cardapioSalvo.getId() ,opcao.getId());
			OpcaoCardapio opcaoCardapio = new OpcaoCardapio();
			opcaoCardapio.setId(id);
			opcaoCardapio.setCardapio(cardapioSalvo);
			opcaoCardapio.setOpcao(opcao);
			opcaoCardapio.setSecao(secao);
			opcaoCardapio.setPreco(novaOpcao.getPreco());
			opcaoCardapio.setRecomendado(novaOpcao.getRecomendado());
			cardapioSalvo.getOpcoes().add(opcaoCardapio);
			this.repository.saveAndFlush(cardapioSalvo);
			
		}
		
		return repository.buscarPor(cardapioSalvo.getId());
	}

	@Override
	public Cardapio alterar( CardapioSalvo cardapioSalvo) {
		
		Restaurante restaurante = restauranteService.buscarPor(
				cardapioSalvo.getRestaurante().getId());
		Cardapio cardapio = repository.buscarPor(cardapioSalvo.getId());
		Preconditions.checkNotNull(cardapio,
				"não existe cardapio vinculado ao id");
		Preconditions.checkArgument(restaurante.equals(cardapio.getRestaurante())
				, "O restaurante do vcardapio não pode ser alterado");
		cardapio.setNome(cardapioSalvo.getNome());
		cardapio.setDescricao(cardapioSalvo.getDescricao());
		cardapio.setRestaurante(restaurante);
		cardapio.setStatus(cardapioSalvo.getStatus());
		Cardapio cardapioAtualizado = repository.saveAndFlush(cardapio);
		
		return buscarPor(cardapioAtualizado.getId());
	}
	
	private void atualizarPrecoDas(List<OpcaoCardapio> opcaoCardapios) {
		
		for (OpcaoCardapio opcaoCardapio : opcaoCardapios) {
			
			if(opcaoCardapio.getOpcao().isEmPromocao()) {
				
				BigDecimal divisor = new BigDecimal(100);
				BigDecimal percentualDeDesconto = opcaoCardapio.getOpcao()
						.getPercenrualDeDesconto();
				
				BigDecimal valorDescontado = opcaoCardapio.getPreco().
						multiply(percentualDeDesconto).divide(divisor);
				
				BigDecimal preco = opcaoCardapio.getPreco()
						.subtract(valorDescontado).setScale(2,RoundingMode.CEILING);
				
				opcaoCardapio.setPreco(preco);
			}			
			
		}
		
	}

	@Override
	public Page<Cardapio> listarPor( Restaurante restaurante,Pageable paginacao) {
		
		Page<Cardapio>cardapios = repository.listarPor(restaurante, paginacao);
		for (Cardapio cardapio : cardapios.getContent()) {
			this.atualizarPrecoDas(cardapio.getOpcoes());
		}
		
		return cardapios;
	}

	@Override
	public Cardapio buscarPor( Integer id) {
		
		Cardapio cardapioEncontrado = repository.buscarPor(id);
		Preconditions.checkNotNull(cardapioEncontrado,
				"não foi encontrado id para o cardapio enformado ");
		Preconditions.checkArgument(cardapioEncontrado.isAtivo(), 
				"O cardápio está inativo");
		this.atualizarPrecoDas(cardapioEncontrado.getOpcoes());
		
		return cardapioEncontrado;
	}
	
	@Override
	public void atualizarStatusPor(Integer id,Status status) {
		
		Cardapio cardapioEncontrado = repository.buscarPor(id);
		Preconditions.checkNotNull(cardapioEncontrado,
				"não foi encontrado id para o cardapio enformado ");
		Cardapio cardapio = buscarPor(id);
		Preconditions.checkNotNull(cardapio.getStatus() != status , 
				"O status do cardapio tem que ser informado ");
		
		this.repository.atualizarPor(id, status);
		
	}

}
