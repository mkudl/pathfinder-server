package pl.lodz.p.pathfinder.serv.model.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import pl.lodz.p.pathfinder.serv.model.PointOfInterest;

/**
 * Created by QDL on 2017-04-07.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PoiInboundJsonWrapper
{
    private String idToken;

    private PointOfInterest pointOfInterest;





    public PoiInboundJsonWrapper()
    {
    }

    public PoiInboundJsonWrapper(String idToken, PointOfInterest pointOfInterest)
    {
        this.idToken = idToken;
        this.pointOfInterest = pointOfInterest;
    }

    public String getIdToken()
    {
        return idToken;
    }

    public void setIdToken(String idToken)
    {
        this.idToken = idToken;
    }

    public PointOfInterest getPointOfInterest()
    {
        return pointOfInterest;
    }

    public void setPointOfInterest(PointOfInterest pointOfInterest)
    {
        this.pointOfInterest = pointOfInterest;
    }
}
