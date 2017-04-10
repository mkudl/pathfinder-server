package pl.lodz.p.pathfinder.serv.model.json;

/**
 * Created by QDL on 2017-04-07.
 */
public class TokenJson
{
    private String idToken;

    public TokenJson()
    {
    }

    public TokenJson(String idToken)
    {
        this.idToken = idToken;
    }

    public String getIdToken()
    {
        return idToken;
    }

    public void setIdToken(String idToken)
    {
        this.idToken = idToken;
    }
}
