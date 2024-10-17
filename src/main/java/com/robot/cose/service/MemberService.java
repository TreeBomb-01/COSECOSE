package com.robot.cose.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.robot.cose.Entity.MemberEntity;
import com.robot.cose.repository.MemberRepository;

import java.util.List;


@Service
public class MemberService {
    
  @Autowired
  private MemberRepository memberRepository;

  public List<MemberEntity> getAllMember(){
    return memberRepository.findAll();
  }
}
