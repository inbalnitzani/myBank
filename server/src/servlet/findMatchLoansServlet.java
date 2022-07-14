package servlet;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dto.LoanDTO;
import engine.BankInterface;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import loan.Loan;
import loan.LoanTerms;
import utils.ServletUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;


@WebServlet(name = "findMatchLoansServlet", urlPatterns = "/findMatchLoans")
public class findMatchLoansServlet extends HttpServlet {

    @Override protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        processRequest(request, response);
    }

    @Override protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        BankInterface bank = ServletUtils.getBank(getServletContext());
        String clientName = request.getParameter("client");
        String data = request.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);
        Gson gson = new Gson();
        LoanTerms loanTerms = gson.fromJson(data, LoanTerms.class);

        synchronized (bank) {
            List<LoanDTO> matchLoans = bank.findMatchLoans(clientName, loanTerms);
            if (!matchLoans.isEmpty()) {
                try (PrintWriter out = response.getWriter()) {
                    String json = gson.toJson(matchLoans);
                    out.println(json);
                    out.flush();
                    response.setStatus(HttpServletResponse.SC_OK);
                } catch (Exception e) {
                    String errorMessage = "Failed to read loans. Please try again..";
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getOutputStream().print(errorMessage);
                }
            }else {
                response.setStatus(HttpServletResponse.SC_OK);
            }
        }
    }
}
