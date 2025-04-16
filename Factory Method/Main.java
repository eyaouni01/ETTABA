
public class Main {
	 public static void main(String[] args) {
	        
	        AnimalFactory cowFactory = new CowFactory();
	        Animal cow = cowFactory.getAnimal("Daisy", 4);
	        System.out.println("Feeding cost for cow: " + cow.calculateFeedingCost());

	        
	        AnimalFactory chickenFactory = new ChickenFactory();
	        Animal chicken = chickenFactory.getAnimal("Clucky", 1);
	        System.out.println("Feeding cost for chicken: " + chicken.calculateFeedingCost());
	    }
}
