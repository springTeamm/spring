package com.spring.demo.host.repository;

import com.spring.demo.entity.PrShareDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HostPrShareDeviceRepository extends JpaRepository<PrShareDevice,Integer> {
    Optional<PrShareDevice> deleteByPrNum(Integer roomId);
}
