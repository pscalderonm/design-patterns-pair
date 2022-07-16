package oscarblancarte.ipd.adapter.creditapi.bankz;

import java.math.BigDecimal;

public class ZBankApiClient {

    private static final BigDecimal MAX_CREDIT_AMOUNT = new BigDecimal(100);

    public boolean requestCreditApproval(ZBankCreditRequest request) throws UnauthorizedCreditException {

        if(request.getAmount().compareTo(MAX_CREDIT_AMOUNT)> 0)
            throw new UnauthorizedCreditException(request.getCustomer(), request.getAmount());

        return true;
    }
}
