package com.hms.strategy.ipd;

import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class GeneralWardChargeStrategy implements RoomChargeStrategy {
    @Override
    public double calculateCharge(LocalDateTime admitTime, LocalDateTime dischargeTime) {
        long days = Math.max(Duration.between(admitTime, dischargeTime).toDays(), 1);
        return days * 500.0; // ₹500 per day
    }
}
