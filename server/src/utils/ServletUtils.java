package utils;
import engine.Bank;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import users.UserManager;
import static utils.Constants.INT_PARAMETER_ERROR;

public class ServletUtils {

    private static final String USER_MANAGER_ATTRIBUTE_NAME = "UserManager";
    private static final String BANK_ATTRIBUTE_NAME = "bank";
    private static final Object userManagerLock = new Object();
    private static final Object bankLock = new Object();

    public static UserManager getUserManager(ServletContext servletContext) {

        synchronized (userManagerLock) {
            if (servletContext.getAttribute(USER_MANAGER_ATTRIBUTE_NAME) == null) {
                servletContext.setAttribute(USER_MANAGER_ATTRIBUTE_NAME, new UserManager());
            }
        }
        return (UserManager) servletContext.getAttribute(USER_MANAGER_ATTRIBUTE_NAME);
    }

    public static Bank getBank(ServletContext servletContext) {

        synchronized (bankLock) {
            if (servletContext.getAttribute(BANK_ATTRIBUTE_NAME) == null) {
                servletContext.setAttribute(BANK_ATTRIBUTE_NAME, new Bank());
            }
        }
        return (Bank) servletContext.getAttribute(BANK_ATTRIBUTE_NAME);
    }
    public static int getIntParameter(HttpServletRequest request, String name) {
        String value = request.getParameter(name);
        if (value != null) {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException numberFormatException) {
            }
        }
        return INT_PARAMETER_ERROR;
    }
}

