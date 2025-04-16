import java.util.Date;

public abstract class AnimalFactory {
    public abstract Animal createAnimal(String name, int age);

    public Animal getAnimal(String name, int age) {
        Animal animal = createAnimal(name, age);
        animal.setCreationDate(new Date());
        return animal;
    }
}