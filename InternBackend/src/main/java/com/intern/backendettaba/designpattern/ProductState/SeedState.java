package com.intern.backendettaba.designpattern.ProductState;

import com.intern.backendettaba.entities.Product;
import com.intern.backendettaba.enums.Etat;

public class SeedState implements ProductState {
    @Override
    public boolean canUpdate() {
        return true;
    }


    @Override
    public boolean canDelete() {
        return true;
    }

   /*  @Override
    public void nextState(Product product) {
      //  product.setCurrentState(new InProgressState());
        product.setEtat(Etat.INPROGRESS);
    }*/

    @Override
    public Etat getEtatEnum() {
        return Etat.SEED;
    }
}
