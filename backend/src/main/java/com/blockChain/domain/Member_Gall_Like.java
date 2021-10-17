package com.blockChain.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@IdClass(Member_Gall_LikePK.class)
public class Member_Gall_Like {
	
	@Id
	@ManyToOne
    @JoinColumn(name = "FROM_MEMBER")
    private Member fromMember;
    
	@Id
	@ManyToOne
    @JoinColumn(name = "TO_MEMBER")
    private Member toMember;

}
