package pl.lodz.p.pathfinder.serv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.pathfinder.serv.model.Trip;
import pl.lodz.p.pathfinder.serv.model.json.TokenJson;
import pl.lodz.p.pathfinder.serv.model.json.TripJsonWrapper;

import java.util.List;
import java.util.Set;

/**
 * Created by QDL on 2017-04-05.
 */

@RestController
@Transactional
@RequestMapping("/trip")
public class TripController
{

    @Autowired
    TripDao tripDao;

    @Autowired
    TokenVerifier tokenVerifier;



    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
//    @RequestMapping(value = "/getAll")
    public Set<Trip> getAllByUser(@RequestParam(value = "idToken", defaultValue = "-1") String idToken)
    {
        String id = tokenVerifier.verifyToken(idToken);
        if(!id.isEmpty()){
            return tripDao.getAllByUser(id);
        }
        //TODO handle auth failure
        return null;
    }

    @RequestMapping(value = "/favorites", method = RequestMethod.GET)
    public Set<Trip> getUserFavorites(@RequestParam(value = "idToken", defaultValue = "-1") String idToken)
    {
        String id = tokenVerifier.verifyToken(idToken);
        if(!id.isEmpty()){
            return tripDao.getUserFavorites(id);
        }
        //TODO handle auth failure
        return null;
    }

    @RequestMapping(value = "/recommended", method = RequestMethod.GET)
    public List<Trip> getRecommended(@RequestParam(value = "idToken", defaultValue = "-1") String idToken)
    {

//        String id = tokenVerifier.verifyToken(body.getIdToken());
        String id = tokenVerifier.verifyToken(idToken);
        if(!id.isEmpty()){
            return tripDao.getRecommended();
        }
        //TODO handle auth failure
        return null;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public void saveNewTrip(@RequestBody TripJsonWrapper body )
    {
        String id = tokenVerifier.verifyToken(body.getIdToken());
        if(!id.isEmpty())
        {
            tripDao.saveNewTrip(body.getTrip(),id);
        }
        //TODO handle auth failure

    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public void UpdateTrip(@RequestBody TripJsonWrapper body)
    {
        String id = tokenVerifier.verifyToken(body.getIdToken());
        if(!id.isEmpty())
        {
            tripDao.updateTrip(body.getTrip());
        }
        //TODO handle auth failure

    }

    //TODO change mapping, too similar
    @RequestMapping(value = "/addFavorite", method = RequestMethod.PUT)
    public void AddTripToFavorites(@RequestBody TripJsonWrapper body)
    {
        String id = tokenVerifier.verifyToken(body.getIdToken());
        if(!id.isEmpty())
        {
            tripDao.addToFavorites(body.getTrip(),id);
        }
        //TODO handle auth failure
    }


}
