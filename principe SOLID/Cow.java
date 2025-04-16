class Cow extends Animal {
    private float milkPerDay;

    public Cow(String name, AnimalType type, String description, int age, float price, Date creationDate, Date boughtDate, float milkPerDay) {
        super(name, type, description, age, price, creationDate, boughtDate);
        this.milkPerDay = milkPerDay;
    }

    @Override
    public float calculateFeedingCost() {
        return 10.0f + (milkPerDay * 0.5f);
    }
}