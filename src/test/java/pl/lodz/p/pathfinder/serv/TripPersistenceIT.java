package pl.lodz.p.pathfinder.serv;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.lodz.p.pathfinder.serv.dao.TripDao;
import pl.lodz.p.pathfinder.serv.model.Trip;
import pl.lodz.p.pathfinder.serv.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * Created by QDL on 2017-06-17.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes = {ProjectConfiguration.class, Hello.class})
@RunWith(SpringRunner.class)
@SpringBootTest
//@Rollback(false)
@Transactional
//@TestPropertySource(properties = { "spring.datasource.username = ", "spring.datasource.password =" })
public class TripPersistenceIT
{
    SessionFactory sessionFactory;
    Session session;

    @PersistenceContext
    private EntityManager em;

    private User creator;

    @Autowired
    TripDao tripDao;


    private int readTripId;
    private int updateTripId;
    private int deleteTripId;


    @Before
    public void before()
    {
//        Configuration config = new Configuration();
//        config.addAnnotatedClass(User.class)
//                .addAnnotatedClass(Trip.class)
//                .addAnnotatedClass(PointOfInterest.class);
//        config.setProperty("hibernate.dialect",
//                "org.hibernate.dialect.H2Dialect");
//        config.setProperty("hibernate.connection.driver_class",
//                "org.h2.Driver");
//        config.setProperty("hibernate.connection.url", "jdbc:h2:~/test");
//        config.setProperty("hibernate.hbm2ddl.auto", "create");

//        sessionFactory = config.buildSessionFactory();
//        session = sessionFactory.openSession();

//        tripDao.setEntityManager(session.getEntityManagerFactory().createEntityManager());

        creator = new User("testUserID");
        em.persist(creator);

        Trip readTrip = new Trip("readTestTrip","readTestTripDesc",creator, Arrays.asList("place1","place2"));
        em.persist(readTrip);
        readTripId = readTrip.getId();  //ids cannot be specified manually, so it needs to be saved


        Trip updateTrip = new Trip("asdfgh","asdfgh",creator, Arrays.asList("place1","place2"));
        em.persist(updateTrip);
        updateTripId = updateTrip.getId();


        Trip deleteTrip = new Trip("deleteTestTrip","deleteTestTripDesc",creator, Arrays.asList("place1","place2"));
        deleteTrip.setCreatedByUser(creator);
        Set<Trip> created = new HashSet<>();
        created.add(deleteTrip);
        creator.setCreatedTrips(created);
        em.persist(deleteTrip);
        deleteTripId = deleteTrip.getId();

        em.flush();

    }

    @Test
    public void saveTest()
    {
        assertNotNull(tripDao);

        Trip t = new Trip("testTrip","testTripDesc",creator, Arrays.asList("place1","place2"));
        String s = "testUserID";
        tripDao.save(t);
        em.flush();

        Trip result = em.find(Trip.class,t.getId());
        assertNotNull(result);
//        assertNotNull(em.unwrap(Session.class).byNaturalId(User.class).using("googleId","testUserID"));
        assertEquals(creator.getGoogleID(),result.getCreatedByUser().getGoogleID());
        assertEquals(t.getName(),result.getName());
    }

    @Test
    public void readTest()
    {
        assertNotNull(tripDao);

        Trip result = tripDao.getTrip(readTripId);

        assertNotNull(result);
        assertEquals(creator.getGoogleID(),result.getCreatedByUser().getGoogleID());
        assertEquals("readTestTrip",result.getName());
        assertThat(result.getPlaces(), contains("place1","place2") );
    }

    @Test
    public void updateTest()
    {
        List<String> updateList = new ArrayList<String>();
        updateList.add("1");
        updateList.add("2");
        updateList.add("3");

        Trip updatedTrip = new Trip("updated","Updated trip",creator, updateList);
        updatedTrip.setId(updateTripId);

        tripDao.updateTrip(updatedTrip);

        Trip result = em.find(Trip.class,updateTripId);

        assertNotNull(result);
        assertEquals("updated",result.getName());
        assertEquals("Updated trip", result.getDescription());
        assertThat(result.getPlaces(), both(hasSize(3)).and(containsInAnyOrder("1","2","3")) );
    }

    @Test
    public void deleteTest()
    {
        tripDao.deleteTrip(deleteTripId,creator.getGoogleID());
        em.flush();

        Query query = em.unwrap(Session.class).createQuery("select count(t) from Trip t where t.id = :tID");
        query.setParameter("tID",deleteTripId);
        long result = (long) query.getSingleResult();



        assertEquals(0, result);
    }



    @After
    public void after() {
//        session.close();
//        sessionFactory.close();
    }
}
