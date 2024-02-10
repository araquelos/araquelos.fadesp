package com.fadesp.dt.controllers;

import java.util.List;
import java.util.Optional;
import jakarta.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fadesp.dt.dtos.PagamentoRecordDto;
import com.fadesp.dt.enums.MetodosPagamentoEnum;
import com.fadesp.dt.enums.StatusPagamentoEnum;
import com.fadesp.dt.models.Pagamento;
import com.fadesp.dt.repositories.PagamentoRepository;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {
	
	@Autowired
	private PagamentoRepository pagamentoRepository; 
	
	/*Requisito 1: A API deve ser capaz de receber um pagamento, e este deverá
	 *ser armazenado no banco de dados com status Pendente de Processamento.*/
	
	@PostMapping	
	public ResponseEntity<Object> adicionar(@RequestBody Pagamento pagamento) {
		
		if((pagamento.getMetodoPagamento() == MetodosPagamentoEnum.CARTAO_CREDITO ||
		    pagamento.getMetodoPagamento() == MetodosPagamentoEnum.CARTAO_DEBITO) && 
		    pagamento.getNumeroCartao() == "")
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Número do cartão é obrigatório quando o método de pagamento for cartão de débito ou crédito.");
		}
		pagamento.setStatusPagamento(StatusPagamentoEnum.PENDENTE_PROCESSAMENTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(pagamentoRepository.save(pagamento));	
	}
	
	/*Requisito 2: A API deve ser capaz de atualizar o status de um pagamento.*/
	
	@PutMapping("/{id}")
	public ResponseEntity<Object> atualizarStatus(@PathVariable("id") Long id, 
			                         @RequestBody @Valid PagamentoRecordDto pagamentoRecordDto) {
		
		Optional<Pagamento> pagamentoSelecionado = pagamentoRepository.findById(id);
		
		if(pagamentoSelecionado.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pagamento não encontrado.");
		}
		
		Pagamento pagamentoAtualizado = pagamentoSelecionado.get();
		
		if(pagamentoSelecionado.get().getStatusPagamento() == StatusPagamentoEnum.PENDENTE_PROCESSAMENTO && 
		   pagamentoRecordDto.statusPagamento() == StatusPagamentoEnum.PENDENTE_PROCESSAMENTO) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Este pagamento já está pendente de processamento.");
		}
		if(pagamentoSelecionado.get().getStatusPagamento() == StatusPagamentoEnum.PROCESSADO_SUCESSO) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Este pagamento já foi processado e não pode mais ser alterado.");
		}
		if(pagamentoSelecionado.get().getStatusPagamento() == StatusPagamentoEnum.PROCESSADO_FALHA &&
		   pagamentoRecordDto.statusPagamento() != StatusPagamentoEnum.PENDENTE_PROCESSAMENTO) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Este pagamento só pode ser alterado para pendente de processamento.");
		}
		
		BeanUtils.copyProperties(pagamentoRecordDto, pagamentoAtualizado);
		return ResponseEntity.status(HttpStatus.OK).body(pagamentoRepository.save(pagamentoAtualizado));
	
	}
	
	/*Requisito 3: A API deve ser capaz de listar todos os pagamentos recebidos e
	 *oferecer filtros de busca para o cliente.*/
	
	@GetMapping
	public ResponseEntity<Object> listar() {

		List<Pagamento> todosPagamentos = pagamentoRepository.findAll();
		
		if(todosPagamentos.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi encontrado nenhum pagamento.");
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(todosPagamentos);
	
	}
	
	@GetMapping("/{keyword}")
	public ResponseEntity<Object> filtrar(@PathVariable("keyword")String keyword) {
		
		 List<Pagamento> pagamentosFiltrados = pagamentoRepository.filtrar(keyword);
		
		if(pagamentosFiltrados.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi encontrado nenhum pagamento.");		
		}
	
		return ResponseEntity.status(HttpStatus.OK).body(pagamentosFiltrados);
	}
	
	/*Requisito 4: A API deve ser capaz de deletar um pagamento,
	 *desde que este ainda esteja com status Pendente de Processamento.*/
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> excluir(@PathVariable("id") Long id) {
		Optional<Pagamento> pagamentoSelecionado = pagamentoRepository.findById(id);
		if(pagamentoSelecionado.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pagamento não encontrado.");
		}
		if(pagamentoSelecionado.get().getStatusPagamento() != StatusPagamentoEnum.PENDENTE_PROCESSAMENTO) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pagamento já foi processado e não pode mais ser excluído.");
		}		
		pagamentoRepository.delete(pagamentoSelecionado.get());
		return ResponseEntity.status(HttpStatus.OK).body("Pagamento excluído com sucesso.");
	}

}
