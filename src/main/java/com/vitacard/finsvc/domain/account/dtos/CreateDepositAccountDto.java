package com.vitacard.finsvc.domain.account.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;


@Builder(toBuilder = true)
public record CreateDepositAccountDto(
        @NotBlank String relationType,
        @NotBlank String relationId,
        @NotBlank String accountType,
        @NotBlank String depositProduct) {
}
