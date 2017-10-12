package servlets;

import beans.AccessTokenVerifier;
import beans.SessionManager;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * Created by tr0k on 2017-09-15.
 */
public class AmazonHandleLoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws javax.servlet.ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws javax.servlet.ServletException, IOException {

        String userAgent = request.getHeader("User-Agent");
        String accessToken = request.getParameter("access_token");
        // Allocate a output writer to write the response message into the network socket
        PrintWriter out = response.getWriter();

        if(accessToken == null || accessToken.trim().length() == 0){
            noAccessPage(out);
            return;
        }

        AccessTokenVerifier verifier = new AccessTokenVerifier(accessToken, userAgent);
        String verifyAccessTokenResponse = verifier.verifyAccessToken();
        if(verifyAccessTokenResponse.length()==0) {
            noAccessPage(out);
            return;
        }

        // Set the response message's MIME type
        response.setContentType("text/html;charset=UTF-8");
        // Write the response message, in an HTML page

        // Create session
        Boolean accessTokenCheck = verifier.comapareClientIds(verifyAccessTokenResponse);
        if(!accessTokenCheck) {
            noAccessPage(out);
            return;
        }
        SessionManager.getInstance().setAccessToken(accessToken);
        accessPage(out, request, verifyAccessTokenResponse);
    }

    /**
     * Generate page with access
     */
    private void accessPage(PrintWriter out, HttpServletRequest request, String verifyAccessTokenResponse) {
        out.println("<!DOCTYPE html>");
        out.println("<html><head>");
        out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
        out.println("<title>Amazon Handle Login</title></head>");
        out.println("<body>");
        out.println("<font color=\"green\"> Successfuly logged! </font>");
        out.println("<p>Request URI: " + request.getRequestURI() + "</p>");
        out.println("<p>Protocol: " + request.getProtocol() + "</p>");
        out.println("<p>Remote Address: " + request.getRemoteAddr() + "</p>");
        out.println("<p>Verify Access Token: " + verifyAccessTokenResponse + "</p>");
        out.println("<p>Compare the aud value to the client_id you are using for your application. If they are different, the access token was not requested by your application, and you should not use the access token.</p>");
        String CLIENT_ID = "amzn1.application-oa2-client.78eefa18c9a741649d84af098aa7f915";
        out.println("<p>My client_id: " + CLIENT_ID + "</p>");
        out.println("Access Token validation: true");
        out.println("<form action=\"welcomeuser.jsp\">");
        out.println("<input type=\"submit\" value=\"ProfileInfo\" />");
        out.println("</form>");

        out.println("</body>");
        out.println("</html>");
        out.close(); // Always close the output writer
    }

    /**
     * Generate page with no access (wrong access token)
     */
    private void noAccessPage(PrintWriter out){
        out.println("<!DOCTYPE html>");
        out.println("<html><head>");
        out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
        out.println("<title>No Access</title></head>");
        out.println("<body>");
        out.println("<font color=\"red\">Wrong access token! </font>");
        out.println("</body>");
        out.println("</html>");
    }
}
