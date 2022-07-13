package servlet;

import com.google.gson.Gson;
import dto.ClientDTO;
import dto.LoanDTO;
import dto.infoForAdminDTO;
import engine.BankInterface;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import users.UserManager;
import utils.ServletUtils;

import java.util.List;
import java.util.Set;

import static java.lang.System.out;

@WebServlet(name = "adminRefresh", urlPatterns = "/adminRefresh")
public class adminRefresherServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response){
        response.setContentType("text/html;charset=UTF-8");
        try {
            Gson gson = new Gson();
            BankInterface bank = ServletUtils.getBank(getServletContext());
            List<ClientDTO> clients = bank.getClients();
            List<LoanDTO> loans = bank.getAllLoans();
            infoForAdminDTO info = new infoForAdminDTO(clients,loans);
            String json = gson.toJson(info);

            response.getWriter().println(json);
            response.getWriter().flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
