import java.util.Date;


public abstract class Animal {
    private String name;
    private AnimalType type;
    private String description;
    private int age;
    private float price;
    private Date creationDate;
    private Date BoughtDate;

    // Getters et setters
    public String getName() { return name; }
    public int getAge() { return age; }
    public AnimalType getType() { return type; }
    public float getPrice() { return price; }
    public Date getCreationDate() { return creationDate; }
    public Date getBoughtDate() {
        return BoughtDate;
    }
    public String getDescription() {
        return description;
    }
    public void setName(String name) { this.name = name; }
    public void setAge(int age) { this.age = age; }
    public void setType(AnimalType type) { this.type = type; }
    public void setPrice(float price) { this.price = price; }
    public void setCreationDate(Date date) { this.creationDate = date; }
    public void setBoughtDate(Date date) { this.BoughtDate = date; }
    

    // Méthode abstraite
    public abstract float calculateFeedingCost();
}