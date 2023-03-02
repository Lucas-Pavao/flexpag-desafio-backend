package com.flexpag.paymentscheduler.Controllers;

import com.flexpag.paymentscheduler.Models.PaymentModel;
import com.flexpag.paymentscheduler.PaymentStatus;
import com.flexpag.paymentscheduler.Services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
public class PaymentController {

    @Autowired

    private PaymentService paymentService;

        @GetMapping(path = "/api/payment/")
        public List<PaymentModel> getAll(){
            List<PaymentModel> payments = paymentService.getAll();
            return ResponseEntity.status(HttpStatus.OK).body(payments).getBody();
        }
        @GetMapping(path = "/api/payment/{id}")
        public ResponseEntity getById(@PathVariable("id") Long id){
            boolean pagamentoExiste = paymentService.pagametoExisteById(id);
            if(pagamentoExiste) {
                PaymentModel payment = paymentService.getById(id);
                if (payment.getData().equals(Instant.now()) || payment.getData().isBefore(Instant.now())) {
                    paymentService.putPagamentoStatus(id, PaymentStatus.PAID);
                }
                return ResponseEntity.status(HttpStatus.OK).body(payment);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pagamento não encontrado!");
            }
        }
        @PostMapping(path = "/api/payment/salvar")
        public ResponseEntity<String> post(@RequestBody PaymentModel payment){
             if (payment.getData().isBefore(Instant.now())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("A data informada já passou!");
            } else {
                Long paymentId = paymentService.post(payment);
                return ResponseEntity.status(HttpStatus.OK).body(String.valueOf(paymentId));
            }
        }


        @DeleteMapping(path = "/api/payment/{id}")
        public ResponseEntity<String> deleteById(@PathVariable("id") Long id){
            boolean doesPaymentExists = paymentService.pagametoExisteById(id);
            if(doesPaymentExists) {
                PaymentModel payment = paymentService.getById(id);
                if (payment.getData().equals(Instant.now()) || payment.getData().isBefore(Instant.now())) {
                    paymentService.putPagamentoStatus(id, PaymentStatus.PAID);
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("O pagamento já foi realizado!");
                } else {
                    paymentService.deleteById(id);
                    return ResponseEntity.status(HttpStatus.OK).body("O agendamento foi cancelado!");
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pagamento não encontrado!");
            }
        }


        @PutMapping(path = "/api/payment/update/{id}")
        public ResponseEntity<String> autualizaDataHora(@RequestBody PaymentModel p, @PathVariable Long id ){
            boolean doesPaymentExists = paymentService.pagametoExisteById(id);
            if(doesPaymentExists) {
                PaymentModel payment = paymentService.getById(id);
                if (payment.getData().equals(Instant.now()) || payment.getData().isBefore(Instant.now())) {
                    paymentService.putPagamentoStatus(id, PaymentStatus.PAID);
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("O pagamento já foi realizado!");
                } else {
                    if (p.getData().isBefore(Instant.now())) {
                        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Data invalida: a data informada já passou!");
                    } else {
                        PaymentModel updatedPayment = paymentService.putDataHoraById(id, p);
                        return ResponseEntity.status(HttpStatus.OK).body(String.valueOf(updatedPayment));
                    }
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não existe pagamento agendado com esse id!");
            }
        }


}
