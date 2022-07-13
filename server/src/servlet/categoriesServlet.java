package servlet;

import com.google.gson.Gson;
import dto.LoanDTO;
import engine.BankInterface;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.ServletUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;


@WebServlet(name = "categoriesServlet", urlPatterns = "/categories")
public class categoriesServlet extends HttpServlet {

    @Override protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        BankInterface bank = ServletUtils.getBank(getServletContext());

        synchronized (bank) {
            try (PrintWriter out = response.getWriter()) {
                Gson gson = new Gson();
                List<String> categories = bank.getCategories();
                String json = gson.toJson(categories);
                out.println(json);
                out.flush();
                response.setStatus(HttpServletResponse.SC_OK);
            } catch (Exception e) {
                String errorMessage = "Failed to read categories!";
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getOutputStream().print(errorMessage);
            }
        }
    }
}
