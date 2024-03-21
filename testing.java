package ConstrucTestingNoah;
import java.util.Arrays;
import java.util.stream.LongStream;

public class testing {
    static long startTime;
    static long totalTime;
    static long newStartTime;
    static long sortArrayTime;

    public Places construct(Places places, double radius, double response)
    {
        startTime = System.nanoTime();

        if(places.size() == 0) {
            return places;
        }

        GreatCircleDistance distance = new GreatCircleDistance();
        
        int placeCount = places.size();
        Place[] unsorted = createUnsortedPlaceArray(places);
        Place[] sorted = new Place[placeCount];
        long[] distances = new long[placeCount];

        Place[] picked = new Place[placeCount];
        long pickedDistance = -1L;

        for(int i=0; i<placeCount; i++)
        {
            long newStartend = System.nanoTime();
            if (i % 1 == 0){System.out.println("New start array " + i + ", time@ =[" + (double)(newStartend - startTime)/ 1_000_000_000.0 + "]\n");}

            long totalDistance = createSortedPlaceArray(unsorted, sorted, distances, distance, radius, i);

            if(pickedDistance < 0 || totalDistance < pickedDistance)
            {
                picked = sorted.clone();
                pickedDistance = totalDistance;
            }
        }

        Places finalPlaces = finalizeArray(picked, places.get(0));

        long endTime = System.nanoTime();
        totalTime = (endTime - startTime);
        return finalPlaces;
    }

    public Place pull(int index, long distance, Place[] places, long[] distances)
    {
        distances[index] = distance;
        return places[index];
    }

    public Place getNext(Place target, Place[] places, long[] distances, GreatCircleDistance distance, double radius)
    {
        long shortestDistance = Long.MAX_VALUE;
        int shortestIndex = -1;
        for(int i=0; i<places.length; i++)
        {
            if(distances[i] >= 0) continue;

            long currentDistance = distance.between(target, places[i], radius);
            if(currentDistance < shortestDistance)
            {
                shortestDistance = currentDistance;
                shortestIndex = i;
            }
        }

        return pull(shortestIndex, shortestDistance, places, distances);
    }

    /**
     * Creates an unsorted array of Place objects from a Places object.
     * 
     * @param places The places to create an unsorted array from.
     * @return An array of place objects with the same members as places.
     */
    public Place[] createUnsortedPlaceArray(Places places)
    {
        Place[] unsorted = new Place[places.size()];

        for(int i=0; i<unsorted.length; i++)
        {
            unsorted[i] = places.get(i);
        }

        return unsorted;
    }

    /**
     * Creates a sorted array of Place objects from an unsorted array.
     * 
     * @param unsorted ; The places to create an unsorted array from.
     * @return The total distance in the sorted array.
     */
    public long createSortedPlaceArray(Place[] unsorted, Place[] sorted, long[] distances, GreatCircleDistance distance, double radius, int start){
        
        Arrays.fill(distances, -1);
        sorted[0] = pull(start, Long.MAX_VALUE, unsorted, distances);

        for (int i = 1; i < unsorted.length; i++) {

            long sortArrayEnd = System.nanoTime();
            if ((i % 40) == 0){System.out.println("Sorting array " + i + ", time@ =[" + (double)(sortArrayEnd - startTime)/ 1_000_000_000.0 + "]");}

            Place previous = sorted[i-1];
            sorted[i] = getNext(previous, unsorted, distances, distance, radius);
        }

        // Fill in the final distance
        long finalDistance = distance.between(sorted[0], sorted[sorted.length - 1], radius);
        distances[start] = finalDistance;
        
        return LongStream.of(distances).sum();
    }

    /**
     * Finalizes an array of Place objects into a Places object.
     * 
     * @param array The array of place objects.
     * @param startingPoint the Place which should be at index 0.
     * @return A places object containing the values in the given array.
     */
    public Places finalizeArray(Place[] array, Place startingPoint)
    {
        Places places = new Places(array.length);

        // Find the index of the starting place
        int start = -1;
        for(int i=0; i<array.length; i++)
        {
            if(array[i] == startingPoint)
            {
                start = i;
                break;
            }
        }

        // Two loops, one for the starting index and after, and one from 0 to start
        for(int i=start; i<array.length; i++)
        {
            places.add(array[i]);
        }
        for(int i=0; i<start; i++)
        {
            places.add(array[i]);
        }

        return places;
    }

    /*Run to get a teset for i = # of places*/
    // Uses random lat and long values for the places
    // time is checked for each new start loop
    // and for each time the places is actually sorted
    public static void main(String[] args) {
        testing test = new testing();
        PlacesAdder adder = new PlacesAdder();
        Places places = new Places();

        for (int i = 0; i < 83; i++) {
            adder.AddPlace(places);
        }

        System.out.println("Number of places =[" + places.size() + "]\n");
        Places placesSorted = test.construct(places, 3959, 1);

        double elapsedTimeInSeconds = (double) totalTime / 1_000_000_000.0;

        System.out.println("Total run time =[" + elapsedTimeInSeconds + "]\n");

        System.out.println("Number of places (after sort) =[" + placesSorted.size() + "]");
    }
}