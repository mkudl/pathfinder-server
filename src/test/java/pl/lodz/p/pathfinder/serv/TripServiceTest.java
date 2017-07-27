package pl.lodz.p.pathfinder.serv;


import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import pl.lodz.p.pathfinder.serv.dao.TripDao;
import pl.lodz.p.pathfinder.serv.dao.UserDao;
import pl.lodz.p.pathfinder.serv.model.Trip;
import pl.lodz.p.pathfinder.serv.model.User;
import pl.lodz.p.pathfinder.serv.service.TripService;


import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TripServiceTest
{


    private String userID = "userID";
    private User creator;

    @InjectMocks
    private
    TripService tripService;

    @Mock
    private
    TripDao tripDao;

    @Mock
    private
    UserDao userDao;


    @Before
    public void init()
    {
        creator = new User();
        when(userDao.getUser(userID)).thenReturn(creator);
    }


    @Test
    public void saveTripTest()
    {
        Trip saveTrip = new Trip();

        tripService.saveNewTrip(saveTrip,userID);
        verify(tripDao,times(1)).save(saveTrip);
        assertNotNull(saveTrip.getCreatedByUser());
        assertThat(saveTrip.getCreatedByUser(), is(creator) );
    }

    @Test
    public void getTripTest()
    {
        tripService.getTrip(0);
        verify(tripDao,times(1)).getTrip(0);
    }

    @Test
    public void recommendedTest()
    {
        tripService.getRecommended();
        verify(tripDao,times(1)).getAnyTripList(anyInt());
    }

    @Test
    public void updateTest()
    {
        Trip updateTrip = new Trip();
        tripService.updateTrip(updateTrip);
        verify(tripDao,times(1)).updateTrip(updateTrip);
    }

    @Test
    public void deleteTest()
    {
        tripService.deleteTrip(0,userID);
        verify(tripDao,times(1)).deleteTrip(0,userID);
    }

    @Test
    public void addToFavoritesTest()
    {
        int favoriteAddTripId = 870;
        User favoriter = new User();
        favoriter.setFavoriteTrips(new HashSet<>());
        String addFavoriteUserID = "addFavoriteUserID";
        when(userDao.getUser(addFavoriteUserID)).thenReturn(favoriter);
        Trip favoriteTrip = new Trip();
        when(tripDao.getTrip(favoriteAddTripId)).thenReturn(favoriteTrip);

        tripService.addTripToFavorites(favoriteAddTripId, addFavoriteUserID);
        assertThat(favoriter.getFavoriteTrips(), hasSize(1));
        assertThat(favoriter.getFavoriteTrips(), contains(favoriteTrip));
    }

    @Test
    public void removeFromFavoritesTest()
    {
        User userWithFavorites = new User();
        Set<Trip> favoriteTrips = new HashSet<>();
        Trip favoriteTrip = new Trip();
        favoriteTrips.add(favoriteTrip);
        int favoriteRemoveTripId = favoriteTrip.getId();
        userWithFavorites.setFavoriteTrips(favoriteTrips);
        String removeFavoriteUserID = "removeFavoriteUserID";
        when(userDao.getUser(removeFavoriteUserID)).thenReturn(userWithFavorites);


        tripService.removeTripFromFavorites(favoriteRemoveTripId, removeFavoriteUserID);
        assertThat(userWithFavorites.getFavoriteTrips(), hasSize(0));
    }

    @Test
    public void getAllByUserTest()
    {
        String userWithSomeTripsID = "userWithSomeTrips";
        User userWithSomeTrips = new User();
        Set<Trip> created = new HashSet<>();
        Trip madeByUser = new Trip();
        created.add(madeByUser);
        userWithSomeTrips.setCreatedTrips(created);


        when(userDao.getUser(userWithSomeTripsID)).thenReturn(userWithSomeTrips);

        Set<Trip> result = tripService.getAllTripsByUser(userWithSomeTripsID);

        assertEquals(created, result);
    }


    @Test
    public void getFavoritesTest()
    {
        String userWithFavoritesID = "userWithFavorites";
        User userWithFavorites = new User();
        Set<Trip> favorites = new HashSet<>();
        Trip favorite = new Trip();
        favorites.add(favorite);
        userWithFavorites.setFavoriteTrips(favorites);

        when(userDao.getUser(userWithFavoritesID)).thenReturn(userWithFavorites);

        Set<Trip> result = tripService.getUserFavoriteTrips(userWithFavoritesID);

        assertThat(result, both(hasSize(1)).and(contains(favorite)));
    }


}
