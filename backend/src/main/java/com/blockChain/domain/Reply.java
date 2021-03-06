package com.blockChain.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Reply {
    // 댓글번호
	@Id
	@GeneratedValue
	@Column(name="REPLY_NO")
    private Long replyNo;

    // 판매번호 
	@ManyToOne
	@JoinColumn(name="SALES_NO")
    private Sales sales;

    // 회원번호
	@ManyToOne
	@JoinColumn(name="MEMBER_NO")
    private Member member;

    // 댓글내용 
	@Column(name="REPLY_CONTENT",length=3000)
    private String replyContent;

    // 댓글작성일자 (초기 날짜순 데이터 입력을 위해 임시로 이렇게 설정. 임시 데이터 입력후 변경할것.)
//	@Column(name = "REPLY_DATE", updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
//	@CreationTimestamp
	@Column(name = "REPLY_DATE")
    private LocalDateTime replyDate;
}
