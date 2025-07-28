package org.nastya.dto;

import lombok.*;

import java.math.BigDecimal;

@Value
public class CurrentBalanceDTO {
    BigDecimal balance;
}