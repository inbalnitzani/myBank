package app.refreshdata;

import app.homePage.clientHomePageController;
import com.google.gson.Gson;
import com.sun.istack.internal.NotNull;
import dto.LoanDTO;
import dto.MovementDTO;
import dto.clientStateDTO;
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
        private Consumer <List<LoanDTO>> loanLenderConsumer;
        private Consumer <List<LoanDTO>> loanLonerConsumer;
        private Consumer <List<LoanDTO>> loanToBuyConsumer;
        private Consumer<Integer> version;
        private Consumer<Integer> lookingBack;

        public void setHomePageController(clientHomePageController controller){
            this.homePageController=controller;
        }
        public dataRefresher(Consumer <List<String>> categoriesConsumer,Consumer <Double> balanceConsumer,Consumer <Integer> yazConsumer, Consumer<Map<Integer,List<MovementDTO>>> movements,Consumer <List<LoanDTO>> loanLenderConsumer, Consumer <List<LoanDTO>> loanLonerConsumer,Consumer<Integer> version,Consumer<Integer> lookingBack,Consumer<List<LoanDTO>>loanToBuyConsumer) {
            this.categoriesConsumer = categoriesConsumer;
            this.balanceConsumer = balanceConsumer;
            this.yazConsumer = yazConsumer;
            this.movements = movements;
            this.loanLonerConsumer = loanLonerConsumer;
            this.loanLenderConsumer = loanLenderConsumer;
            this.version=version;
            this.lookingBack = lookingBack;
            this.loanToBuyConsumer = loanToBuyConsumer;
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
                               balanceConsumer.accept(info.getBalance());
                               yazConsumer.accept(info.getYaz());
                               categoriesConsumer.accept(info.getCategories());
                               movements.accept(info.getMovements());
                               loanLenderConsumer.accept(info.getLoanLender());
                               loanLonerConsumer.accept(info.getLoanLoner());
                               version.accept(info.getVersion());
                               lookingBack.accept(info.getLookingBack());
                               loanToBuyConsumer.accept(info.getLoansForSale());
                       });
                   }
                }
            });
        }
    }

