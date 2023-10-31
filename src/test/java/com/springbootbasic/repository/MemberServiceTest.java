package com.springbootbasic.repository;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.springbootbasic.domain.Member;
import com.springbootbasic.service.MemberService;

class MemberServiceTest {

	MemberService service;
	MemoryMemberRepository repository;
	
	/*
	 *  테스트 실행 전 호출
	 */
	@BeforeEach
	public void beforeEach() {
		repository = new MemoryMemberRepository();
		service = new MemberService( repository );
	}
	
	/*
	 *  테스트 실행 종료 후 호출
	 */
	@AfterEach
	public void afterEach() {
		repository.clearStore();
	}
	
	@Test
	void 회원가입() {
	
		// given
		Member m = new Member();
		m.setName("spring");
		
		// when
		Long saveId = service.join( m );
		
		// then
		Member findMember = service.findMemberOne( saveId ).get();
		
		Assertions.assertEquals( m.getName(), findMember.getName() );
	}
	
	@Test
	public void 중복_회원_예외 () {
	
		// given
		Member m1 = new Member();
		m1.setName("spring");
		
		Member m2 = new Member();
		m2.setName("spring");
		
		// when
		service.join( m1 );
		
		IllegalArgumentException e = assertThrows( IllegalArgumentException.class, () -> service.join(m2) );
		
		// 방법 1.
		Assertions.assertEquals( e.getMessage(), "이미 존재하는 회원입니다." );
		
		// 방법 2.
//		try {
//			service.join( m2 );
//			fail();
//		} catch ( IllegalArgumentException e ) {
//			Assertions.assertEquals( e.getMessage(), "이미 존재하는 회원입니다." );
//		}
		
	}
	
	@Test
	void findMembers() {
		
	}
	
	@Test
	void findMemberOne () {
		 
	}
}
