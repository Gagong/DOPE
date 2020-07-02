package com.dope.gagong.bots.Utils;

import com.dope.gagong.bots.Json.JSONDataParser;
import com.dope.gagong.bots.Protocols.JDAProtocol;
import com.dope.gagong.bots.Variables.Channels;
import com.dope.gagong.bots.Variables.Variables;
import net.dv8tion.jda.api.EmbedBuilder;
import okhttp3.*;
import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.TimeZone;

import org.jetbrains.annotations.NotNull;

public class Api {
    private static final OkHttpClient httpClient = new OkHttpClient();
    private static final Channels Channels = new Channels();
    private static final Variables Variables = new Variables();
    private static HttpURLConnection serverStatus = null;

    public static void Update() {
        DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("EEEE, MMM dd, yyyy HH:mm:ss a");
        LocalDateTime localTime = LocalDateTime.now(TimeZone.getTimeZone("UTC").toZoneId());
        String localTimeString = FORMATTER.format(localTime);

        Request request = new Request.Builder()
                .url("https://powerofdark.space/api/status")
                //.url("https://raw.githubusercontent.com/Gagong/DOPE/master/DOPE/status.json")
                .build();

        httpClient.newCall(request).enqueue(new Callback() {
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
                Objects.requireNonNull(JDAProtocol.JDA.getTextChannelById(Channels.SERVER_STATUS_LOGS)).sendMessage("```java\n" +
                        "HTTPExeption\n" +
                        localTimeString + "\n" +
                        e.getMessage() + "```").queue();
            }

            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                URL url = new URL("https://powerofdark.space");
                try {
                    serverStatus = (HttpURLConnection) url.openConnection();
                    serverStatus.setRequestMethod("GET");
                    serverStatus.connect();
                } catch (SocketTimeoutException e) {
                    Objects.requireNonNull(JDAProtocol.JDA.getTextChannelById(Channels.SERVER_STATUS_LOGS)).sendMessage("```java\n" +
                            "SocketTimeoutException\n" +
                            localTimeString + "\n" +
                            e.getMessage() + "```").queue();
                }

                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful()) {
                        this.serverUnaviableMethod();
                        throw new IOException("Unexpected code " + response);
                    }
                    assert responseBody != null;
                    String apiData = responseBody.string();
                    //Debug.p("API", "onResponse", apiData);
                    JSONDataParser.parser(apiData);
                    EmbedBuilder ServerStatus = new EmbedBuilder();
                    ServerStatus.setTitle("DOPE WEB SERVER STATUS: ONLINE")
                            .addField("Status Code:", "```arm\n" + serverStatus.getResponseCode() + "```", false)
                            .addField("Status Message:", "```arm\n" + serverStatus.getResponseMessage() + "```", false)
                            .addField("Last update:", "```fix\n" + localTimeString + "```", false)
                            .setColor(Color.GREEN)
                            .setTimestamp(Instant.now());
                    Objects.requireNonNull(JDAProtocol.JDA.getTextChannelById(Channels.SERVER_STATUS)).editMessageById(Variables.SERVER_STATUS_MESSAGE, ServerStatus.build()).queue();
                    //JDAProtocol.JDA.getTextChannelById(Channels.SERVER_STATUS).sendMessage(ServerStatus.build()).queue();
                }
            }
            private void serverUnaviableMethod() throws IOException {
                EmbedBuilder ServerStatus = new EmbedBuilder();
                ServerStatus.setTitle("DOPE WEB SERVER STATUS: OFFLINE")
                        .addField("Error Code:", "```arm\n" + serverStatus.getResponseCode() + "```", false)
                        .addField("Error Message:", "```arm\n" + serverStatus.getResponseMessage() + "```", false)
                        .addField("Last update:", "```fix\n" + localTimeString + "```", false)
                        .setColor(Color.RED)
                        .setTimestamp(Instant.now());
                Objects.requireNonNull(JDAProtocol.JDA.getTextChannelById(Channels.SERVER_STATUS)).editMessageById(Variables.SERVER_STATUS_MESSAGE, ServerStatus.build()).queue();
                Request request = new Request.Builder()
                        .url("https://raw.githubusercontent.com/Gagong/DOPE/master/DOPE/status.json")
                        .build();

                httpClient.newCall(request).enqueue(new Callback() {
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        e.printStackTrace();
                        Objects.requireNonNull(JDAProtocol.JDA.getTextChannelById(Channels.SERVER_STATUS_LOGS)).sendMessage("```java\n" +
                                "HTTPExeption\n" +
                                localTimeString + "\n" +
                                e.getMessage() + "```").queue();
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

