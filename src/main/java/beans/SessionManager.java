package beans;

import java.io.IOException;

/**
 * Created by tr0k on 2017-09-24.
 */
public class SessionManager {

    private String accessToken;

    private static SessionManager ourInstance = new SessionManager();

    public static SessionManager getInstance() {
        return ourInstance;
    }

    private SessionManager() {
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Boolean isSessionValid(){
        AccessTokenVerifier verifier = new AccessTokenVerifier(accessToken);
        try {
            String callResponse = verifier.verifyAccessToken();
            if(callResponse.length() != 0 && verifier.comapareClientIds(callResponse)){
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
