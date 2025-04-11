package com.intern.backendettaba.designpattern.ProductState;

import com.intern.backendettaba.entities.Product;
import com.intern.backendettaba.enums.Etat;

public interface ProductState {
    boolean canUpdate();


    boolean canDelete();

    //void nextState(Product product);

    Etat getEtatEnum();
}
