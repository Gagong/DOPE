package Utils;

import Json.JSONDataParser;
import okhttp3.*;
import java.io.*;
import org.jetbrains.annotations.NotNull;

public class Api {
    private static final OkHttpClient httpClient = new OkHttpClient();

    public static void Update() {
        Request request = new Request.Builder()
                .url("https://powerofdark.space/api/status")
                //.url("https://raw.githubusercontent.com/Gagong/DOPE/master/DOPE/status.json")
                .build();

        httpClient.newCall(request).enqueue(new Callback() {
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful()) {
                        this.serverUnaviableMethod();
                        throw new IOException("Unexpected code " + response);
                    }
                    assert responseBody != null;
                    String apiData = responseBody.string();
                    //Debug.p("API", "onResponse", apiData);
                    JSONDataParser.parser(apiData);
                }
            }
            private void serverUnaviableMethod() {
                Request request = new Request.Builder()
                        .url("https://raw.githubusercontent.com/Gagong/DOPE/master/DOPE/status.json")
                        .build();

                httpClient.newCall(request).enqueue(new Callback() {
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        e.printStackTrace();
                    }

                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        try (ResponseBody responseBody = response.body()) {
                            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                            assert responseBody != null;
                            String apiData = responseBody.string();
                            //Debug.p("API", "onResponse", apiData);
                            JSONDataParser.parser(apiData);
                        }
                    }
                });
            }
        });
    }
}

