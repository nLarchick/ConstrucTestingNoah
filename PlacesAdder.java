package ConstrucTestingNoah;
import java.util.Random;

public class PlacesAdder {

    public void AddPlace(Places places) {
        Random randy = new Random();
        double min = -100.0; double max = 100.0;

        double randomLat = min + (max - min) * randy.nextDouble();
        double randomLon = min + (max - min) * randy.nextDouble();

        Place toAdd = new Place(randomLat, randomLon);
        places.add(toAdd);
    }
}
