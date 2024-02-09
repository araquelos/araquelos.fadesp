package com.fadesp.dt.controller;

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
import com.fadesp.dt.repository.PagamentoRepository;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {
	
	@Autowired
	private PagamentoRepository pagamentoRepository; 
	
	@GetMapping("/listar")
	public List<Pagamento> listar() {
		return pagamentoRepository.findAll();
	}
	
	@GetMapping("/listar/{id}")
	public Optional<Pagamento> filtrarPorCodigoDebito(@PathVariable("id")Long id) {			
		return pagamentoRepository.findById(id);
	}
	
	@PostMapping	
	public ResponseEntity<Object> adicionar(@RequestBody Pagamento pagamento) {
		
		if((pagamento.getMetodoPagamento() == MetodosPagamentoEnum.CARTAO_CREDITO ||
		    pagamento.getMetodoPagamento() == MetodosPagamentoEnum.CARTAO_DEBITO) && 
		    pagamento.getNumeroCartao() == "")
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Número do cartão é obrigatório quando o método de pagamento for cartão de débito ou crédito.");
		}
		else return ResponseEntity.status(HttpStatus.CREATED).body(pagamentoRepository.save(pagamento));	
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Object> atualizarStatus(@PathVariable("id") Long id, 
			                         @RequestBody @Valid PagamentoRecordDto pagamentoRecordDto) {
		Optional<Pagamento> pagamentoSelecionado = pagamentoRepository.findById(id);
		
		if(pagamentoSelecionado.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pagamento não encontrado.");
		}
		
		var pagamentoAtualizado = pagamentoSelecionado.get();			
		BeanUtils.copyProperties(pagamentoRecordDto, pagamentoAtualizado);
		
		/*if(pagamentoSelecionado.get().getStatusPagamento() == StatusPagamentoEnum.PROCESSADO_SUCESSO) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Este pagamento já foi processado e não pode mais ser alterado.");
		}
		else if(pagamentoSelecionado.get().getStatusPagamento() == StatusPagamentoEnum.PENDENTE_PROCESSAMENTO && pagamentoRecordDto.statusPagamento() == StatusPagamentoEnum.PENDENTE_PROCESSAMENTO) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Este pagamento já está em processamento.");
		} else if(pagamentoSelecionado.get().getStatusPagamento() == StatusPagamentoEnum.PROCESSADO_FALHA && pagamentoRecordDto.statusPagamento() != StatusPagamentoEnum.PENDENTE_PROCESSAMENTO) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Este pagamento só pode ser alterado para pendente de processamento.");
		}
		
		else return ResponseEntity.status(HttpStatus.OK).body(pagamentoRepository.save(pagamentoAtualizado));*/
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(pagamentoSelecionado.get().getStatusPagamento()+"/"+pagamentoAtualizado.getStatusPagamento()+"/"+pagamentoRecordDto.statusPagamento());
	}
	
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
