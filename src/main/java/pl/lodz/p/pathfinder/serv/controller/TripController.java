package pl.lodz.p.pathfinder.serv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.pathfinder.serv.TokenVerifier;
import pl.lodz.p.pathfinder.serv.dao.TripDao;
import pl.lodz.p.pathfinder.serv.model.Trip;
import pl.lodz.p.pathfinder.serv.model.json.TripJsonWrapper;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by QDL on 2017-04-05.
 */

@RestController
@Transactional(isolation = Isolation.SERIALIZABLE)
@RequestMapping("/trip")
public class TripController
{

    private final TripDao tripDao;

    private final TokenVerifier tokenVerifier;

    @Autowired
    public TripController(TripDao tripDao, TokenVerifier tokenVerifier)
    {
        this.tripDao = tripDao;
        this.tokenVerifier = tokenVerifier;
    }


    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public ResponseEntity<Set<Trip>> getAllByUser(@RequestParam(value = "idToken", defaultValue = "-1") String idToken)
    {
        String id = tokenVerifier.verifyToken(idToken);
        if(!id.isEmpty()){
            return ResponseEntity.ok(tripDao.getAllByUser(id));
        }
        return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }


    @RequestMapping(value = "/favorites", method = RequestMethod.GET)
    public ResponseEntity<Set<Trip>> getUserFavorites(@RequestParam(value = "idToken", defaultValue = "-1") String idToken)
    {
        String id = tokenVerifier.verifyToken(idToken);
        if(!id.isEmpty()){
            return ResponseEntity.ok(tripDao.getUserFavorites(id));
        }
        return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }


    @RequestMapping(value = "/recommended", method = RequestMethod.GET)
    public ResponseEntity<List<Trip>> getRecommended(@RequestParam(value = "idToken", defaultValue = "-1") String idToken)
    {
        String id = tokenVerifier.verifyToken(idToken);
        if(!id.isEmpty()){
            return ResponseEntity.ok(tripDao.getRecommended());
        }
        return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }


    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity saveNewTrip(@RequestBody TripJsonWrapper body )
    {
        String id = tokenVerifier.verifyToken(body.getIdToken());
        if(!id.isEmpty())
        {
            tripDao.saveNewTrip(body.getTrip(),id);
            return ResponseEntity.ok(null);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }


    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity updateTrip(@RequestBody TripJsonWrapper body)
    {
        String id = tokenVerifier.verifyToken(body.getIdToken());
        if(!id.isEmpty())
        {
            tripDao.updateTrip(body.getTrip());
            return ResponseEntity.ok(null);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }


    @RequestMapping(value = "/addFavorite", method = RequestMethod.PUT)
    public ResponseEntity AddTripToFavorites(@RequestParam(value = "idToken", defaultValue = "-1") String idToken,
                                   @RequestParam(value = "tripID") int tripID)
    {
        String id = tokenVerifier.verifyToken(idToken);
        if(!id.isEmpty())
        {
            tripDao.addToFavorites(tripID,id);
            return ResponseEntity.ok(null);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }


    @RequestMapping(value = "/removeFavorite", method = RequestMethod.DELETE)
    public ResponseEntity RemoveTripFromFavorite(@RequestParam(value = "idToken", defaultValue = "-1") String idToken,
                                       @RequestParam(value = "tripID") int tripID)
    {
        String id = tokenVerifier.verifyToken(idToken);
        if(!id.isEmpty())
        {
            tripDao.removeFromFavorites(tripID,id);
            return ResponseEntity.ok(null);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }


    @RequestMapping(value = "/checkFavorite", method = RequestMethod.GET)
    public ResponseEntity<Map<String,Boolean>> checkUserFavorite(@RequestParam(value = "idToken", defaultValue = "-1") String idToken,
                                                                 @RequestParam(value = "tripID") int tripID)
    {
        String userID = tokenVerifier.verifyToken(idToken);
        if(!userID.isEmpty())
        {
            if (tripDao.getUserFavorites(userID).contains(tripDao.getTrip(tripID)) ){
                //can't return a primitive type and creating a wrapper seems like overkill, so it returns a map
                return ResponseEntity.ok(Collections.singletonMap("isFavorite",true));
            }
            else {
                return ResponseEntity.ok(Collections.singletonMap("isFavorite",false));
            }
        }
        return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    @RequestMapping(value = "/deleteTrip", method = RequestMethod.DELETE)
    public ResponseEntity<Map<String,Boolean>> deleteTrip(@RequestParam(value = "idToken", defaultValue = "-1") String idToken,
                                                                 @RequestParam(value = "tripID") int tripID)
    {
        String userID = tokenVerifier.verifyToken(idToken);
        if(!userID.isEmpty())
        {
            try{
                tripDao.deleteTrip(tripID,userID);
                return ResponseEntity.ok(null);
            }
            catch (SecurityException e)
            {
                Logger.getAnonymousLogger().log(Level.WARNING,e.getMessage());
                return  ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
            }
        }
        return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

}
