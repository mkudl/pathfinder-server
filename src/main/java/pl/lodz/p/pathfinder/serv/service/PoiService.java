package pl.lodz.p.pathfinder.serv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.lodz.p.pathfinder.serv.dao.PoiDao;
import pl.lodz.p.pathfinder.serv.dao.UserDao;
import pl.lodz.p.pathfinder.serv.model.PointOfInterest;
import pl.lodz.p.pathfinder.serv.model.User;

import java.util.Set;

@Component
public class PoiService
{
    PoiDao poiDao;

    UserDao userDao;

    @Autowired
    PoiService(PoiDao poiDao, UserDao userDao)
    {
        this.poiDao = poiDao;
        this.userDao = userDao;
    }




    public PointOfInterest getPoi(String googleID)
    {
        return  poiDao.getPoi(googleID);
    }



    public Set<PointOfInterest> getAllPoisByUser(String userID)
    {
        User u = userDao.getUser(userID);
        return u.getCreatedPois();
    }

    public Set<PointOfInterest> getUserFavoritePois(String userID)
    {
//        User u = getSession().byNaturalId(User.class).using("googleID",userID).load();
        User u = userDao.getUser(userID);
        return u.getFavoritePois();
    }

    public void addPoiToFavorites(String poiId, String userID)
    {
        User u = userDao.getUser(userID);

        PointOfInterest poi = poiDao.getPoi(poiId);

        u.getFavoritePois().add(poi);
    }


    public void removePoiFromFavorites(String poiId, String userID)
    {
        User u = userDao.getUser(userID);
        PointOfInterest poi = poiDao.getPoi(poiId);
        u.getFavoritePois().remove(poi);
    }


    public void addPoiToCreated(String poiId, String userID)
    {
        User u = userDao.getUser(userID);
        PointOfInterest poi = new PointOfInterest(poiId,u);
        u.getCreatedPois().add(poi);
    }
}
