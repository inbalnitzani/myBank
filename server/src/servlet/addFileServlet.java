package servlet;

import app.constParameters;
import engine.BankInterface;
import exception.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import users.UserManager;
import utils.Constants;
import utils.ServletUtils;
import java.io.FileNotFoundException;

@WebServlet(name = "addFileToSystem", urlPatterns = "/addFileServlet")
public class addFileServlet extends HttpServlet {

    @Override protected void doGet(HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException {
        processRequest(request,response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)  {
        response.setContentType("text/html;charset=UTF-8");
        BankInterface bank=ServletUtils.getBank(getServletContext());
       try {
           bank.addNewXMLFile(request.getParameter("Path"));
       } catch (Exception err){

       }
    }
}


