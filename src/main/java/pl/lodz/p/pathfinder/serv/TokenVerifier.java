package pl.lodz.p.pathfinder.serv;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by QDL on 2017-04-07.
 */
@Component
public class TokenVerifier
{
    //TODO move
    final static String CLIENT_ID = "NOPE";

    public String verifyToken(String idToken)
    {
        //TODO make beans from these or make method static
        HttpTransport transport = new ApacheHttpTransport();
        JsonFactory jsonfactory = new JacksonFactory();

        String asd;
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport,jsonfactory)
                .setAudience(Collections.singletonList(CLIENT_ID))
                .build();

//        GoogleIdToken idToken = verifier.verify(idTokenString);
        GoogleIdToken googleIdToken = null;
        try
        {
//            idToken = verifier.verify(body.substring(8,body.length()));
            googleIdToken = verifier.verify(idToken);
        } catch (GeneralSecurityException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        if (googleIdToken != null) {
            GoogleIdToken.Payload payload = googleIdToken.getPayload();

            // Print user identifier
            String userId = payload.getSubject();
//            System.out.println("User ID: " + userId);

            // Get profile information from payload
//            String email = payload.getEmail();
//            boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
//            String name = (String) payload.get("name");
//            String pictureUrl = (String) payload.get("picture");
//            String locale = (String) payload.get("locale");
//            String familyName = (String) payload.get("family_name");
//            String givenName = (String) payload.get("given_name");

            return userId;

        } else {

            Logger.getAnonymousLogger().log(Level.WARNING,"Invalid token" + idToken);
            return "";
        }
    }
}
