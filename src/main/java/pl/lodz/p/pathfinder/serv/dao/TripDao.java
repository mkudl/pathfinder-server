package pl.lodz.p.pathfinder.serv.dao;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.lodz.p.pathfinder.serv.model.Trip;
import pl.lodz.p.pathfinder.serv.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by QDL on 2017-04-06.
 */

@Repository
public class TripDao
{

    @PersistenceContext
    private EntityManager entityManager;

    private final UserDao userDao;

    @Autowired
    public TripDao(UserDao userDao)
    {
        this.userDao = userDao;
    }


    private Session getSession()
    {
        return entityManager.unwrap(Session.class);
    }



    public Trip getTrip(int ID)
    {
        return entityManager.find(Trip.class,ID);
    }

    public Set<Trip> getAllByUser(String userID)
    {
        User u = userDao.getUser(userID);
        if(u!=null) return u.getCreatedTrips();
        else return new HashSet<>();
    }

    public Set<Trip> getUserFavorites(String userID)
    {
        User u = userDao.getUser(userID);
        return u.getFavoriteTrips();
    }

    /**
     *
     * @return A list of recommended Trips, chosen based on an advanced algorithm
     */
    public List<Trip> getRecommended()
    {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Trip> criteriaQuery = cb.createQuery(Trip.class);
        Root<Trip> root = criteriaQuery.from( Trip.class);
        criteriaQuery.select(root);

        //an advanced algorithm
        return entityManager.createQuery(criteriaQuery).setMaxResults(10).getResultList();
    }

    /**
     * To be used only to persist Trips not yet in db
     * @param trip Trip object to be saved in db
     */
    public void saveNewTrip(Trip trip, String userID)
    {
        User creator = userDao.getUser(userID);
        trip.setCreatedByUser(creator);
        entityManager.persist(trip);
    }

    public void deleteTrip(Trip trip, String userID)
    {
        //TODO verify that user is creator (just in case; can't trust frontend)
        //TODO delete
    }

    /**
     * Update an existing Trip with the new values of its Name, Description and Places
     * To be used only if trip is already saved in db
     * @param trip Object holding the id of the object to be updated, as well as the values to be inserted
     */
    public void updateTrip(Trip trip)
    {
        //Get object based on the id of the parameter, update fields
        Trip savedTrip = entityManager.find(Trip.class,trip.getId());
        savedTrip.setName(trip.getName());
        savedTrip.setDescription(trip.getDescription());
        savedTrip.setPlaces(trip.getPlaces());
        entityManager.merge(savedTrip);
    }

    public void addToFavorites(int tripID, String userID)
    {
        User u = userDao.getUser(userID);
        Trip newFavorite = entityManager.find(Trip.class,tripID);
        u.getFavoriteTrips().add(newFavorite);
    }

    public void removeFromFavorites(int tripID, String userID)
    {
        User u = userDao.getUser(userID);
        Set<Trip> updatedFavorites = u.getFavoriteTrips().stream().filter( t -> t.getId() != tripID ).collect(Collectors.toSet());
        u.setFavoriteTrips(updatedFavorites);
    }


}
