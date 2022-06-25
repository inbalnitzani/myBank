package servlet;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import users.UserManager;
import utils.ServletUtils;

import java.io.IOException;


@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    @Override protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      //  response.setStatus(HttpServletResponse.SC_OK);
         processRequest(request,response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

      //  String usernameFromSession = SessionUtils.getUsername(request);
        ServletContext sCtx = request.getSession().getServletContext();

        UserManager userManager = ServletUtils.getUserManager(sCtx);

        response.setStatus(HttpServletResponse.SC_OK);
//        if (usernameFromSession == null) {
//            String usernameFromParameter = request.getParameter(USERNAME);
//            if (usernameFromParameter == null || usernameFromParameter.isEmpty()) {
//                //no username in session and no username in parameter - redirect back to the index page
//                //this return an HTTP code back to the browser telling it to load
//               response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//             //  response.sendRedirect(SIGN_UP_URL);
//            } else {
//                //normalize the username value
//                usernameFromParameter = usernameFromParameter.trim();
//
//                synchronized (this) {
//                    if (userManager.isUserExists(usernameFromParameter)) {
//                        String errorMessage = "Username " + usernameFromParameter + " already exists. Please enter a different username.";
//                        request.setAttribute(Constants.USER_NAME_ERROR, errorMessage);
//                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//                        // getServletContext().getRequestDispatcher(LOGIN_ERROR_URL).forward(request, response);
//                    }
//                    else {
//                        userManager.addUser(usernameFromParameter);
//                        request.getSession(true).setAttribute(Constants.USERNAME, usernameFromParameter);
//                        System.out.println("On login, request URI is: " + request.getRequestURI());
//                      //  response.sendRedirect(CHAT_ROOM_URL);
//                        response.setStatus(HttpServletResponse.SC_OK);
//                    }
//                }
//            }
//        }
//
//         else
//            response.setStatus(HttpServletResponse.SC_OK);
//            response.setStatus(201);
//            response.sendRedirect(CHAT_ROOM_URL);
      //
    }

    private final String CHAT_ROOM_URL = "../chatroom/chatroom.html";
    private final String SIGN_UP_URL = "../signup/signup.html";
    private final String LOGIN_ERROR_URL = "/pages/loginerror/login_attempt_after_error.jsp";
}


