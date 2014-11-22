package vocabspa.server;


import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Alek on 5/7/2014.
 */
public class DefaultServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        /*for (Cookie cookie : req.getCookies()) {
            if (cookie.getName().equals("timezoneoffset")) {
                req.setAttribute(cookie.getName(), cookie.getValue());
                break;
            }
        }*/
        req.getRequestDispatcher("/welcome.jsp").forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

    }

    
}
