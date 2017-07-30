package pl.lodz.p.pathfinder.serv.dao;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.lodz.p.pathfinder.serv.model.PointOfInterest;
import pl.lodz.p.pathfinder.serv.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Set;

/**
 * Created by QDL on 2017-04-10.
 */


@Repository
public class PoiDao
{


    @PersistenceContext
    private
    EntityManager entityManager;

    private final UserDao userDao;

    @Autowired
    public PoiDao(UserDao userDao)
    {
        this.userDao = userDao;
    }


    private Session getSession()
    {
        return entityManager.unwrap(Session.class);
    }




    /**
     * Returns POI from database if one exists, creates a new one otherwise
     * To be used when receiving data from the client, and it is uncertain whether the POI already exists in the db
     */
    public PointOfInterest getPoi(String googleID)
    {
        Session session = entityManager.unwrap(Session.class);

        if(countPointsCreatedByUser(googleID)>0){
            return session.byNaturalId(PointOfInterest.class).using("googleID",googleID).load();
        }
        else
        {
            PointOfInterest poi = new PointOfInterest(googleID);
            entityManager.persist(poi);
            return poi;
        }
    }

    private int countPointsCreatedByUser(String googleID)
    {
        Query query = entityManager.unwrap(Session.class).createQuery("select count(p) from PointOfInterest p where p.googleID = :gID");
        query.setParameter("gID",googleID);
        return Math.toIntExact((Long) query.getSingleResult());
    }


}
