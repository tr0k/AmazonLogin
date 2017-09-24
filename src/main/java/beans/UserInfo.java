package beans;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tr0k on 2017-09-24.
 */
public class UserInfo {

    private String name;
    private String email;
    private String userId;

    public UserInfo(){
        String accessToken = SessionManager.getInstance().getAccessToken();
        try {
            Map m = obtainProfileInfo(accessToken);
            this.name = String.valueOf(m.get("name"));
            this.email = String.valueOf(m.get("email"));
            this.userId = String.valueOf(m.get("user_id"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Call the profile endpoint
     * Exchange the access token for user profile
     */
    private Map obtainProfileInfo(String accessToken) throws IOException {
        Content c = Request.Get("https://api.amazon.com/user/profile")
                .addHeader("Authorization", "bearer " + accessToken)
                .execute()
                .returnContent();

        ObjectMapper mapper = new ObjectMapper();
        TypeReference<HashMap<String,Object>> typeRef
                = new TypeReference<HashMap<String,Object>>() {};
        Map m = mapper.readValue(
                c.toString(),
                typeRef);
        return m;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
