package com.shabuldaev.domain;

import lombok.Data;
import java.sql.Timestamp;
import java.math.BigDecimal;

@Data
public class Bookings {
    private final String bookRef;
    private final Timestamp bookDate;
    private final BigDecimal totalAmount;
}