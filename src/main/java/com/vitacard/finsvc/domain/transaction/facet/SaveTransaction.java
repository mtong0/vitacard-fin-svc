package com.vitacard.finsvc.domain.transaction.facet;

import com.vitacard.finsvc.client.UnitWebClient;
import com.vitacard.finsvc.domain.transaction.dtos.UnitCardTransaction;
import com.vitacard.finsvc.domain.transaction.dtos.UnitTransaction;
import com.vitacard.finsvc.domain.transaction.infrasturcture.ITransactionRepository;
import com.vitacard.finsvc.domain.transaction.model.CardTransaction;
import com.vitacard.finsvc.domain.transaction.model.ITransactionFactory;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unit.UnitResponse;
import unit.UnitResponseData;

@Service
public class SaveTransaction implements ISaveTransaction {
    @Autowired
    private UnitWebClient unitWebClient;
    @Autowired
    private ITransactionRepository transactionRepository;
    @Autowired
    private ITransactionFactory transactionFactory;

    @Override
    public UnitTransaction handle(String accountId, String transactionId) {
        UnitResponseData unitResponseData = new UnitResponse(unitWebClient.getTransactionById(accountId, transactionId)).getOnly();
        String type = unitResponseData.type();
        return Match(type).of(
                Case($(UnitCardTransaction.TYPE), ()->this.handle(unitResponseData))
        );
    }

    @Override
    public UnitCardTransaction handle(UnitResponseData unitResponseData) {
        UnitCardTransaction unitCardTransaction = new UnitCardTransaction(unitResponseData);
        CardTransaction cardTransaction = transactionFactory.create(unitCardTransaction);
        transactionRepository.addTransaction(cardTransaction);
        return unitCardTransaction;
    }
}
