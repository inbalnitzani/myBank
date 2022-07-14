package servlet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dto.LoanDTO;
import engine.BankInterface;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.ServletUtils;

import java.lang.reflect.Type;
import java.util.List;



@WebServlet(name = "startInlayProcessServlet", urlPatterns = "/startInlayProcess")
public class startInlayProcessServlet extends HttpServlet {

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
        BankInterface bank= ServletUtils.getBank(getServletContext());
        List<LoanDTO> loans;
        try {
            Type listType = new TypeToken<List<LoanDTO>>() {}.getType();
            String data = request.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);
            Gson gson = new Gson();
            loans = gson.fromJson(data, listType);

            synchronized (bank){
                int amountLeft = bank.startInlayProcess(loans,clientName);
                response.addHeader("amountLeft", String.valueOf(amountLeft));
                response.setStatus(HttpServletResponse.SC_OK);
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}