package dev.Abhishek.PaymentService.client;

import dev.Abhishek.PaymentService.dto.orderDto.OrderStatusUpdateRequestDto;
import dev.Abhishek.PaymentService.exception.ClientException.OrderServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
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
        try {
            ResponseEntity<Void> response = restTemplate.postForEntity(url, requestDto, Void.class);
            // Handle response if necessary
        } catch (ResourceAccessException e) {
            throw new OrderServiceException("Failed to update order status : Timeout or resource access issue.");
        } catch (RestClientException e) {
            throw new OrderServiceException("Failed to update order status: An error occurred during REST call.");
        }
    }


}
