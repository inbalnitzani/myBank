package servlet;

import com.google.gson.Gson;
import dto.infoForAdminDTO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import users.UserManager;
import utils.ServletUtils;

import java.util.Set;

import static java.lang.System.out;

@WebServlet(name = "adminRefresh", urlPatterns = "/adminRefresh")
public class adminRefresherServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response){
        response.setContentType("text/html;charset=UTF-8");
        try {
            Gson gson = new Gson();
            UserManager userManager = ServletUtils.getUserManager(getServletContext());
            Set<String> info = userManager.getUsers();
            String json = gson.toJson(info);
            out.println(json);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
