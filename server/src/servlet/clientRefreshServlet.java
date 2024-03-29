package servlet;
import com.google.gson.Gson;
import dto.ClientDTO;
import dto.LoanDTO;
import dto.MovementDTO;
import dto.clientStateDTO;
import engine.BankInterface;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import loan.Status;
import utils.ServletUtils;
import utils.infoForClient;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


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
                    Integer timeToLookBackAt = 0;
                    int yaz = bank.getWorldTime();
                    List<String> categories = bank.getCategories();
                    Double balance;
                    Map<Integer, List<MovementDTO>> movements;
                    List<LoanDTO> loansLender;
                    List<LoanDTO> loansBorrower;
                    List<LoanDTO> loansForSale = null;
                    Gson gson = new Gson();
                    int version = bank.getVersion();
                    if (version != versionClient) {
                        if (bank.getRewind()) {
                        timeToLookBackAt = bank.getLookingBack();
                        clientStateDTO state = bank.getClientForRewind(clientName,timeToLookBackAt);
                            ClientDTO client = state.getClient();

                            balance = client.getCurrBalance();
                            movements = client.getMovements();
                            loansLender = client.getLoansAsGiver();
                            loansBorrower = client.getLoansAsBorrower();
                        }

                        else {
                            balance = bank.getCurrBalance(clientName);
                            movements = bank.getMovementsByClientName(clientName);
                            loansLender = bank.getLenderLoansByName(clientName);
                            loansBorrower = bank.getBorrowerLoansByName(clientName);
                            loansForSale = bank.getLoansForSale(clientName);

                        }

                        infoForClient info = new infoForClient(categories, balance, yaz, movements, loansLender, loansBorrower,version,timeToLookBackAt,loansForSale);
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
