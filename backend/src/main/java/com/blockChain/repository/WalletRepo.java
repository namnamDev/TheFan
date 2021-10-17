package com.blockChain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blockChain.domain.Wallets;

public interface WalletRepo extends JpaRepository<Wallets ,Long>, WalletRepoCustom {

}
