import java.util.Date;
class Chicken extends Animal {
    private int eggsPerWeek;

    public Chicken(String name, AnimalType type, String description, int age, float price, Date creationDate, Date boughtDate, int eggsPerWeek) {
        super(name, type, description, age, price, creationDate, boughtDate);
        this.eggsPerWeek = eggsPerWeek;
    }

    @Override
    public float calculateFeedingCost() {
        return 2.5f * getAge(); // Exemple arbitraire
    }
}