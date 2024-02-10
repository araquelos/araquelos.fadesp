package com.fadesp.dt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fadesp.dt.enums.StatusPagamentoEnum;
import com.fadesp.dt.models.Pagamento;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Long>{

	@Query("SELECT p FROM Pagamento p WHERE p.codigoDebito = ?1")
    public Optional<Pagamento> filtrarPorCodigoDebito(Integer codigoDebito);
	
	@Query("SELECT p FROM Pagamento p WHERE p.cpfCnpjPagador = ?1")
    public Optional<Pagamento> filtrarPorCPFCNPJ(String cpfCnpjPagador);
	
	@Query("SELECT p FROM Pagamento p WHERE p.statusPagamento = ?1")
    public Optional<Pagamento> filtrarPorStatus(StatusPagamentoEnum statusPagamento);
}
