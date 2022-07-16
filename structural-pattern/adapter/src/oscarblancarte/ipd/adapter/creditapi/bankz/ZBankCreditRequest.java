package oscarblancarte.ipd.adapter.creditapi.bankz;

import java.math.BigDecimal;

public class ZBankCreditRequest {
    private String customer;
    private BigDecimal amount;

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
