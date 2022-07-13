package clientsList;

import com.google.gson.Gson;
import dto.ClientDTO;
import dto.LoanDTO;
import dto.infoForAdminDTO;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import servlet.HttpClientUtil;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;
import java.util.function.Consumer;

public class clientsListRefresher extends TimerTask {
    private final Consumer<List<ClientDTO>> usersListConsumer;
    private final Consumer<List<LoanDTO>> loansConsumer;

    public clientsListRefresher(Consumer<List<ClientDTO>> usersListConsumer, Consumer<List<LoanDTO>> loansConsumer) {
        this.usersListConsumer = usersListConsumer;
        this.loansConsumer = loansConsumer;
    }

    @Override
    public void run() {
        String finalUrl = HttpUrl
                .parse("http://localhost:8080/demo_Web_exploded/adminRefresh")
                .newBuilder()
                .build()
                .toString();
        HttpClientUtil.runAsync(finalUrl, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                System.out.println("OnFailure");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String json = response.body().string();
                Gson gson = new Gson();
                infoForAdminDTO info = gson.fromJson(json, infoForAdminDTO.class);
                usersListConsumer.accept(info.getClientsInfo());
                loansConsumer.accept(info.getLoansInfo());
            }

        });
    }
}
