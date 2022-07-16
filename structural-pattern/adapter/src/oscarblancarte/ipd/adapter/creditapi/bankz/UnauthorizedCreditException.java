package oscarblancarte.ipd.adapter.creditapi.bankz;

import java.math.BigDecimal;

public class UnauthorizedCreditException extends Exception {

    public UnauthorizedCreditException(String customer, BigDecimal amount){
        super(String.format("The request credit amount (%s) has not be approved for customer %s",
                amount.toString(), customer));
    }
}
