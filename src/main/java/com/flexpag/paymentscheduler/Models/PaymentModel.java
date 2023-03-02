package com.flexpag.paymentscheduler.Models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import com.flexpag.paymentscheduler.PaymentStatus;


import javax.persistence.*;
import java.time.Instant;
                //Determina que essa classe modela um objeto

@Entity(name = "payment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false,length = 50)
    private String nome;
    @Column(nullable = false,length = 50)
    private Double valorPagar;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "GMT-3")
    private Instant data;
    @NonNull
    private PaymentStatus status;

    public PaymentModel(Long id, String nome, Double valorPagar, Instant data) {
        this.id = id;
        this.nome = nome;
        this.valorPagar = valorPagar;
        this.data = data;
        this.status = PaymentStatus.PENDING;
    }


}
