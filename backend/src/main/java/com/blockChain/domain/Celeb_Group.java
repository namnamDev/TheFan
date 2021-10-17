package com.blockChain.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Celeb_Group {
	
    // 그룹넘버 
	@Id
	@Column(name="GROUP_NO")
    private Long groupNo;

    // 그룹명(솔로 포함) 
	@Column(name="GROUP_NM")
    private String groupNm;

    // 그룹인원 
	@Column(name="GROUP_NOFP")
    private Long groupNofp;
}
