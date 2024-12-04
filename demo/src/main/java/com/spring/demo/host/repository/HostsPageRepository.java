package com.spring.demo.host.repository;

import com.spring.demo.entity.Hosts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HostsPageRepository extends JpaRepository<Hosts,Integer> {
}
