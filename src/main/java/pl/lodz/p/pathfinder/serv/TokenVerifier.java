package pl.lodz.p.pathfinder.serv;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.json.Json;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final static String CLIENT_ID = "NOPE";

    @Autowired
    private HttpTransport transport;
    @Autowired
    private JsonFactory jsonFactory;

    public String verifyToken(String idToken)
    {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport,jsonFactory)
                .setAudience(Collections.singletonList(CLIENT_ID))
                .build();
        GoogleIdToken googleIdToken = null;
        try
        {
            googleIdToken = verifier.verify(idToken);
        } catch (GeneralSecurityException | IOException e)
        {
            e.printStackTrace();
        }
        if (googleIdToken != null) {
            GoogleIdToken.Payload payload = googleIdToken.getPayload();
            return payload.getSubject();
        } else {
            Logger.getAnonymousLogger().log(Level.WARNING,"Invalid token" + idToken);
            return "";
        }
    }
}
