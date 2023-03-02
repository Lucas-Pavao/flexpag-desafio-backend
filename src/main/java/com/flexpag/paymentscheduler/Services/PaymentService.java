package com.flexpag.paymentscheduler.Services;

import com.flexpag.paymentscheduler.Models.PaymentModel;
import com.flexpag.paymentscheduler.Repositorys.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import com.flexpag.paymentscheduler.PaymentStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository repository;


    public List<PaymentModel> getAll(){
        return (List<PaymentModel>) repository.findAll();
    }

    public PaymentModel getById(@PathVariable("id") Long id){
        Optional<PaymentModel> foundPayment = repository.findById(id);
        return foundPayment.get();
    }
    @PostMapping(path = "/api/payment/salvar")
    public long post(@RequestBody PaymentModel payment){
        payment.setStatus(PaymentStatus.PENDING);
        PaymentModel savedPayment = repository.save(payment);
        return savedPayment.getId();
    }



    public void deleteById(@PathVariable("id") Long id){
        repository.deleteById(id);
    }


    public PaymentModel put(@RequestBody PaymentModel payment){
        return repository.save(payment);
    }

    public boolean pagametoExisteById(Long id) {
        return repository.existsById(id);
    }


    public void putPagamentoStatus(Long id, PaymentStatus status) {
        PaymentModel foundPayment = getById(id);
        foundPayment.setStatus(status);
    }

    public PaymentModel putDataHoraById(Long id, PaymentModel p) {
        PaymentModel payment = getById(id);
        payment.setData(p.getData());
        return payment;
    }


}
