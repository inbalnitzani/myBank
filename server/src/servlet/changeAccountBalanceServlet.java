package servlet;

import engine.BankInterface;
import exception.NotEnoughMoney;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.ServletUtils;

import java.io.IOException;

@WebServlet(name = "changeAccountBalanceServlet", urlPatterns = "/changeAccountBalance")
public class changeAccountBalanceServlet extends HttpServlet {

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
        BankInterface bank = ServletUtils.getBank(getServletContext());
        String clientName = request.getParameter("owner");
        Double amount = Double.parseDouble(request.getParameter("amount"));

        synchronized (bank) {
            Double accountBalance = bank.getCurrBalance(clientName);

            switch (request.getParameter("TypeMovement")) {
                case "withdraw":
                    try {
                        if (amount > accountBalance) {
                            String errorMessage = "Client: " + clientName + " has only " + accountBalance + " ILS. Can't withdraw more than this!";
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.getOutputStream().print(errorMessage);
                        } else {
                            accountBalance = bank.withdrawMoneyFromAccount(clientName, amount);
                            response.addHeader("accountBalance", String.valueOf(accountBalance));
                            response.setStatus(HttpServletResponse.SC_OK);
                        }
                    } catch (Exception e) {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    }
                    break;
                case "charge":
                    accountBalance = bank.loadMoney(clientName, amount);
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.addHeader("accountBalance", String.valueOf(accountBalance));
                    break;
                default:
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    break;
            }
        }
    }
}
