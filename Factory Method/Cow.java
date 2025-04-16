public class Cow extends Animal {
    private float milkPerDay;

    public float getMilkPerDay() { return milkPerDay; }
    public void setMilkPerDay(float milkPerDay) { this.milkPerDay = milkPerDay; }

    @Override
    public float calculateFeedingCost() {
        return 15.0f * getAge() / 10; 
    }
}