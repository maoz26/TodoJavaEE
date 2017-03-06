/**
 * Created by maoztamir on 19/01/2017.
 */

package listeners;

import java.util.HashMap;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Session Counter Class
 * Counts How Many Session Are In Our Web App
 */
public class SessionCounterListener implements HttpSessionListener {

    private static SessionCounterListener instance;
    private static int totalActiveSessions;
    private static HashMap<String, HttpSession> activesessions = new HashMap<String, HttpSession>();
    public final static Logger logger = Logger.getLogger(SessionCounterListener.class.getName());

    public SessionCounterListener() { }

    public static HashMap<String, HttpSession> getActivesessions() {
        return activesessions;
    }

    public static void setActivesessions(HashMap<String, HttpSession> activesessions) {
        SessionCounterListener.activesessions = activesessions;
    }

    /**
     * Sessions Count Getter
     * @return
     */
    public static int getTotalActiveSession(){
        return totalActiveSessions;
    }

    /**
     * This Function will help us count the number
     * of active sessions & Adding them to our map
     */
    @Override
    public void sessionCreated(HttpSessionEvent incoming_session) {
        totalActiveSessions++;
        activesessions.put(incoming_session.getSession().getId(), incoming_session.getSession());
        logger.info("Session Id: "+incoming_session.getSession().getId() +" Created");
        System.out.println("sessionCreated - add one session into counter");
    }

    /**
     * Function to lower by 1 the number of sessions,
     * and erase session from the session map
     */
    @Override
    public void sessionDestroyed(HttpSessionEvent arg0) {
        totalActiveSessions--;
        logger.info("Session Id: "+arg0.getSession().getId() +" Deleted");
        activesessions.remove(arg0.getSession().getId());
        System.out.println("sessionDestroyed - deduct one session from counter");
    }
}
