package com.spring.demo.host.repository;

import com.spring.demo.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.Optional;

@Repository
public interface HostPaymentDateRepository extends JpaRepository<Payment, Data> {


}
