package clientsList;


import dto.ClientDTO;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import servlet.HttpClientUtil;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.TimerTask;
import java.util.function.Consumer;

public class clientsListRefresher extends TimerTask {
    private final Consumer<Map<String,ClientDTO>> usersListConsumer;


    public clientsListRefresher( Consumer<Map<String,ClientDTO>> usersListConsumer) {
        this.usersListConsumer = usersListConsumer;
    }

    @Override
    public void run() {
        String finalUrl = HttpUrl
                .parse("")
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
            //    Gson gson = new Gson();
             //   Map<String,ClientDTO> info = gson.fromJson(json, Map.class);
              //  usersListConsumer.accept(info);
                //usersListConsumer.accept(info.getLoans());
            }

        });
    }
}
