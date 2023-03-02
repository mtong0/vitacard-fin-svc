package com.vitacard.finsvc.domain.transaction.dtos;

import lombok.Builder;

@Builder
public record CardTransactionDto(String customerId, long amount, String merchantType) {
}
