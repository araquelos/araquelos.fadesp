package com.fadesp.dt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fadesp.dt.models.Pagamento;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Long>{

	//Optional<Pagamento> filtrarPorCodigoDebito(Integer codigoDebito);


	
}
