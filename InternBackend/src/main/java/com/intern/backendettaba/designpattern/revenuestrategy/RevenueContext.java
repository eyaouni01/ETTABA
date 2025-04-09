package com.intern.backendettaba.designpattern.revenuestrategy;

import com.intern.backendettaba.interfaces.RevenueStrategy;

public class RevenueContext {
    private RevenueStrategy strategy;

    public RevenueContext(RevenueStrategy strategy) {
        this.strategy = strategy;
    }

    public float calculer() {
        return strategy.calculerRevenu();
    }
}