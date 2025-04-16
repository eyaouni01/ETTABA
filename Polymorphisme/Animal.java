import java.util.Date;

public abstract class Animal {
    private String name;
    private AnimalType type;
    private String description;
    private int age;
    private float price;
    private Date creationDate;
    private Date boughtDate;
    
    // Constructeur
    public Animal(String name, int age, AnimalType type, float price) {
        this.name = name;
        this.age = age;
        this.type = type;
        this.price = price;
        this.creationDate = new Date();
    }
    
    // Getters et setters
    public String getName() { return name; }
    public int getAge() { return age; }
    public AnimalType getType() { return type; }
    public float getPrice() { return price; }
    public Date getCreationDate() { return creationDate; }
    public Date getBoughtDate() { return boughtDate; }
    public String getDescription() { return description; }
    
    public void setName(String name) { this.name = name; }
    public void setAge(int age) { this.age = age; }
    public void setType(AnimalType type) { this.type = type; }
    public void setPrice(float price) { this.price = price; }
    public void setCreationDate(Date date) { this.creationDate = date; }
    public void setBoughtDate(Date date) { this.boughtDate = date; }
    public void setDescription(String description) { this.description = description; }
    
    // Méthode abstraite
    public abstract float calculateFeedingCost();
}
