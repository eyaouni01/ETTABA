
public class Main {
    public static void main(String[] args) {
        Cow cow = new Cow("Daisy", 4, 1200.0f, 25.5f);
        System.out.println("Feeding cost for cow: " + cow.calculateFeedingCost());
        
        Chicken chicken = new Chicken("Clucky", 1, 15.0f, 5);
        System.out.println("Feeding cost for chicken: " + chicken.calculateFeedingCost());
        
        // Démonstration du polymorphisme
        Animal[] farmAnimals = new Animal[2];
        farmAnimals[0] = cow;
        farmAnimals[1] = chicken;
        
        float totalFeedingCost = 0;
        for (Animal animal : farmAnimals) {
            totalFeedingCost += animal.calculateFeedingCost();
            System.out.println(animal.getName() + " feeding cost: " + animal.calculateFeedingCost());
        }
        
        System.out.println("Total feeding cost for all animals: " + totalFeedingCost);
    }
}