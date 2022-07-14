package app.refreshdata;

import app.homePage.clientHomePageController;
import com.google.gson.Gson;
import com.sun.istack.internal.NotNull;
import dto.MovementDTO;
import dto.infoForAdminDTO;
import javafx.application.Platform;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import servlet.HttpClientUtil;
import utils.infoForClient;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;
import java.util.function.Consumer;

    public class dataRefresher extends TimerTask {

        private final Consumer <List<String>> categoriesConsumer;
        private final Consumer <Double> balanceConsumer;
        private final Consumer <Integer> yazConsumer;
        private final Consumer <Map<Integer,List<MovementDTO>>> movements;
        private clientHomePageController homePageController;

        public void setHomePageController(clientHomePageController controller){
            this.homePageController=controller;
        }
        public dataRefresher(Consumer <List<String>> categoriesConsumer,Consumer <Double> balanceConsumer,Consumer <Integer> yazConsumer, Consumer<Map<Integer,List<MovementDTO>>> movements ) {
            this.categoriesConsumer = categoriesConsumer;
            this.balanceConsumer = balanceConsumer;
            this.yazConsumer = yazConsumer;
            this.movements = movements;
        }

        @Override
        public void run() {
            String finalUrl = HttpUrl
                    .parse("http://localhost:8080/demo_Web_exploded/clientRefresh")
                    .newBuilder()
                    .addQueryParameter("client", homePageController.getClientName())
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
                    infoForClient info = gson.fromJson(json, infoForClient.class);
                    Platform.runLater(()->{
                        balanceConsumer.accept(info.getBalance());
                        yazConsumer.accept(info.getYaz());
                        categoriesConsumer.accept(info.getCategories());
                        movements.accept(info.getMovements());
                    });
                }
            });
        }
    }

