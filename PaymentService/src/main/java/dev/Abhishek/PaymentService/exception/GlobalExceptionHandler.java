package dev.Abhishek.PaymentService.exception;

import dev.Abhishek.PaymentService.dto.ExceptionResponseDto;
import dev.Abhishek.PaymentService.exception.ClientException.OrderServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice()
public class GlobalExceptionHandler {
    @ExceptionHandler({OrderServiceException.class})
    public ResponseEntity handlePaymentServiceException(OrderServiceException pe) {
        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto(
                pe.getMessage(),
                503
        );
        return new ResponseEntity<>(exceptionResponseDto, HttpStatus.SERVICE_UNAVAILABLE);
    }
    @ExceptionHandler({WebhookProcessingException.class})
    public ResponseEntity handleWebhookProcessingException(WebhookProcessingException pe) {
        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto(
                pe.getMessage(),
                500
        );
        return new ResponseEntity<>(exceptionResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler({PaymentNotFoundException.class})
    public ResponseEntity handlePaymentNotFoundExceptionException(PaymentNotFoundException pe) {
        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto(
                pe.getMessage(),
                404
        );
        return new ResponseEntity<>(exceptionResponseDto, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler({PaymentLinkGenerationException.class})
    public ResponseEntity handlePaymentLinkGenerationException(PaymentLinkGenerationException pe) {
        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto(
                pe.getMessage(),
                500
        );
        return new ResponseEntity<>(exceptionResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}