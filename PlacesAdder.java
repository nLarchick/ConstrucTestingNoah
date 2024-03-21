package ConstrucTestingNoah;
import java.util.Random;

public class PlacesAdder {
    public static final double min = -150.0;
    public static final double max = 150.0;

    public void AddPlace(Places places) {
        Random randy = new Random();

        double randomLat = min + (max - min) * randy.nextDouble();
        double randomLon = min + (max - min) * randy.nextDouble();

        Place toAdd = new Place(randomLat, randomLon);
        places.add(toAdd);
    }
}
