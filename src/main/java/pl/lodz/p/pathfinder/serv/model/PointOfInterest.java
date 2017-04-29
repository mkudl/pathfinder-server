package pl.lodz.p.pathfinder.serv.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.List;

/**
 * Created by QDL on 2017-04-05.
 */

@Entity
@Table(name="pois")
public class PointOfInterest
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    @NaturalId
    private String googleID; //ID of the places in Google APIs

    @ManyToOne
    @JsonIgnore
    private User createdByUser; //ID of the Google user that created this poi

    public PointOfInterest()
    {
    }

    public PointOfInterest(String googleID)
    {
        this.googleID = googleID;
    }

    public PointOfInterest(String googleID, User createdByUser)
    {
        this.googleID = googleID;
        this.createdByUser = createdByUser;
    }

    public int getID()
    {
        return ID;
    }

    public void setID(int ID)
    {
        this.ID = ID;
    }

    public String getGoogleID()
    {
        return googleID;
    }

    public void setGoogleID(String googleID)
    {
        this.googleID = googleID;
    }

    public User getCreatedByUser()
    {
        return createdByUser;
    }

    public void setCreatedByUser(User createdByUser)
    {
        this.createdByUser = createdByUser;
    }
}
