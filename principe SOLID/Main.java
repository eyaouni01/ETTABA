public class Main {
    public static void main(String[] args) {
        // Création des objets
        Animal chicken = new Chicken("Poulette", AnimalType.POULTRY, "Pondeuse", 2, 15.0f, new Date(), new Date(), 20);
        Animal cow = new Cow("Marguerite", AnimalType.BOVINE, "Donneuse de lait", 4, 500.0f, new Date(), new Date(), 10.5f);

        // Affichage du coût d’alimentation
        printFeedingCost(chicken);
        printFeedingCost(cow);
    }

    public static void printFeedingCost(Animal animal) {
        System.out.println("Animal: " + animal.getName());
        System.out.println("Feeding cost: " + animal.calculateFeedingCost() + "€\n");
    }
}