package dev.Abhishek.PaymentService.service;

import dev.Abhishek.PaymentService.client.OrderClient;
import dev.Abhishek.PaymentService.dto.orderDto.OrderStatusUpdateRequestDto;
import dev.Abhishek.PaymentService.entity.orderEntity.OrderStatus;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class RazorpayWebhookServiceImpl implements RazorpayWebhookService{
    private OrderClient orderClient;

    public RazorpayWebhookServiceImpl(OrderClient orderClient) {
        this.orderClient = orderClient;
    }

    public void processWebhook(String payload) {
        JSONObject jsonObject = new JSONObject(payload);
        String event = jsonObject.getString("event");
        String paymentId = jsonObject.getJSONObject("payload")
                .getJSONObject("payment")
                .getJSONObject("entity")
                .getString("id");

        String orderId = jsonObject.getJSONObject("payload")
                .getJSONObject("payment")
                .getJSONObject("entity")
                .getString("order_id");

        String status = jsonObject.getJSONObject("payload")
                .getJSONObject("payment")
                .getJSONObject("entity")
                .getString("status");

        OrderStatusUpdateRequestDto requestDto =new OrderStatusUpdateRequestDto();
        requestDto.setOrderId(orderId);
        if ("payment.captured".equals(event)) {
            requestDto.setStatus(OrderStatus.COMPLETED);
        } else if ("payment.failed".equals(event)) {
            requestDto.setStatus(OrderStatus.FAILED);
        }
        updateOrderStatus(requestDto);
    }
    private void updateOrderStatus(OrderStatusUpdateRequestDto requestDto) {
        orderClient.updateOrderStatus(requestDto);

    }
}
