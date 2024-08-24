package dev.Abhishek.PaymentService.service;

import dev.Abhishek.PaymentService.client.OrderClient;
import dev.Abhishek.PaymentService.dto.orderDto.OrderStatusUpdateRequestDto;
import dev.Abhishek.PaymentService.entity.PaymentStatus;
import dev.Abhishek.PaymentService.entity.orderEntity.OrderStatus;
import dev.Abhishek.PaymentService.exception.ClientException.OrderServiceException;
import dev.Abhishek.PaymentService.exception.PaymentNotFoundException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RazorpayWebhookServiceImpl implements RazorpayWebhookService{
    private OrderClient orderClient;
    private PaymentService paymentService;

    public RazorpayWebhookServiceImpl(OrderClient orderClient, PaymentService paymentService) {
        this.orderClient = orderClient;
        this.paymentService = paymentService;
    }

    public void processWebhook(String payload)throws PaymentNotFoundException,OrderServiceException {
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

        String referenceId = jsonObject.getJSONObject("payload")
                .getJSONObject("payment")
                .getJSONObject("entity")
                .getJSONObject("notes").getString("reference_id");

        String status = jsonObject.getJSONObject("payload")
                .getJSONObject("payment")
                .getJSONObject("entity")
                .getString("status");

        OrderStatusUpdateRequestDto requestDto =new OrderStatusUpdateRequestDto();
        requestDto.setOrderId(referenceId);

        if ("order.paid".equals(event) || "payment_link.paid".equals(event)) {
            requestDto.setStatus(OrderStatus.COMPLETED);
            ((PaymentServiceImpl)paymentService).updatePaymentStatus(UUID.fromString(referenceId), PaymentStatus.SUCCESS);
            updateOrderStatus(requestDto);
        } else if ("payment.failed".equals(event) || "payment_link.expired".equals(event)) {
            requestDto.setStatus(OrderStatus.FAILED);
            ((PaymentServiceImpl)paymentService).updatePaymentStatus(UUID.fromString(referenceId), PaymentStatus.FAILED);
            updateOrderStatus(requestDto);
        }
        else{
            System.out.println("Ignoring event - "+event);
        }

    }
    private void updateOrderStatus(OrderStatusUpdateRequestDto requestDto)throws OrderServiceException {
        orderClient.updateOrderStatus(requestDto);

    }
}
