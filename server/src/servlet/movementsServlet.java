package servlet;
import client.Movement;
import com.google.gson.Gson;
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


@WebServlet(name = "movementsServlet", urlPatterns = "/movements")
public class movementsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html;charset=UTF-8");
        String clientName = request.getParameter("client");
        BankInterface bank= ServletUtils.getBank(getServletContext());

        synchronized (bank){
            try (PrintWriter out = response.getWriter()) {
                Gson gson = new Gson();
                Map<Integer, List<MovementDTO>> movements = bank.getMovementsByClientName(clientName);
                String json = gson.toJson(movements);
                out.println(json);
                out.flush();
                response.setStatus(HttpServletResponse.SC_OK);
            } catch (Exception e){
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
    }
}