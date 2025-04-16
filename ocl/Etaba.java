package ocl;

import java.time.LocalDate;
import java.util.Objects;
/* context Etabla
inv readyDateAfterPlantation: self.plantationDate < self.readyDate
inv positiveHeight: self.height > 0 
inv positiveWidth: self.width > 0
inv positivePrice: self.price > 0 
 */public class Etaba {
    private String name;
    private LocalDate plantationDate;
    private LocalDate readyDate;
    private double height;
    private double width;
    private double price;

    // Constructeur
    public Etaba(String name, LocalDate plantationDate, LocalDate readyDate, 
                double height, double width, double price) {
        setName(name);
        setPlantationDate(plantationDate);
        setReadyDate(readyDate);
        setHeight(height);
        setWidth(width);
        setPrice(price);
    }

    // Getters et Setters avec validation
    public void setReadyDate(LocalDate readyDate) {
        Objects.requireNonNull(readyDate, "La date de maturité ne peut pas être nulle");
        if (plantationDate != null && !readyDate.isAfter(plantationDate)) {
            throw new IllegalArgumentException("La date de maturité doit être après la date de plantation");
        }
        this.readyDate = readyDate;
    }

    public void setPlantationDate(LocalDate plantationDate) {
        Objects.requireNonNull(plantationDate, "La date de plantation ne peut pas être nulle");
        if (readyDate != null && !plantationDate.isBefore(readyDate)) {
            throw new IllegalArgumentException("La date de plantation doit être avant la date de maturité");
        }
        this.plantationDate = plantationDate;
    }

    public void setHeight(double height) {
        if (height <= 0) {
            throw new IllegalArgumentException("La hauteur doit être positive");
        }
        this.height = height;
    }

    public void setWidth(double width) {
        if (width <= 0) {
            throw new IllegalArgumentException("La largeur doit être positive");
        }
        this.width = width;
    }

    public void setPrice(double price) {
        if (price <= 0) {
            throw new IllegalArgumentException("Le prix doit être positif");
        }
        this.price = price;
    }

   

    // Getters standards
    public String getName() { return name; }
    public LocalDate getPlantationDate() { return plantationDate; }
    public LocalDate getReadyDate() { return readyDate; }
    public double getHeight() { return height; }
    public double getWidth() { return width; }
    public double getPrice() { return price; }
    
    public void setName(String name) {
        this.name = Objects.requireNonNull(name, "Le nom ne peut pas être nul");
    }
}