package pl.lodz.p.pathfinder.serv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.lodz.p.pathfinder.serv.dao.TripDao;
import pl.lodz.p.pathfinder.serv.dao.UserDao;
import pl.lodz.p.pathfinder.serv.model.Trip;
import pl.lodz.p.pathfinder.serv.model.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TripService
{
    TripDao tripDao;

    UserDao userDao;

    @Autowired
    TripService(TripDao tripDao, UserDao userDao)
    {
        this.tripDao = tripDao;
        this.userDao = userDao;
    }



    /**
     * To be used only to persist Trips not yet in db
     * @param trip Trip object to be saved in db
     */
    public void saveNewTrip(Trip trip, String userID)
    {
        User creator = userDao.getUser(userID);
        trip.setCreatedByUser(creator);
        tripDao.save(trip);
    }

    public Trip getTrip(int ID)
    {
        return tripDao.getTrip(ID);
    }

    /**
     * @return A list of recommended Trips
     */
    public List<Trip> getRecommended()
    {
        return tripDao.getAnyTripList(10);
    }

    /**
     * Update an existing Trip with the new values of its Name, Description and Places
     * To be used only if trip is already saved in db
     * @param trip Object holding the id of the object to be updated, as well as the values to be inserted
     */
    public void updateTrip(Trip trip)
    {
        tripDao.updateTrip(trip);
    }

    public void deleteTrip(int tripID, String userID)
    {
        tripDao.deleteTrip(tripID,userID);
    }




    public void addTripToFavorites(int tripID, String userID)
    {
        User u = userDao.getUser(userID);
        Trip newFavorite = tripDao.getTrip(tripID);
        u.getFavoriteTrips().add(newFavorite);
    }

    public void removeTripFromFavorites(int tripID, String userID)
    {
        User u = userDao.getUser(userID);
        Set<Trip> updatedFavorites = u.getFavoriteTrips().stream().filter(t -> t.getId() != tripID ).collect(Collectors.toSet());
        u.setFavoriteTrips(updatedFavorites);
    }

    public Set<Trip> getAllTripsByUser(String userID)
    {
        User u = userDao.getUser(userID);
        if(u!=null) return u.getCreatedTrips();
        else return new HashSet<>();
    }

    public Set<Trip> getUserFavoriteTrips(String userID)
    {
        User u = userDao.getUser(userID);
        return u.getFavoriteTrips();
    }


}
