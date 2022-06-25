package servlet;

import app.constParameters;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import users.UserManager;
import utils.Constants;
import utils.ServletUtils;
import utils.SessionUtils;

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

        String loginType=request.getParameter(constParameters.LOGIN_TYPE);
        if (loginType.equals(constParameters.TYPE_CLIENT)) {
            clientLogin(request,response);
        } else {
            adminLogin(request,response);
        }
    }
    public void clientLogin(HttpServletRequest request, HttpServletResponse response){
        UserManager userManager = ServletUtils.getUserManager(getServletContext());
        String usernameFromParameter = request.getParameter(Constants.NAME).trim();

        if (usernameFromParameter == null || usernameFromParameter.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } else {
            if (userManager.isUserExists(usernameFromParameter)) {
                response.setStatus(HttpServletResponse.SC_CONFLICT);
            } else synchronized (this) {
                    userManager.addUser(usernameFromParameter);
                    response.setStatus(HttpServletResponse.SC_OK);
            }
        }
    }
    public void adminLogin(HttpServletRequest request, HttpServletResponse response){
        UserManager userManager = ServletUtils.getUserManager(getServletContext());
        String usernameFromParameter = request.getParameter(Constants.NAME).trim();

        if(userManager.isAdminInSystem()){
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        } else {
            if (usernameFromParameter == null || usernameFromParameter.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            } else synchronized (this) {
                userManager.addUser(usernameFromParameter);
                response.setStatus(HttpServletResponse.SC_OK);
            }
        }
    }
}


