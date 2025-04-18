package com.intern.backendettaba.designpattern.PatternObserver;


import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
    public abstract class Subject {
        private List<Observer> observers = new ArrayList<>();

        public void addObserver(Observer observer) {
            observers.add(observer);
        }

        public void removeObserver(Observer observer) {
            observers.remove(observer);
        }

        public void notifyObservers(String eventType, Object data) {
            System.out.println("╔════════════════════════════════╗");
            System.out.println("║  OBSERVER: Email notification  ║");
            System.out.println("║  Envoyé à: {} ║"+ observers.size());
            System.out.println("╚════════════════════════════════╝");
            for (Observer observer : observers) {

                observer.update(eventType, data);
            }
        }
}

