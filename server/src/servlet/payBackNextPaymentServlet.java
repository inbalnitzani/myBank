package servlet;

import engine.BankInterface;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.ServletUtils;

import java.io.FileNotFoundException;

@WebServlet(name = "payBackNextPaymentServlet", urlPatterns = "/payBackNextPayment")
public class payBackNextPaymentServlet extends HttpServlet {

    @Override protected void doGet(HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException {
        processRequest(request,response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html;charset=UTF-8");
        BankInterface bank = ServletUtils.getBank(getServletContext());
        String loanId = request.getParameter("loanId");
        String totalAmount = request.getParameter("totalAmount");
        String yaz = request.getParameter("yaz");
        synchronized (bank) {
            try {
                bank.payBackNextPayment(loanId,Double.parseDouble(totalAmount),Integer.parseInt(yaz));
                response.setStatus(HttpServletResponse.SC_OK);
            } catch (Exception err) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
    }
}
