public class ChickenFactory extends AnimalFactory {
    @Override
    public Animal createAnimal(String name, int age) {
        Chicken chicken = new Chicken();
        chicken.setName(name);
        chicken.setAge(age);
        chicken.setType(AnimalType.POULTRY);
        chicken.setPrice(15.0f);
        chicken.setEggsPerWeek(5); 
        return chicken;
    }
}