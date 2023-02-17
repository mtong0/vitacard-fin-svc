package com.vitacard.finsvc.commons;

import com.vitacard.finsvc.commons.unitevents.UnitTransactionCreatedEvent;
import lombok.Getter;
import unit.UnitResponse;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;

@Getter
public class UnitCommand {
    private UnitResponse body;
    private String type;

    public UnitCommand(String body) {
        this.body = new UnitResponse(body);
        type = this.body.getData().get("type").getAsString();
    }

    public static DomainEvent getEvent(UnitCommand unitCommand) {
        String type = unitCommand.getType();
        return Match(type).of(
                Case($("transaction.created"), ()-> UnitTransactionCreatedEvent.create(unitCommand.body.getOnly()))
        );
    }
}
