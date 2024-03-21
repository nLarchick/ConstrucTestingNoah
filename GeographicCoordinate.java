package ConstrucTestingNoah;
/**
 * Used to describe a geographic position in the world.
 */
public interface GeographicCoordinate
{
    /**
     * Get the latitude for this position.
     * 
     * @return The latitude, in radians.
     */
    public Double latRadians();

    /**
     * Get the longitude for this position.
     * 
     * @return the longitude, in radians.
     */
    public Double lonRadians();
}