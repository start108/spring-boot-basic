package com.springbootbasic.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springbootbasic.domain.Member;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {

	// JPQL select m from Member m where m.name = ? 
	@Override
	Optional<Member> findByName(String name);
}
