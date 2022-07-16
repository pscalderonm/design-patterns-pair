package oscarblancarte.ipd.adapter.impl;

import oscarblancarte.ipd.adapter.creditapi.bankz.UnauthorizedCreditException;
import oscarblancarte.ipd.adapter.creditapi.bankz.ZBankApiClient;
import oscarblancarte.ipd.adapter.creditapi.bankz.ZBankCreditRequest;

import java.math.BigDecimal;

public class ZBankCreditAdapter implements IBankAdapter {

    @Override
    public BankCreditResponse sendCreditRequest(BankCreditRequest request) {

        ZBankCreditRequest zRequest = new ZBankCreditRequest();
        zRequest.setCustomer(request.getCustomer());
        zRequest.setAmount(BigDecimal.valueOf(request.getAmount()));

        ZBankApiClient apiClient = new ZBankApiClient();
        BankCreditResponse response = new BankCreditResponse();
        try{
            response.setApproved(apiClient.requestCreditApproval(zRequest));

        }catch(UnauthorizedCreditException uex){
            System.out.printf("[ZBankCreditAdapter-Error]:%s%n", uex.getMessage());
            response.setApproved(false);
        }

        return response;
    }
}
