package com.fiserv.poc.report.model;

import java.util.Date;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Transaction {


    public String transactionId;
    public String merchantId;
    public Date transactionDate;
    public String cardNumber;

}
