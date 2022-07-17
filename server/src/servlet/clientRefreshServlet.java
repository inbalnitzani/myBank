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
import utils.infoForClient;
import java.util.List;
import java.util.Map;


@WebServlet(name = "clientRefreshServlet", urlPatterns = "/clientRefresh")
public class clientRefreshServlet extends HttpServlet {

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
            String clientName = request.getParameter("client");
            int versionClient = Integer.parseInt(request.getParameter("version"));
            try {
                BankInterface bank = ServletUtils.getBank(getServletContext());
                synchronized (bank) {
                    Gson gson = new Gson();
                    int version = bank.getVersion();
                    if (version != versionClient) {
                        int yaz = bank.getWorldTime();
                        List<String> categories = bank.getCategories();
                        Double balance = bank.getCurrBalance(clientName);
                        Map<Integer, List<MovementDTO>> movements = bank.getMovementsByClientName(clientName);
                        List<LoanDTO> loansLender = bank.getLenderLoansByName(clientName);
                        List<LoanDTO> loansBorrower = bank.getBorrowerLoansByName(clientName);
                        Integer lookingBack = 0;
                        if (bank.getRewind())
                            lookingBack = bank.getLookingBack();
                        infoForClient info = new infoForClient(categories, balance, yaz, movements, loansLender, loansBorrower,version,lookingBack);
                        String json = gson.toJson(info);
                        response.addHeader("NeedToRefresh", "true");
                        response.getWriter().println(json);
                        response.getWriter().flush();
                    } else {
                        response.addHeader("NeedToRefresh", "false");
                        response.addHeader("version", String.valueOf(version));
                    }
                    response.setStatus(HttpServletResponse.SC_OK);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
}
