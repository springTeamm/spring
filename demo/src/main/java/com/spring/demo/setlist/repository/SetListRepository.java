package com.spring.demo.setlist.repository;

import com.spring.demo.setlist.entity.SetList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SetListRepository  extends JpaRepository<SetList, Long> {
}
