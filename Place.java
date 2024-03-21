package ConstrucTestingNoah;
import java.lang.Math;
import java.util.LinkedHashMap;

public class Place extends LinkedHashMap<String, String> implements GeographicCoordinate
{

    /**
     * Constructor.
     * @param latitude The geographic latitude for this location, in degrees.
     * @param longitude The geographic longitude for this location, in degrees.
     */
    Place(){}
    public Place(Double latitude, Double longitude)
    {
        this.put("latitude", latitude.toString());
        this.put("longitude", longitude.toString());
    }

    /**
     * {@inheritDoc}
     */
    public Double latRadians()
    {
        return Math.toRadians(Double.parseDouble(this.get("latitude")));
    }

    /**
     * {@inheritDoc}
     */
    public Double lonRadians()
    {
        return Math.toRadians(Double.parseDouble(this.get("longitude")));
    }
}
