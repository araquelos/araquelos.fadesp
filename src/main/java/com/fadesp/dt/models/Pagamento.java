package com.fadesp.dt.models;

import java.util.Objects;

import com.fadesp.dt.enums.MetodosPagamentoEnum;
import com.fadesp.dt.enums.StatusPagamentoEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Pagamento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    
	@Column(nullable = false)
	private Integer codigoDebito;
    
	@Column(nullable = false)
	private String cpfCnpjPagador;
    
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private MetodosPagamentoEnum metodoPagamento;
    
	@Column(nullable = true)
	private String numeroCartao;
    
	@Column(nullable = false)
	private Double valorPagamento;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private StatusPagamentoEnum statusPagamento;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getCodigoDebito() {
		return codigoDebito;
	}

	public void setCodigoDebito(Integer codigoDebito) {
		this.codigoDebito = codigoDebito;
	}

	public String getCpfCnpjPagador() {
		return cpfCnpjPagador;
	}

	public void setCpfCnpjPagador(String cpfCnpjPagador) {
		this.cpfCnpjPagador = cpfCnpjPagador;
	}

	public MetodosPagamentoEnum getMetodoPagamento() {
		return metodoPagamento;
	}

	public void setMetodoPagamento(MetodosPagamentoEnum metodoPagamento) {
		this.metodoPagamento = metodoPagamento;
	}

	public String getNumeroCartao() {
		return numeroCartao;
	}

	public void setNumeroCartao(String numeroCartao) {
		this.numeroCartao = numeroCartao;
	}

	public Double getValorPagamento() {
		return valorPagamento;
	}

	public void setValorPagamento(Double valorPagamento) {
		this.valorPagamento = valorPagamento;
	}

	public StatusPagamentoEnum getStatusPagamento() {
		return statusPagamento;
	}

	public void setStatusPagamento(StatusPagamentoEnum statusPagamento) {
		this.statusPagamento = statusPagamento;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pagamento other = (Pagamento) obj;
		return Objects.equals(id, other.id);
	}
	
	
}
