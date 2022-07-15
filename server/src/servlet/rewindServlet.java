package servlet;

import com.google.gson.Gson;
import dto.stateDTO;
import engine.Bank;
import engine.BankInterface;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.ServletUtils;

import java.util.Map;


@WebServlet(name = "rewind", urlPatterns = "/rewind")
public class rewindServlet extends HttpServlet{

    protected void doGet(HttpServletRequest request, HttpServletResponse response){
        response.setContentType("text/html;charset=UTF-8");
        try {
            Gson gson = new Gson();
            BankInterface bank = ServletUtils.getBank(getServletContext());
            bank.setRewind(true);
            synchronized (bank) {

                stateDTO state = bank.getStates().get(Integer.parseInt(request.getParameter("yaz")));
                String json = gson.toJson(state);

                response.getWriter().println(json);
                response.getWriter().flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}