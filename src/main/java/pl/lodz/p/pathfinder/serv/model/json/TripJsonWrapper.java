package pl.lodz.p.pathfinder.serv.model.json;

import pl.lodz.p.pathfinder.serv.model.Trip;

/**
 * Created by QDL on 2017-04-07.
 */
public class TripJsonWrapper
{
    private String idToken;


    private Trip trip;


    public TripJsonWrapper()
    {
    }

    public TripJsonWrapper(String idToken, Trip trip)
    {
        this.idToken = idToken;
        this.trip = trip;
    }


    public String getIdToken()
    {
        return idToken;
    }

    public void setIdToken(String idToken)
    {
        this.idToken = idToken;
    }

    public Trip getTrip()
    {
        return trip;
    }

    public void setTrip(Trip trip)
    {
        this.trip = trip;
    }
}
