package com.flexpag.paymentscheduler.Repositorys;

import com.flexpag.paymentscheduler.Models.PaymentModel;
import org.springframework.data.repository.CrudRepository;

public interface PaymentRepository extends CrudRepository<PaymentModel, Long> {

}
