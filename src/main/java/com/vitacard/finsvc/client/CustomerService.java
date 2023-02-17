package com.vitacard.finsvc.client;

import customer.InternalCreateCustomerRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.JSONStringConverter;

@Service
public class CustomerService {
    @Autowired
    private CustomerSvcClient customerSvcClient;

    public String addCustomer(InternalCreateCustomerRequestDto internalCreateCustomerRequestDto) {
        return customerSvcClient.addCustomer(JSONStringConverter.toJSONString(internalCreateCustomerRequestDto));
    }
}
