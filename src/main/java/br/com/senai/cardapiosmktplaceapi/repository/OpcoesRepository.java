package br.com.senai.cardapiosmktplaceapi.repository;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.senai.cardapiosmktplaceapi.entity.Categoria;
import br.com.senai.cardapiosmktplaceapi.entity.Opcao;
import br.com.senai.cardapiosmktplaceapi.entity.Restaurante;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Confimacao;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;

@Repository
public interface OpcoesRepository extends JpaRepository<Opcao,Integer> {

	@Query(value = 
			"SELECT o "
			+ "FROM Opcao o "
			+ "JOIN FETCH o.categoria JOIN FETCH o.restaurante "
			+ "WHERE Upper(o.nome) LIKE Upper(:nome) "
			+ "AND o.categoria = :categoria "
			+ "AND o.restaurante = :restaurante "
			+ "ORDER BY o.nome ",
			countQuery = "SELECT Count(o) "
						+ "FROM Opcao o "
						+ "WHERE Upper(o.nome) LIKE Upper(:nome) "
						+ "AND o.restaurante = :restaurante "
						+ "AND o.categoria = :categoria ")

	public Page<Opcao> listarPor(String nome, Categoria categoria,Restaurante restaurante, Pageable paginacao);
	
	@Query(value = 
			"SELECT o "
			+ "FROM Opcao o "
			+ "JOIN FETCH o.categoria "
			+ "JOIN FETCH o.restaurante "
			+ "WHERE o.id = :id")
	public Opcao buscarPor(Integer id);
	
	@Modifying
	@Query(value = "UPDATE Opcao o SET o.status = :status WHERE o.id = :id")
	public void atualizarPor(Integer id, Status status);
	
	
	@Query(value = 
			"SELECT o "
			+ "FROM Opcao o "
			+ "WHERE Upper(o.nome) = Upper(:nome) ")
	public Opcao buscarPor(String nome);
	
	
	@Modifying
	@Query("UPDATE Opcao o " 
	       + "SET o.status = :status, " 
		   + "o.nome = :nome, " 
	       + "o.descricao = :descricao, " 
	       + "o.promocao = :promocao, " 
	       + "o.percenrualDeDesconto = :percenrualDeDesconto, " 
	       + "o.categoria = :categoria, " 
	       + "o.restaurante = :restaurante " 
	       + "WHERE o.id = :id")
	void atualizarOpcao(@Param("id") Integer id, @Param("nome") String nome,
	                    @Param("descricao") String descricao, @Param("status") Status status,
	                    @Param("promocao") Confimacao promocao,@Param("percenrualDeDesconto") BigDecimal percenrualDeDesconto,
	                    @Param("categoria") Categoria categoria,@Param("restaurante") Restaurante restaurante);

	
}
