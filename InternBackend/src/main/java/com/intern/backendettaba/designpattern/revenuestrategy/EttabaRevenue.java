package com.intern.backendettaba.designpattern.revenuestrategy;

import com.intern.backendettaba.entities.Ettaba;
import com.intern.backendettaba.interfaces.RevenueStrategy;

public class EttabaRevenue implements RevenueStrategy {
    private final Ettaba ettaba;


    public EttabaRevenue(Ettaba ettaba) {
        this.ettaba = ettaba;
    }

    @Override
    public float calculerRevenu() {
        return (float)( ettaba.getHeight()* ettaba.getWidth()*ettaba.getPrice()) ;
    }

}
