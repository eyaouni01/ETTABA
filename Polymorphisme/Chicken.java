public class Chicken extends Animal {
    private int eggsPerWeek;
    
    public Chicken(String name, int age, float price, int eggsPerWeek) {
        super(name, age, AnimalType.POULTRY, price);
        this.eggsPerWeek = eggsPerWeek;
    }
    
    public int getEggsPerWeek() { return eggsPerWeek; }
    public void setEggsPerWeek(int eggsPerWeek) { this.eggsPerWeek = eggsPerWeek; }
    
    @Override
    public float calculateFeedingCost() {
        return 2.5f * getAge() / 10;
    }
}