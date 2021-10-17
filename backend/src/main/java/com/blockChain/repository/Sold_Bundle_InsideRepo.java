package com.blockChain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blockChain.domain.Sold_Bundle_Inside;

public interface Sold_Bundle_InsideRepo extends JpaRepository<Sold_Bundle_Inside,Long>,Sold_Bundle_InsideRepoCustom{

}
