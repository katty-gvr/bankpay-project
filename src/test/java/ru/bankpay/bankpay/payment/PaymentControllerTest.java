package ru.bankpay.bankpay.payment;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.bankpay.bankpay.exception.NotFoundException;
import ru.bankpay.bankpay.exception.PaymentException;
import ru.bankpay.bankpay.payment.dto.PaymentCreateRequest;
import ru.bankpay.bankpay.payment.dto.PaymentCreateResponse;
import ru.bankpay.bankpay.payment.service.PaymentService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PaymentService paymentService;

    @Test
    @WithMockUser(username = "testuser", password = "testpassword")
    public void testMakePaymentSuccess() throws Exception {

        PaymentCreateRequest request = new PaymentCreateRequest(1L, 2L, 500.00);
        PaymentCreateResponse expectedResponse = new PaymentCreateResponse("Платеж создан");

        when(paymentService.makePayment(request)).thenReturn(expectedResponse);

        mockMvc.perform(post("/payments/pay")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));
    }

    @Test
    @WithMockUser(username = "testuser", password = "testpassword")
    public void testMakePaymentInsufficient() {

        PaymentCreateRequest request = new PaymentCreateRequest(1L, 2L, 1500.00);
        PaymentException paymentException = new PaymentException("Баланс аккаунта после перевода не может быть меньше 0");

        when(paymentService.makePayment(request)).thenThrow(paymentException);

        try {
            paymentService.makePayment(request);
        } catch (PaymentException e) {
            assertEquals("Баланс аккаунта после перевода не может быть меньше 0", e.getMessage());
        }
    }

    @Test
    @WithMockUser(username = "testuser", password = "testpassword")
    public void testMakePaymentAccountNotFound() throws Exception {

        PaymentCreateRequest request = new PaymentCreateRequest(1L, 3L, 500.00);
        NotFoundException exception = new NotFoundException("Аккаунт id=3 is не найден");

        when(paymentService.makePayment(request)).thenThrow(exception);

        mockMvc.perform(post("/payments/pay")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }
}
