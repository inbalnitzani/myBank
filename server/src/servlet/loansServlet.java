package servlet;

import com.google.gson.Gson;
import dto.LoanDTO;
import dto.MovementDTO;
import engine.BankInterface;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.ServletUtils;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;


@WebServlet(name = "loansServlet", urlPatterns = "/loans")
public class loansServlet extends HttpServlet {

        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) {
            processRequest(request, response);
        }

        protected void processRequest(HttpServletRequest request, HttpServletResponse response) {
            response.setContentType("text/html;charset=UTF-8");
            String clientName = request.getParameter("client");
            String loansType = request.getParameter("loansType");
            BankInterface bank= ServletUtils.getBank(getServletContext());

            synchronized (bank){
                try (PrintWriter out = response.getWriter()) {
                    Gson gson = new Gson();
                    List<LoanDTO> loansDTOS=null;
                    switch (loansType){
                        case "borrower":
                            loansDTOS=bank.getBorrowerLoansByName(clientName);
                            response.setStatus(HttpServletResponse.SC_OK);
                            break;
                        case "lender":
                            loansDTOS=bank.getLenderLoansByName(clientName);
                            response.setStatus(HttpServletResponse.SC_OK);
                            break;
                        default:
                            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    }
                    String json = gson.toJson(loansDTOS);
                    out.println(json);
                    out.flush();
                } catch (Exception e){
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                }
            }
        }
}
