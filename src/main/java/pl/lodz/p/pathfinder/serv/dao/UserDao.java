package pl.lodz.p.pathfinder.serv.dao;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import pl.lodz.p.pathfinder.serv.model.PointOfInterest;
import pl.lodz.p.pathfinder.serv.model.Trip;
import pl.lodz.p.pathfinder.serv.model.User;

import javax.persistence.EntityManager;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashSet;

/**
 * Created by QDL on 2017-04-07.
 */

/**
 * Data Acces Object for the User class
 * List members are to be retrieved from the DAOs of their respective classes
 */
@Repository
public class UserDao
{
    @PersistenceContext
    EntityManager entityManager;


    /**
     * Returns user from database if one exists, creates a new one otherwise
     */
    public User getUser(String googleID)
    {
        Session session = entityManager.unwrap(Session.class);

        if(getUserCount(googleID)>0){
            return session.byNaturalId(User.class).using("googleID",googleID).load();
        }
        else
        {
            User user = new User(googleID,new HashSet<>(),new HashSet<>(),new HashSet<>(),new HashSet<>());
            entityManager.persist(user);
            return user;
        }
    }


    /**
     * Check how many users with given ID are saved in the database
     * Used to determine whether to get an existing user or create and save a new one
     * @param googleID
     * @return Number of entries with that ID in db
     */
    public int getUserCount(String googleID)    //TODO? change access to private
    {
        Query query = entityManager.unwrap(Session.class).createQuery("select count(u) from User u where u.googleID = :gID");
        query.setParameter("gID",googleID);
        return Math.toIntExact((Long) query.getSingleResult());
    }

}
