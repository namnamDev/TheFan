package com.blockChain.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter
@ToString
public class Member {

  // 회원번호 
  @Id
  @GeneratedValue
  @Column(name="MEMBER_NO")
  private Long memberNo;

  // 회원아이디
  @Column(name="MEMBER_ID",length=45)
  private String memberId;

  // 회원비밀번호
  @Column(name="MEMBER_PW",length=100)
  private String memberPw;

  // 회원등급()
  @ManyToOne
  @JoinColumn(name="MEMBER_GRADE")
  private Member_Grade memberGrade;

  // 팬클럽넘버(클래스타입)
//  @Column(name="MEMBER_FANNO",length=3)
//  @Enumerated(EnumType.STRING)
//  private Fanclub_Class memberFanNo;

  // 회원이메일
  @Column(name="MEMBER_EMAIL",length=45)
  private String memberEmail;

  // 회원별명
  @Column(name="MEMBER_NICK",length=45)
  private String memberNick;

  // 전화번호
  @Column(name="MEMBER_PHONE",length=11)
  private String memberPhone;
//  //지갑
//  @Column(name="MEMBER_WALLET",length=500)
//  private String memberWallet;
}
