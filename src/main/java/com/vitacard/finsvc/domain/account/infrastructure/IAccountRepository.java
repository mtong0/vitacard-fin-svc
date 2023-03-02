package com.vitacard.finsvc.domain.account.infrastructure;

import com.vitacard.finsvc.domain.account.model.Account;
import com.vitacard.finsvc.domain.account.model.AccountEvent;
import com.vitacard.finsvc.domain.account.model.AccountEvent.AccountTransactionEvent;
import com.vitacard.finsvc.domain.account.model.DepositAccount;

public interface IAccountRepository {
    Account addAccount(DepositAccount depositAccount);
    Account getAccountById(String id);
    Account publish(AccountEvent event);
}
