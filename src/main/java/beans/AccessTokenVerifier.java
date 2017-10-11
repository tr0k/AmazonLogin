package beans;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by tr0k on 2017-10-11.
 */
public class AccessTokenVerifier {

    private String userAgent;
    private String accessToken;
    private final String CLIENT_ID = "amzn1.application-oa2-client.78eefa18c9a741649d84af098aa7f915";

    public AccessTokenVerifier(String accessToken, String userAgent) {
        this.userAgent = userAgent;
        this.accessToken = accessToken;
    }

    public AccessTokenVerifier(String accessToken) {
        this.accessToken = accessToken;
        this.userAgent = "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36";
    }

    /**
     * HTTP call to https://api.amazon.com/auth/O2/tokeninfo, passing the access token
     */
    public String verifyAccessToken() throws IOException {
        if(accessToken == null) return "";
        String url = "https://api.amazon.com/auth/O2/tokeninfo";
        url = url + "?access_token=" + URLEncoder.encode(accessToken, "UTF-8");

        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", userAgent);

        int responseCode = connection.getResponseCode();
        System.out.println("Response code: " + responseCode);

        if(responseCode == 400) return "";

        // Read response
        StringBuffer response = new StringBuffer();
        BufferedReader in  = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null){
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }

    /**
     * Compare client id used for this app with the one received from https://api.amazon.com/auth/O2/tokeninfo
     * @return
     */
    public boolean comapareClientIds(String callResponse){
        // Convert response msg to JSON format
        JSONObject jsonObject = new JSONObject(callResponse);
        String clientId = String.valueOf(jsonObject.get("aud"));
        return clientId.equals(CLIENT_ID);
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
