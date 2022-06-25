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

import java.io.IOException;


@WebServlet(name = "LogoutServlet", urlPatterns = "/logout")
public class LogoutServlet extends HttpServlet {

    @Override protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html;charset=UTF-8");

        String logoutType=request.getParameter(constParameters.LOGIN_TYPE);
        if (logoutType.equals(constParameters.TYPE_CLIENT)) {
            clientLogout(request,response);
        } else {
            adminLogout(response);
        }
    }
    public void clientLogout(HttpServletRequest request, HttpServletResponse response) {
        UserManager userManager = ServletUtils.getUserManager(getServletContext());
        synchronized (this) {
            userManager.removeUser(request.getParameter(Constants.NAME).trim());
        }
        response.setStatus(HttpServletResponse.SC_OK);
    }
    public void adminLogout(HttpServletResponse response) {
        UserManager userManager = ServletUtils.getUserManager(getServletContext());
        synchronized (this) {
            userManager.removeAdminFromSystem();
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }
}



