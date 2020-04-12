package me.aaronakhtar.rf_sdk;

import me.aaronakhtar.rf_sdk.exceptions.RaidForumsGeneralException;
import me.aaronakhtar.rf_sdk.objects.User;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RaidForumsApi {

    private String url;
    private Map<String, String> request_headers;

    private RaidForumsApi(String url, Map<String, String> request_headers) throws MalformedURLException, URISyntaxException {
        this.url = url;
        this.request_headers = request_headers;
        new URL(url).toURI();                                   // test if @url is valid
    }

    public static RaidForumsApi getInstance() throws MalformedURLException, URISyntaxException {
        String url = "https://raidforums.com";
        Map<String, String> request_headers = new HashMap<String, String>();
        request_headers.put("User-Agent", "Mozilla/5.0 (Linux; Android 5.1.1; 2014817 Build/LMY47V) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.111 Mobile Safari/537.36");
        request_headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        return new RaidForumsApi(url, request_headers);
    }


    public User getUser(String username) throws Exception {
        List<String> source_code = new ArrayList<>();
        final HttpURLConnection connection = (HttpURLConnection) new URL(url + "/User-" + username).openConnection();
        try (AutoCloseable autoCloseable = () -> connection.disconnect()) {
            connection.setRequestMethod("GET");
            for (String x : request_headers.keySet()) {
                connection.addRequestProperty(x, request_headers.get(x));
            }
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                try (AutoCloseable autoCloseable11 = () -> reader.close()) {
                    String f;
                    while ((f = reader.readLine()) != null) {
                        source_code.add(f);
                    }
                }
            } catch (FileNotFoundException e) {
                throw new RaidForumsGeneralException("User does not exist");
            }
        }
        Map<String, String> userValues = new HashMap<>();
        boolean f = false;
        boolean f1 = false;
        for (String l : source_code) {
            String[] x = l.replaceAll("\\<.*?>", "").split(":");

            if (f) {
                userValues.put("Total Threads", l.replaceAll("\\<.*?>", "").split(" ")[0]);
                f = false;
                continue;
            } else if (f1) {
                userValues.put("Total Posts", l.replaceAll("\\<.*?>", "").split(" ")[0]);
                f1 = false;
                continue;
            }

            if (l.contains("The member you specified is either invalid or doesn't exist.")) {
                throw new RaidForumsGeneralException("User does not exist");
            } else if (l.contains("Joined")) {
                userValues.put("Join Date", x[1]);
            } else if (l.contains("Time Spent Online")) {
                userValues.put("Time Spent Online", x[1]);
            } else if (l.contains("Total Threads")) {
                f = true;
                continue;
            } else if (l.contains("Total Posts")) {
                f1 = true;
                continue;
            }

        }
        if (!userValues.isEmpty()) {
            return new User(username, userValues.get("Join Date"), userValues.get("Time Spent Online"), userValues.get("Total Posts"), userValues.get("Total Threads"));
        }

        return null;
    }


}
