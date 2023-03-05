package ru.practicum.ewm_main.paypal.client;

import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.orders.*;
import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.ewm_main.paypal.dto.PaymentOrder;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentClient {
    @Autowired
    PayPalHttpClient payPalHttpClient;

    @SneakyThrows
    public PaymentOrder createOrder(Double price) {
        OrdersCreateRequest ordersCreateRequest = new OrdersCreateRequest();
        ordersCreateRequest.header("prefer", "return=representation");
        ordersCreateRequest.requestBody(buildBody(price));

        HttpResponse<Order> orderHttpResponse = payPalHttpClient.execute(ordersCreateRequest);

        Order order = orderHttpResponse.result();

        String redirectUrl = order.links().stream()
                .filter(link -> "approve".equals(link.rel()))
                .findFirst()
                .orElseThrow(NoSuchElementException::new)
                .href();

        return PaymentOrder.builder()
                .status(order.status())
                .payId(order.id())
                .redirectUrl(redirectUrl)
                .build();
    }

    //Данная можель используется при реальном взаимодействии с Paypal sandbox

    /*@SneakyThrows
    public Order confirm(String token) {
        OrdersCaptureRequest ordersCaptureRequest = new OrdersCaptureRequest(token);

        HttpResponse<Order> httpResponse = payPalHttpClient.execute(ordersCaptureRequest);
        return httpResponse.result();
    }*/

    private OrderRequest buildBody(Double price) {
        OrderRequest orderRequest = new OrderRequest();

        orderRequest.checkoutPaymentIntent("CAPTURE");

        AmountWithBreakdown amountBreakdown = new AmountWithBreakdown().currencyCode("USD").value(price.toString());
        PurchaseUnitRequest purchaseUnitRequest = new PurchaseUnitRequest().amountWithBreakdown(amountBreakdown);
        orderRequest.purchaseUnits(List.of(purchaseUnitRequest));

        ApplicationContext applicationContext = new ApplicationContext()
                .returnUrl("https://localhost:8080/success")
                .cancelUrl("https://localhost:8080/cancel");
        orderRequest.applicationContext(applicationContext);

        return orderRequest;
    }
}
