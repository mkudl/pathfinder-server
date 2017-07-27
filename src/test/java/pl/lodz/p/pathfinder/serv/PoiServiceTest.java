package pl.lodz.p.pathfinder.serv;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import pl.lodz.p.pathfinder.serv.dao.PoiDao;
import pl.lodz.p.pathfinder.serv.dao.UserDao;
import pl.lodz.p.pathfinder.serv.model.PointOfInterest;
import pl.lodz.p.pathfinder.serv.model.User;
import pl.lodz.p.pathfinder.serv.service.PoiService;


import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PoiServiceTest
{

    @InjectMocks
    private PoiService poiService;

    @Mock
    private PoiDao poiDao;

    @Mock
    private UserDao userDao;


    private final String poiID = "testPoiID";
    private final String userID = "testUserID";
    private User creator;

    @Before
    public void init()
    {
        creator = new User(userID);
        Set<PointOfInterest> created = new HashSet<>();
        Set<PointOfInterest> favorites = new HashSet<>();
        PointOfInterest poi1 = new PointOfInterest("poiGoogleId1");
        PointOfInterest poi2 = new PointOfInterest("poiGoogleId2");
        created.add(poi1);
        created.add(poi2);
        favorites.add(poi1);
        favorites.add(poi2);
        creator.setCreatedPois(created);
        creator.setFavoritePois(favorites);

        when(userDao.getUser(userID)).thenReturn(creator);

        PointOfInterest poi = new PointOfInterest(poiID);
        when(poiDao.getPoi(poiID)).thenReturn(poi);
    }


    @Test
    public void getPoi()
    {
        poiService.getPoi(poiID);
        verify(poiDao,times(1)).getPoi(poiID);
    }

    @Test
    public void getAllPois()
    {
        Set<PointOfInterest> result = poiService.getAllPoisByUser(userID);
        assertThat(result, both(hasSize(2)).and(containsInAnyOrder(
                hasProperty("googleID",is("poiGoogleId1")),
                hasProperty("googleID",is("poiGoogleId2")) )));
    }

    @Test
    public void getFavoritePois()
    {
        Set<PointOfInterest> result = poiService.getUserFavoritePois(userID);
        assertThat(result, both(hasSize(2)).and(containsInAnyOrder(
                hasProperty("googleID",is("poiGoogleId1")),
                hasProperty("googleID",is("poiGoogleId2")) )));
    }

    @Test
    public void addToFavorites()
    {
        User addFavoriteUser = new User();
        addFavoriteUser.setFavoritePois(new HashSet<>());
        when(userDao.getUser("addFavorite")).thenReturn(addFavoriteUser);

        poiService.addPoiToFavorites(poiID,"addFavorite");

        assertThat(addFavoriteUser.getFavoritePois(), both(hasSize(1))
                .and(contains(hasProperty("googleID",is(poiID)))));
    }

    @Test
    public void removeFromFavorites()
    {
        User userWithFavorites = new User();
        Set<PointOfInterest> favoritePois = new HashSet<>();
        PointOfInterest favoritePoi = new PointOfInterest("removeFavorite");
        favoritePois.add(favoritePoi);
        userWithFavorites.setFavoritePois(favoritePois);
        String removeFavoriteUserID = "removeFavoriteUserID";
        when(userDao.getUser(removeFavoriteUserID)).thenReturn(userWithFavorites);

        poiService.removePoiFromFavorites("removeFavorite",removeFavoriteUserID);
    }

    public void addToCreated()
    {
        User poiCreator = new User();
        when(userDao.getUser("addCreated")).thenReturn(poiCreator);
        poiService.addPoiToCreated("newlyCreatedPoiId","addCreated");
        assertThat(poiCreator.getCreatedPois(), hasSize(1));
    }
}
