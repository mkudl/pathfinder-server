package pl.lodz.p.pathfinder.serv;

import org.hibernate.Session;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.lodz.p.pathfinder.serv.dao.PoiDao;
import pl.lodz.p.pathfinder.serv.model.PointOfInterest;
import pl.lodz.p.pathfinder.serv.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;


import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class PoiPersistenceIT
{
    @PersistenceContext
    private EntityManager em;

    @Autowired
    private PoiDao poiDao;


    @Test
    public void getExistingPoi()
    {
        User creator = new User("testUserID");
        String poiID = "poiGID";

        em.persist(creator);

        PointOfInterest place = new PointOfInterest(poiID, creator);
        em.persist(place);

        PointOfInterest result = poiDao.getPoi(poiID);
        assertThat(result, notNullValue());
        assertEquals(result.getGoogleID(), poiID);
        assertEquals(result.getCreatedByUser(), creator);
    }

    @Test
    public void getNewPoi()
    {
        PointOfInterest result = poiDao.getPoi("poiGetNewTest");
        em.flush();
        Query query = em.unwrap(Session.class).createQuery("select count(p) from PointOfInterest p where p.googleID = :gID");
        query.setParameter("gID","poiGetNewTest");
        long count = (long) query.getSingleResult();

        assertNotNull(result);
        assertEquals(1,count);
    }
}
