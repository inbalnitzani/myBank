package servlet;

import engine.BankInterface;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.ServletUtils;

import java.io.FileNotFoundException;


@WebServlet(name = "payApartOfDebtServlet", urlPatterns = "/payApartOfDebt")
public class payApartOfDebtServlet extends HttpServlet {

    @Override protected void doGet(HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException {
        processRequest(request,response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html;charset=UTF-8");
        BankInterface bank = ServletUtils.getBank(getServletContext());
        String loanId = request.getParameter("loanId");
        String amount = request.getParameter("amount");
        synchronized (bank) {
            try {
                bank.payApartOfDebt(loanId,Double.parseDouble(amount));
                response.setStatus(HttpServletResponse.SC_OK);
            } catch (Exception err) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
    }

}
