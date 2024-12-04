package com.spring.demo.host.repository;

import com.spring.demo.entity.PrJpg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HostPrJpgRepository extends JpaRepository<PrJpg,Integer> {
    Optional<PrJpg> deleteByPrNum(Integer roomId);
}
