package ru.practicum.ewm_main;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class ProductServiceInterceptor implements HandlerInterceptor {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    @Value("${stats-server-endpoint.url}")
    private URL url;
    @Value("${app-name}")
    private String app;

    @Override
    public void afterCompletion(HttpServletRequest servletRequest, HttpServletResponse response, Object handler, Exception exception) throws Exception {
        if (servletRequest.getContextPath().equals("/events") && response.getStatus() == HttpServletResponse.SC_OK) {


            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");

            JSONObject hit = new JSONObject();
            hit.put("ip", servletRequest.getRemoteAddr());
            hit.put("uri", servletRequest.getRequestURI());
            hit.put("app", app);
            hit.put("timestamp", LocalDateTime.now().format(DATE_TIME_FORMATTER));

            try (OutputStream os = con.getOutputStream()) {
                byte[] input = hit.toString().getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            } catch (final Exception ex) {
                System.out.println("Failed to send request.");
            }
        }
    }
}
