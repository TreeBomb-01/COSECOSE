package com.robot.cose.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.robot.cose.Entity.MemberEntity;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity,String>{
  
}
