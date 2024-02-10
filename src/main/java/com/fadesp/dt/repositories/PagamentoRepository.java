package com.fadesp.dt.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fadesp.dt.models.Pagamento;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Long>{

	@Query("SELECT p FROM Pagamento p WHERE CONCAT(p.codigoDebito, ' ', p.cpfCnpjPagador, ' ', p.statusPagamento) LIKE %?1%")
	public List<Pagamento> filtrar(String keyword);
	
}
