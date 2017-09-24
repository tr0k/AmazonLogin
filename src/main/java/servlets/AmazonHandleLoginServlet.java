package servlets;

import beans.SessionManager;
import org.json.JSONObject;

import javax.servlet.http.HttpServlet;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


/**
 * Created by tr0k on 2017-09-15.
 */
public class AmazonHandleLoginServlet extends HttpServlet {
    private final String CLIENT_ID = "amzn1.application-oa2-client.78eefa18c9a741649d84af098aa7f915";

    @Override
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
            throws javax.servlet.ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
            throws javax.servlet.ServletException, IOException {
        String userAgent = request.getHeader("User-Agent");
        String accessToken = request.getParameter("access_token");
        String verifyAccessTokenResponse = verifyAccessToken(accessToken, userAgent);

        // Set the response message's MIME type
        response.setContentType("text/html;charset=UTF-8");
        // Allocate a output writer to write the response message into the network socket
        PrintWriter out = response.getWriter();
        // Write the response message, in an HTML page

        // Create session
        if(comapareClientIds(verifyAccessTokenResponse)) {
            SessionManager.getInstance().setAccessToken(accessToken);
        }

        try {
            out.println("<!DOCTYPE html>");
            out.println("<html><head>");
            out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
            out.println("<title>Amazon Handle Login</title></head>");
            out.println("<body>");
            out.println("<p>Request URI: " + request.getRequestURI() + "</p>");
            out.println("<p>Protocol: " + request.getProtocol() + "</p>");
            out.println("<p>Remote Address: " + request.getRemoteAddr() + "</p>");
            out.println("<p>Verify Access Token: " + verifyAccessTokenResponse + "</p>");
            out.println("<p>Compare the aud value to the client_id you are using for your application. If they are different, the access token was not requested by your application, and you should not use the access token.</p>");
            out.println("<p>My client_id: " + CLIENT_ID + "</p>");
            out.println("Access Token validation: " + String.valueOf(comapareClientIds(verifyAccessTokenResponse)));

            out.println("<form action=\"welcomeuser.jsp\">");
            out.println("<input type=\"submit\" value=\"ProfileInfo\" />");
            out.println("</form>");

            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close(); // Always close the output writer
        }
    }

    /**
     * Compare client id used for this app with the one received from https://api.amazon.com/auth/O2/tokeninfo
     * @return
     */
    private boolean comapareClientIds(String callResponse){
        // Convert response msg to JSON format
        JSONObject jsonObject = new JSONObject(callResponse);
        String clientId = String.valueOf(jsonObject.get("aud"));
        return clientId.equals(CLIENT_ID);
    }

    /**
     * HTTP call to https://api.amazon.com/auth/O2/tokeninfo, passing the access token
     */
    private String verifyAccessToken(String accessToken, String userAgent) throws IOException {
        String url = "https://api.amazon.com/auth/O2/tokeninfo";
        url = url + "?access_token=" + URLEncoder.encode(accessToken, "UTF-8");

        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", userAgent);

        int responseCode = connection.getResponseCode();
        System.out.println("Response code: " + responseCode);

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
}
