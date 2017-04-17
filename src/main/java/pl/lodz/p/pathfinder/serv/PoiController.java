package pl.lodz.p.pathfinder.serv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.pathfinder.serv.model.PointOfInterest;

import java.util.Set;

/**
 * Created by QDL on 2017-04-10.
 */

@RestController
@Transactional
@RequestMapping("/poi")
public class PoiController
{

    @Autowired
    PoiDao poiDao;

    @Autowired
    TokenVerifier tokenVerifier;




    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public Set<PointOfInterest> getAllByUser(@RequestParam(value = "idToken", defaultValue = "-1") String idToken)
    {
        String id = tokenVerifier.verifyToken(idToken);
        if(!id.isEmpty()){
            return poiDao.getAllByUser(id);
        }
        //TODO handle auth failure
        return null;
    }

    @RequestMapping(value = "/favorites", method = RequestMethod.GET)
    public Set<PointOfInterest> getUserFavorites(@RequestParam(value = "idToken", defaultValue = "-1") String idToken)
    {
        String id = tokenVerifier.verifyToken(idToken);
        if(!id.isEmpty()){
            return poiDao.getUserFavorites(id);
        }
        //TODO handle auth failure
        return null;
    }


    @RequestMapping(value = "/addFavorite", method = RequestMethod.POST)
    public void addPoiToFavorites(@RequestParam(value = "idToken", defaultValue = "-1") String idToken,
                                   @RequestParam(value = "poiGoogleId") String poiGoogleId)
    {
        String id = tokenVerifier.verifyToken(idToken);
        if(!id.isEmpty())
        {
            poiDao.addToFavorites(poiGoogleId,id);
        }
        //TODO handle auth failure
    }


    @RequestMapping(value = "/removeFavorite", method = RequestMethod.DELETE)
    public void removePoiFromFavorites(@RequestParam(value = "idToken", defaultValue = "-1") String idToken,
                                   @RequestParam(value = "poiGoogleId") String poiGoogleId)
    {
        String id = tokenVerifier.verifyToken(idToken);
        if(!id.isEmpty())
        {
            poiDao.removeFromFavorites(poiGoogleId,id);
        }
        //TODO handle auth failure
    }


    @RequestMapping(value = "/addCreated", method = RequestMethod.POST)
    public void addPoiToCreated(@RequestParam(value = "idToken", defaultValue = "-1") String idToken,
                                  @RequestParam(value = "poiGoogleId") String poiGoogleId)
    {
        String id = tokenVerifier.verifyToken(idToken);
        if(!id.isEmpty())
        {
            poiDao.addToCreated(poiGoogleId,id);
        }
        //TODO handle auth failure
    }

}
