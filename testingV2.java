package ConstrucTestingNoah;

import java.util.Arrays;

public class testingV2 {
    public long startTime;
    public long finishTime;

    public Places construct(Places places, double radius, double response) {
        startTime = System.nanoTime();
        int placeCount = places.size();

        if(places.size() == 0) {return places;}

        GreatCircleDistance distance = new GreatCircleDistance();

        Place[] tour = new Place[placeCount];
        Place[] tourFinal = new Place[placeCount];
        createTwoPlaceArray(places, tour, tourFinal);
        long Shortest = Long.MAX_VALUE;

        /*For all the different starting locations*/
        for (int startingDex = 0; startingDex < placeCount; startingDex++) {
            long totalDistance;
            totalDistance = createSortedPlaceArray(startingDex, tour, distance, radius);
            if (totalDistance < Shortest) {
                Shortest = totalDistance;
                tourFinal = tour; // ?May make reference?
            }
        }

        finishTime = System.nanoTime();
        return finalizeArray(tourFinal, places.get(0));
    }    

    /**
     * createSortedPlaceArray
     * 
     * create a new sorted tour
     * as well as compute and return its total distance
     * 
     * @return long ; total distance of new tour
     */
    public long createSortedPlaceArray(int startIndex, Place[] tour, GreatCircleDistance distance, double radius) {
        long tourDistance = 0L;
        int unvisited = 0;

        swap(unvisited, startIndex, tour);
        unvisited++;

        while (unvisited < tour.length) {
            int nextClosestIndex = getNext(Arrays.copyOfRange(tour, unvisited, tour.length-1), tour[unvisited-1], distance, radius);
            tourDistance += distance.between(tour[unvisited-1], tour[nextClosestIndex], radius);
            swap(unvisited, nextClosestIndex, tour);
            unvisited++;
        }
        
        return tourDistance;
    }

    /**
     * getNext
     * 
     * find the next closest place and return it's index
     * 
     * @return int ; index of next closest
    */
    private int getNext(Place[] unvisitedPlaces, Place currentPlace, GreatCircleDistance distance, double radius) {
        long shortest = Long.MAX_VALUE;
        int shortestIndex = -1;

        if (unvisitedPlaces.length == 0){return 0;}

        for(int i = 0; i < unvisitedPlaces.length; i++) {
            long currentDist = (distance.between(currentPlace, unvisitedPlaces[i], radius));
            if (currentDist < shortest) {
                shortest = currentDist;
                shortestIndex = i;
            }
        }
        return shortestIndex;
    }

    /**
     * swap
     * 
     * swap next closest value to in-order position
     * 
     * @param nextClosest ; next in the tour
     * @param unvisited ; pointer to front of tour
     */
    private void swap(int unvisited, int nextClosest, Place[] tour) {
        Place temp = tour[unvisited];
        tour[unvisited] = tour[nextClosest];
        tour[nextClosest] = temp;
    }

    /**
     * Used to reduce time complexity of making
     * two identical arrays based off of a Places obj
     */
    public void createTwoPlaceArray(Places places, Place[] array1, Place[] array2) {
        Place[] unsorted = new Place[places.size()];

        for(int i=0; i<unsorted.length; i++){
            array1[i] = places.get(i);
            array2[i] = places.get(i);
        }
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
}