package com.intern.backendettaba.entities;

import com.intern.backendettaba.designpattern.ProductState.InProgressState;
import com.intern.backendettaba.designpattern.ProductState.ProductState;
import com.intern.backendettaba.designpattern.ProductState.ReadyState;
import com.intern.backendettaba.designpattern.ProductState.SeedState;
import com.intern.backendettaba.enums.Etat;
import com.intern.backendettaba.interfaces.RevenueStrategy;
import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    private Etat etat;
    private Float boughtPrice;
    private Float soldPrice;
    @Transient
    private Float profit;
    private Float weight;
    @Column(columnDefinition = "TEXT")
    private String description;

    private LocalDate creationDate;
    private LocalDate boughtDate;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "product_images", joinColumns = { @JoinColumn(name = "product_id") }, inverseJoinColumns = {
            @JoinColumn(name = "image_id") })
    private Set<Image> productImages;

    @ManyToOne
    // Adding the name
    @JoinColumn(name = "ettaba_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    Ettaba ettaba;

    public Set<Image> getImages() {
        return this.productImages;
    }

    // Ajouter cette méthode pour définir les images
    public void setImages(Set<Image> images) {
        this.productImages = images;
    }

    @Transient
    private ProductState currentState;

    public void setCurrentStateFromEnum() {
        switch (this.etat) {
            case SEED:
                this.currentState = new SeedState();
                break;
            case INPROGRESS:
                this.currentState = new InProgressState();
                break;
            case READY:
                this.currentState = new ReadyState();
                break;
            default:
                throw new IllegalStateException("Unknown state: " + this.etat);
        }
    }

    public ProductState getCurrentState() {
        if (this.currentState == null) {
            setCurrentStateFromEnum();
        }
        return this.currentState;
    }

    // Adding Low Coupling (grasp)
    public boolean canBeUpdated() {
        return this.getCurrentState().canUpdate();
    }

    // Adding Low Coupling (grasp)
    public boolean canBeDeleted() {
        return this.getCurrentState().canDelete();
    }

    /*
     * contrainte OCL ici 👇
     * context Produit
     * inv PrixVenteSuperieurPrixAchatSiPret:
     * self.etat = Etat::READY implies self.soldPrice > self.boughtPrice
     * le produit pret ne dois pas etre vendu avec un prix < achat
     */
    @AssertTrue(message = "Pour un produit en état READY, le prix de vente doit être supérieur au prix d'achat.")
    public boolean isSellingPriceValid() {
        if (this.etat == Etat.READY) {
            if (this.soldPrice == null || this.boughtPrice == null) {
                return false;
            }
            return this.soldPrice > this.boughtPrice;
        }
        return true;
    }
}
