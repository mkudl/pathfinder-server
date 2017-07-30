package pl.lodz.p.pathfinder.serv;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.lodz.p.pathfinder.serv.dao.UserDao;
import pl.lodz.p.pathfinder.serv.model.User;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;



@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserPersistenceIT
{
    @PersistenceContext
    private EntityManager em;

    @Autowired
    UserDao userDao;


    @Before
    public void init()
    {
        User user = new User("existing user");
        em.persist(user);
        em.flush();
    }

    @Test
    public void existingUserTest()
    {
        User result = userDao.getUser("existing user");

        assertNotNull(result);
        assertThat(result.getGoogleID(), is("existing user"));
    }

    @Test
    public void newUserTest()
    {
        User result = userDao.getUser("qwerty");

        assertNotNull(result);
        assertThat(result.getGoogleID(), is("qwerty"));
    }
}
