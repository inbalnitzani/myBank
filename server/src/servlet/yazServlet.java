package servlet;

import com.google.gson.Gson;
import dto.LoanDTO;
import engine.BankInterface;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.ServletUtils;

import java.io.PrintWriter;
import java.util.List;


@WebServlet(name = "yazServlet", urlPatterns = "/yaz")
public class yazServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html;charset=UTF-8");
        BankInterface bank = ServletUtils.getBank(getServletContext());

        synchronized (bank) {
            try {
                int yaz = bank.getWorldTime();
                response.addHeader("yaz", String.valueOf(yaz));
                response.setStatus(HttpServletResponse.SC_OK);
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
    }

}