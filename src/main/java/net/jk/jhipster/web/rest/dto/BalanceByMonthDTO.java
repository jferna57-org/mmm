package net.jk.jhipster.web.rest.dto;

import java.util.List;

/**
 * Created by jk on 19/01/16.
 */
public class BalanceByMonthDTO {

    List<String> month;

    List<Double> amount;

    public List<String> getMonth() {
        return month;
    }

    public void setMonth(List<String> month) {
        this.month = month;
    }

    public List<Double> getAmount() {
        return amount;
    }

    public void setAmount(List<Double> amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "BalanceByMonthDTO{" +
            "month=" + month +
            ", amount=" + amount +
            '}';
    }
}
