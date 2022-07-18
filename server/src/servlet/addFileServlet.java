package servlet;
import engine.BankInterface;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.ServletUtils;
import java.io.IOException;

@WebServlet(name = "addFileToSystem", urlPatterns = "/addFileServlet")
public class addFileServlet extends HttpServlet {

    @Override protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        processRequest(request,response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        BankInterface bank=ServletUtils.getBank(getServletContext());
        String clientName = request.getParameter("Name");
       try {
           bank.addNewXMLFile(request.getParameter("Path"),clientName);
           response.setStatus(HttpServletResponse.SC_OK);
       } catch (Exception err){
           response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
           response.getOutputStream().print(err.getMessage());
       }
    }
}


