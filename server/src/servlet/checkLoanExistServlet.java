package servlet;

import engine.BankInterface;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.ServletUtils;

import java.io.FileNotFoundException;

@WebServlet(name = "checkLoanExistServlet", urlPatterns = "/checkLoanExist")
public class checkLoanExistServlet extends HttpServlet {

    @Override protected void doGet(HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException {
        processRequest(request,response);
    }
    @Override protected void doPost(HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException {
        processRequest(request,response);
    }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)  {
        response.setContentType("text/html;charset=UTF-8");
        BankInterface bank= ServletUtils.getBank(getServletContext());
        try {
            Boolean answer = bank.checkLoanExist(request.getParameter("loanName"));
            response.addHeader("IsLoanExist",answer.toString());
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception err){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}


