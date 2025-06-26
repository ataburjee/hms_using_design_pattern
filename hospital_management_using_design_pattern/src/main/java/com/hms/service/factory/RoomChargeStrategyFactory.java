package com.hms.service.factory;

import com.hms.enums.ipd.WardType;
import com.hms.strategy.ipd.GeneralWardChargeStrategy;
import com.hms.strategy.ipd.IcuChargeStrategy;
import com.hms.strategy.ipd.PrivateRoomChargeStrategy;
import com.hms.strategy.ipd.RoomChargeStrategy;
import org.springframework.stereotype.Component;

public class RoomChargeStrategyFactory {
    public static RoomChargeStrategy getStrategy(WardType type) {
        return switch(type) {
            case GENERAL -> new GeneralWardChargeStrategy();
            case PRIVATE -> new PrivateRoomChargeStrategy();
            case ICU -> new IcuChargeStrategy();
        };
    }
}
