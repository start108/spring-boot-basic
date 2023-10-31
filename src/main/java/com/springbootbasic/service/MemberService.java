package com.springbootbasic.service;

import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.springbootbasic.domain.Member;
import com.springbootbasic.repository.MemberRepository;

@Transactional
public class MemberService {

	private final MemberRepository repository;
	
	public MemberService(MemberRepository repository) {
		this.repository = repository;
	}

	/*
	 * 회원가입
	 * */
	public Long join( Member member ) {
		
		// 같은 이름이 있는 중복 회원X
		validateDuplicateMember( member ); //
		
		repository.save( member );
		
		return member.getId();
	}

	/*
	 * 전체 회원 조회
	 * */
	public List<Member> findMembers() {
		return repository.findAll();
	}
	
	/*
	 * 회원 조회
	 * */
	public Optional<Member> findMemberOne( Long memberId ) {
		return repository.findById( memberId );
	}
	
	/*
	 * 중복회원 체크
	 * */
	private void validateDuplicateMember( Member member ) {
		repository.findByName( member.getName() )
				.ifPresent( m -> {
					throw new IllegalArgumentException( "이미 존재하는 회원입니다." );
				});
	}
}
