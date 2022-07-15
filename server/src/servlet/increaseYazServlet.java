package servlet;

import com.google.gson.Gson;
import engine.Bank;
import engine.BankInterface;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kotlin.jvm.Synchronized;
import users.UserManager;
import utils.ServletUtils;


@WebServlet(name = "increaseYaz", urlPatterns = "/increaseYaz")
public class increaseYazServlet extends HttpServlet{

    protected void doGet(HttpServletRequest request, HttpServletResponse response){
        response.setContentType("text/html;charset=UTF-8");
        try {
            Gson gson = new Gson();
            BankInterface bank = ServletUtils.getBank(getServletContext());
            synchronized (bank) {

                bank.saveStateToMap();
                bank.promoteTime();
                int curYaz = bank.getWorldTime();
                String json = gson.toJson(curYaz);

                response.getWriter().println(json);
                response.getWriter().flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
