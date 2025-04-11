package com.intern.backendettaba.designpattern.ProductState;

import com.intern.backendettaba.entities.Product;
import com.intern.backendettaba.enums.Etat;

public class ReadyState implements ProductState {
    @Override
    public boolean canUpdate() {
        return false;
    }

    @Override
    public boolean canDelete() {
        return false;
    }

    @Override
    public Etat getEtatEnum() {
        return Etat.READY;
    }

}
