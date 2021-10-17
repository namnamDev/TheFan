package com.blockChain.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//CELEB
@Entity
@Getter @Setter
@ToString
public class Celeb {

  // 샐럽번호
  @Id
  @JoinColumn(name="CELEB_NO")
  private Long celebNo;

  // 샐럽명 
  @Column(name="CELEB_NM",length=45)
  private String celebNm;
  
  // 성별 
  @Column(name="CELEB_MW",length=3)
  private String celebMw;

  // 그룹넘버 
  @ManyToOne
  @JoinColumn(name="GROUP_NO")
  private Celeb_Group group;

  // 대뷔시기 
  @Column(name="CELEB_DEBUT")
  private LocalDateTime celebDebut;

  // 은퇴시기 
  @Column(name="CELEB_RETIRE")
  private LocalDateTime celebRetire;
}