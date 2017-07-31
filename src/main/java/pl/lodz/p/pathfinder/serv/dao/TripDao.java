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




    public Trip getTrip(int ID)
    {
        return entityManager.find(Trip.class,ID);
    }



    public void save(Trip trip)
    {
        entityManager.persist(trip);
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


    public void deleteTrip(int tripID, String userID)
    {
        Trip trip = entityManager.find(Trip.class,tripID);
        if(trip.getCreatedByUser().getGoogleID().equals(userID))
        {
            if(trip.getFavoritingUsers() != null)
            {
                for( User user : trip.getFavoritingUsers())
                {
                    user.getFavoriteTrips().remove(trip);
                }
            }
            User u = trip.getCreatedByUser();
            u.getCreatedTrips().remove(trip);
        } else {
            throw new SecurityException("Delete method called for user who is not trip's owner");
        }
    }


    /**
     * @return A list of first @amount Trips that are found, according to no criteria in particular
     */
    public List<Trip> getAnyTripList(int amount)
    {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Trip> criteriaQuery = cb.createQuery(Trip.class);
        Root<Trip> root = criteriaQuery.from( Trip.class);
        criteriaQuery.select(root);
        return entityManager.createQuery(criteriaQuery).setMaxResults(amount).getResultList();
    }

}
