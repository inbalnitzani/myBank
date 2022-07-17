package app.refreshdata;

import app.homePage.clientHomePageController;
import com.google.gson.Gson;
import com.sun.istack.internal.NotNull;
import dto.LoanDTO;
import dto.MovementDTO;
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
        private Consumer <List<LoanDTO>> loanGiverConsumer;
        private Consumer <List<LoanDTO>> loanBorrowerConsumer;
        private Consumer<Integer> version;

        public void setHomePageController(clientHomePageController controller){
            this.homePageController=controller;
        }
        public dataRefresher(Consumer <List<String>> categoriesConsumer, Consumer <Double> balanceConsumer, Consumer <Integer> yazConsumer, Consumer<Map<Integer,List<MovementDTO>>> movements, Consumer <List<LoanDTO>> loanGiverConsumer, Consumer <List<LoanDTO>> loanBorrowerConsumer, Consumer<Integer> version) {
            this.categoriesConsumer = categoriesConsumer;
            this.balanceConsumer = balanceConsumer;
            this.yazConsumer = yazConsumer;
            this.movements = movements;
            this.loanBorrowerConsumer = loanBorrowerConsumer;
            this.loanGiverConsumer = loanGiverConsumer;
            this.version=version;
        }

        @Override
        public void run() {
            String finalUrl = HttpUrl
                    .parse("http://localhost:8080/demo_Web_exploded/clientRefresh")
                    .newBuilder()
                    .addQueryParameter("client", homePageController.getClientName())
                    .addQueryParameter("version", String.valueOf(homePageController.getVersion()))
                    .build()
                    .toString();

            HttpClientUtil.runAsync(finalUrl, new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    System.out.println("OnFailure");
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                   Boolean needToRefresh = Boolean.valueOf(response.header("NeedToRefresh"));
                   if(needToRefresh) {
                       String json = response.body().string();
                       Gson gson = new Gson();
                       infoForClient info = gson.fromJson(json, infoForClient.class);
                       Platform.runLater(() -> {
                           version.accept(info.getVersion());
                           yazConsumer.accept(info.getYaz());
                           balanceConsumer.accept(info.getBalance());
                           categoriesConsumer.accept(info.getCategories());
                           movements.accept(info.getMovements());
                           loanGiverConsumer.accept(info.getLoanLender());
                           loanBorrowerConsumer.accept(info.getLoanLoner());
                       });
                   }
                }
            });
        }
    }

