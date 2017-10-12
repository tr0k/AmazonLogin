package servlets;

import aws.ListAccessKeys;
import beans.SessionManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by tr0k on 2017-10-12.
 */
public class AWSServlet extends HttpServlet{

    private List<String> accessKeys;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter out = resp.getWriter();

        if(!SessionManager.getInstance().isSessionValid()) {
            noAccessPage(out);
            return;
        }

        // Invoke AWS
        ListAccessKeys lak = new ListAccessKeys("Emilos");
        accessKeys = lak.listAccessKeys();
        accessPage(out);
    }

    /**
     * Generate page with access
     */
    private void accessPage(PrintWriter out) {
        out.println("<!DOCTYPE html>");
        out.println("<html><head>");
        out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
        out.println("<title>AWS Usage</title></head>");
        out.println("<body>");
        if(accessKeys != null && accessKeys.size() > 0){
            out.println("<span style=\"color: green; \">You have connected to Amazon Web Service IAM! </span><br/>");
        }
        out.println("Access keys for Emilos user:<br/>");
        for (String key : accessKeys){
            out.println(key);
            out.println("<br/>");
        }
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
