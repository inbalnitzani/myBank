package servlet;

import engine.BankInterface;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.ServletUtils;

@WebServlet(name = "newLoanServlet", urlPatterns = "/newLoan")
public class newLoanServlet extends HttpServlet {

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

        String id=request.getParameter("loanName");
        String owner=request.getParameter("owner");
        String amountString=request.getParameter("Amount");
        String InterestString=request.getParameter("Interest");
        String category=request.getParameter("Category");
        String totalTimeString=request.getParameter("TotalTime");
        String paceString=request.getParameter("Pace");
        try {
            bank.addNewLoan(id,owner,Integer.parseInt(amountString),Integer.parseInt(InterestString),category,Integer.parseInt(totalTimeString),Integer.parseInt(paceString));
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception err) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }



}


