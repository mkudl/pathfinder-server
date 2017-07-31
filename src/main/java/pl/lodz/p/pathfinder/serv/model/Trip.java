package pl.lodz.p.pathfinder.serv.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;
import java.util.Set;


@Entity
@Table(name="trips")
public class Trip
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private User createdByUser; //Google ID of the user that created this trip

    @ManyToMany(mappedBy = "favoriteTrips")
    @JsonIgnore
    private Set<User> favoritingUsers;


    @ElementCollection(fetch = FetchType.EAGER)
    @OrderColumn
    @Fetch(FetchMode.SELECT)
    @JsonProperty("pointOfInterestList")
    private List<String> places; //Reference to the IDs of the places in Google APIs


    public Trip()
    {

    }

    public Trip(String name, String description, User createdByUser, List<String> places)
    {
        this.name = name;
        this.description = description;
        this.createdByUser = createdByUser;
        this.places = places;
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

    public User getCreatedByUser()
    {
        return createdByUser;
    }

    public void setCreatedByUser(User createdByUser)
    {
        this.createdByUser = createdByUser;
    }

    public List<String> getPlaces()
    {
        return places;
    }

    public void setPlaces(List<String> places)
    {
        this.places = places;
    }

    public Set<User> getFavoritingUsers()
    {
        return favoritingUsers;
    }

    public void setFavoritingUsers(Set<User> favoritingUsers)
    {
        this.favoritingUsers = favoritingUsers;
    }
}
