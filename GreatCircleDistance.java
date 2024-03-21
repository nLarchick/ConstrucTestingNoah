package ConstrucTestingNoah;
public class GreatCircleDistance {
    public long between (GeographicCoordinate placeOne, GeographicCoordinate placeTwo, double radius)
    {
        double lonDelta = delta(placeOne.lonRadians(), placeTwo.lonRadians());
        double angle = angle(placeOne.latRadians(), placeTwo.latRadians(), lonDelta);
        return Math.round(distance(angle, radius));
    }

    /**
     * Calculate the numerator used in the arctan2 operation.
     * 
     * @param latOne The first latitude, in radians.
     * @param latTwo The second latitude, in radians.
     * @param delta The change in longitude between the two coordinates.
     */
    public double numerator(double latOne, double latTwo, double delta)
    {
        // The left hand side of the addition argument under the square root
        double leftHand = Math.pow(Math.cos(latTwo) * Math.sin(delta), 2);

        // The right hand side of the addition argument under the square root
        double rightHand = Math.pow(
            (Math.cos(latOne) * Math.sin(latTwo)) - 
            (Math.sin(latOne) * Math.cos(latTwo) * Math.cos(delta)),
            2
        );

        return Math.sqrt(leftHand + rightHand);
    }

    public double denominator(double latOne, double latTwo, double delta)
    {
        return (Math.sin(latOne) * Math.sin(latTwo)) + (Math.cos(latOne) * Math.cos(latTwo) * Math.cos(delta));
    }

    public double angle(double latOne, double latTwo, double delta)
    {
        return Math.atan2(numerator(latOne, latTwo, delta), denominator(latOne, latTwo, delta));
    }

    public double delta(double lonOne, double lonTwo)
    {
        return Math.abs(lonTwo - lonOne);
    }

    public double distance(double angle, double radius)
    {
        return angle * radius;
    }
}
