package ru.practicum.ewm_main;

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

    @Override
    public void afterCompletion(HttpServletRequest servletRequest, HttpServletResponse response, Object handler, Exception exception) throws Exception {
        if (servletRequest.getContextPath().equals("/events") && response.getStatus() == HttpServletResponse.SC_OK) {

            URL url = new URL("https://localhost:9090/hit");

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");

            JSONObject hit = new JSONObject();
            hit.put("ip", servletRequest.getRemoteAddr());
            hit.put("uri", servletRequest.getRequestURI());
            hit.put("app", "main-server");
            hit.put("app", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

            try (OutputStream os = con.getOutputStream()) {
                byte[] input = hit.toString().getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            } catch (final Exception ex) {
                System.out.println("Failed to send request.");
            }
        }
    }
}
