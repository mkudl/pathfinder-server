package pl.lodz.p.pathfinder.serv.model.json;

import pl.lodz.p.pathfinder.serv.model.PointOfInterest;

import java.util.List;

/**
 * Created by QDL on 2017-04-07.
 */
public class TripJsonInbound
{
    private int id;
    private String name;
    private String description;
    private List<PointOfInterest> pointOfInterestList;


    public TripJsonInbound()
    {
    }

    public TripJsonInbound(int id, String name, String description, List<PointOfInterest> pointOfInterestList)
    {

        this.id = id;
        this.name = name;
        this.description = description;
        this.pointOfInterestList = pointOfInterestList;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public List<PointOfInterest> getPointOfInterestList()
    {
        return pointOfInterestList;
    }

    public void setPointOfInterestList(List<PointOfInterest> pointOfInterestList)
    {
        this.pointOfInterestList = pointOfInterestList;
    }
}
