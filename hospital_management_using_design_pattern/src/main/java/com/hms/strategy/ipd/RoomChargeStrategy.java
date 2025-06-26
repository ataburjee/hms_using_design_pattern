package com.hms.strategy.ipd;

import java.time.LocalDateTime;

public interface RoomChargeStrategy {
    double calculateCharge(LocalDateTime admitTime, LocalDateTime dischargeTime);
}
