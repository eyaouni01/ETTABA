package com.intern.backendettaba.designpattern.ProductState;

import com.intern.backendettaba.entities.Product;
import com.intern.backendettaba.enums.Etat;

public class InProgressState implements ProductState {

    @Override
    public boolean canUpdate() {
        return true; // Modifications d'un produit en état INPROGRESS
    }

    @Override
    public boolean canDelete() {
        return false; // Impossible de supprimer un produit en état INPROGRESS
    }
    @Override
    public Etat getEtatEnum() {
        // Retourner l'état INPROGRESS
        return Etat.INPROGRESS;
    }
}
