package com.vitacard.finsvc.domain.account.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder(toBuilder = true)
@Getter()
@Setter()
@AllArgsConstructor
public class CreateDepositAccountCommand {
    String relationType;
    String relationId;
    String accountType;
    String depositProduct;
}
