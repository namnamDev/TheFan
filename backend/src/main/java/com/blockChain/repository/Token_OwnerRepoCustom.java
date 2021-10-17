package com.blockChain.repository;

import java.util.List;
import java.util.Optional;

import com.blockChain.domain.Member;
import com.blockChain.domain.Token;
import com.blockChain.domain.Token_Owner;

public interface Token_OwnerRepoCustom {

	Optional<List<Member>> sltMultiOwner();

	Optional<List<Token_Owner>> sltMultiTokenByMember(Long memberNo);

	Optional<Token_Owner> sltByTokenMember(Long memberNo, Long tokenNo);

	Optional<Token_Owner> sltToken(Long tokenNo);

}
