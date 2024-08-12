package dev.Abhishek.PaymentService.client;

import dev.Abhishek.PaymentService.dto.orderDto.OrderStatusUpdateRequestDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


public class OrderClient {
    private RestTemplateBuilder restTemplateBuilder;
    private String OrderServiceBaseUrl;
    private String orderServiceUpdateOrderStatusPath;

    public OrderClient(RestTemplateBuilder restTemplateBuilder,@Value("{orderService.api.base.url}") String orderServiceBaseUrl,@Value("{orderService.api.updateOrderStatus.path}") String orderServiceUpdateOrderStatusPath) {
        this.restTemplateBuilder = restTemplateBuilder;
        OrderServiceBaseUrl = orderServiceBaseUrl;
        this.orderServiceUpdateOrderStatusPath = orderServiceUpdateOrderStatusPath;
    }
    public void updateOrderStatus(OrderStatusUpdateRequestDto requestDto) {
        String url = OrderServiceBaseUrl.concat(orderServiceUpdateOrderStatusPath);
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<Void> response = restTemplate.postForEntity(url, requestDto, Void.class);
        // Handle response if necessary
    }

}
