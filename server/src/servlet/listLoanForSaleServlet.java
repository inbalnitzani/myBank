package servlet;

import com.google.gson.Gson;
import engine.BankInterface;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.ServletUtils;

@WebServlet(name = "listLoanForSale", urlPatterns = "/listLoanForSale")
public class listLoanForSaleServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response){
        response.setContentType("text/html;charset=UTF-8");
        try {
            Gson gson = new Gson();
            BankInterface bank = ServletUtils.getBank(getServletContext());

            synchronized (bank) {
                String loan = request.getParameter("loan");
                String client = request.getParameter("client");
                bank.listLoanForSale(loan,client);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
