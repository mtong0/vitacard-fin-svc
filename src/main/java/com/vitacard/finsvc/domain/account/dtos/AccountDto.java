package com.vitacard.finsvc.domain.account.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record AccountDto(
        @NotBlank String id,
        @NotBlank String customerId
) {
}
