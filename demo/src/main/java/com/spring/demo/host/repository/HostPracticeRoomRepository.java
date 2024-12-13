package com.spring.demo.host.repository;

import com.spring.demo.entity.PracticeRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HostPracticeRoomRepository extends JpaRepository<PracticeRoom, Integer> {

    Optional<PracticeRoom> findByPrNum(Integer prNum);

    @Query("SELECT pr FROM PracticeRoom pr WHERE pr.hostInfoNum = :hostInfoNum")
    List<PracticeRoom> findByHostInfoNum(@Param("hostInfoNum") Integer hostInfoNum);


}

