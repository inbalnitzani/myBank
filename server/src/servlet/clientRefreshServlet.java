package servlet;

import client.Movement;
import com.google.gson.Gson;
import dto.ClientDTO;
import dto.LoanDTO;
import dto.MovementDTO;
import dto.infoForAdminDTO;
import engine.BankInterface;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.ServletUtils;
import utils.infoForClient;

import java.util.List;
import java.util.Map;


@WebServlet(name = "clientRefreshServlet", urlPatterns = "/clientRefresh")
public class clientRefreshServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        processRequest(request, response);
    }

        protected void processRequest(HttpServletRequest request, HttpServletResponse response) {
            response.setContentType("text/html;charset=UTF-8");
            String clientName = request.getParameter("client");

            try {
                Gson gson = new Gson();
                BankInterface bank = ServletUtils.getBank(getServletContext());
                synchronized (bank){
                    int yaz = bank.getWorldTime();
                    List<String> categories = bank.getCategories();
                    Double balance = bank.getCurrBalance(clientName);
                    Map<Integer,List<MovementDTO>> movements = bank.getMovementsByClientName(clientName);
                    infoForClient info = new infoForClient(categories,balance,yaz,movements);
                    String json = gson.toJson(info);
                    response.getWriter().println(json);
                    response.getWriter().flush();
                    response.setStatus(HttpServletResponse.SC_OK);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
}
