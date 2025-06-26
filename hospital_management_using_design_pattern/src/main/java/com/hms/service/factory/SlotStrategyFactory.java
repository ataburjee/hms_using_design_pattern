package com.hms.service.factory;

import com.hms.enums.opd.DoctorType;
import com.hms.strategy.opd.SlotCreationStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SlotStrategyFactory {

    private final Map<String, SlotCreationStrategy> policies;

    @Autowired
    public SlotStrategyFactory(List<SlotCreationStrategy> strategyList) {
        this.policies = new HashMap<>();
        for (SlotCreationStrategy policy : strategyList) {
            String key = policy.getClass().getAnnotation(Component.class).value();
            policies.put(key, policy);
        }
    }

    public SlotCreationStrategy getStrategy(DoctorType doctorType) {
        return policies.get(doctorType.name());
    }
}
