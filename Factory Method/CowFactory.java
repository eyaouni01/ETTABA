
public class CowFactory extends AnimalFactory {
    @Override
    public Animal createAnimal(String name, int age) {
        Cow cow = new Cow();
        cow.setName(name);
        cow.setAge(age);
        cow.setType(AnimalType.BOVINE);
        cow.setPrice(1200.0f);
        cow.setMilkPerDay(25.5f); 
        return cow;
    }
}