import java.util.Date;
abstract class Animal {
    private String name;
    private AnimalType type;
    private String description;
    private int age;
    private float price;
    private Date creationDate;
    private Date boughtDate;

    public Animal(String name, AnimalType type, String description, int age, float price, Date creationDate, Date boughtDate) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.age = age;
        this.price = price;
        this.creationDate = creationDate;
        this.boughtDate = boughtDate;
    }

    // Getters
    public String getName() { return name; }
    public int getAge() { return age; }
    public AnimalType getType() { return type; }
    public float getPrice() { return price; }
    public Date getCreationDate() { return creationDate; }
    public Date getBoughtDate() { return boughtDate; }
    public String getDescription() { return description; }

    // Méthode abstraite
    public abstract float calculateFeedingCost();
}
