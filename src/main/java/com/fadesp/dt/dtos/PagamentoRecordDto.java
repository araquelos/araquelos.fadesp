package com.fadesp.dt.dtos;

import com.fadesp.dt.enums.StatusPagamentoEnum;

import jakarta.validation.constraints.NotNull;

public record PagamentoRecordDto(@NotNull StatusPagamentoEnum statusPagamento) {

}
