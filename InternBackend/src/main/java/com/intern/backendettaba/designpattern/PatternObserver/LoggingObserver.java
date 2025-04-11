package com.intern.backendettaba.designpattern.PatternObserver;

import org.springframework.stereotype.Component;

@Component
public class LoggingObserver implements Observer{
    @Override
    public void update(String eventType, Object data) {
        System.out.println("[LOG] Événement : " + eventType + ", Données : " + data);
    }
}
