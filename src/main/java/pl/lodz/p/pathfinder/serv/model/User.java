package pl.lodz.p.pathfinder.serv.model;


import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.Set;

@Entity
public class User
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    @NaturalId
    private String googleID;

    //since user won't be deleted it should be fine to cascade all
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "createdByUser", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private Set<Trip> createdTrips;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "createdByUser")
    @Fetch(FetchMode.SELECT)
    private Set<PointOfInterest> createdPois;


    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Trip> favoriteTrips;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<PointOfInterest> favoritePois;



    public User(String googleID)
    {
        this.googleID = googleID;
    }

    public User(String googleID, Set<Trip> createdTrips, Set<PointOfInterest> createdPois, Set<Trip> favoriteTrips, Set<PointOfInterest> favoritePois)
    {
        this.googleID = googleID;
        this.createdTrips = createdTrips;
        this.createdPois = createdPois;
        this.favoriteTrips = favoriteTrips;
        this.favoritePois = favoritePois;
    }

    /**
     * Required no-args constructor
     */
    public User(){}


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

    public Set<Trip> getCreatedTrips()
    {
        return createdTrips;
    }

    public void setCreatedTrips(Set<Trip> createdTrips)
    {
        this.createdTrips = createdTrips;
    }

    public Set<PointOfInterest> getCreatedPois()
    {
        return createdPois;
    }

    public void setCreatedPois(Set<PointOfInterest> createdPois)
    {
        this.createdPois = createdPois;
    }

    public Set<Trip> getFavoriteTrips()
    {
        return favoriteTrips;
    }

    public void setFavoriteTrips(Set<Trip> favoriteTrips)
    {
        this.favoriteTrips = favoriteTrips;
    }

    public Set<PointOfInterest> getFavoritePois()
    {
        return favoritePois;
    }

    public void setFavoritePois(Set<PointOfInterest> favoritePois)
    {
        this.favoritePois = favoritePois;
    }
}
