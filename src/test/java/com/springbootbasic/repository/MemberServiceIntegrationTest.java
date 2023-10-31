package com.springbootbasic.repository;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import com.springbootbasic.domain.Member;
import com.springbootbasic.service.MemberService;

@SpringBootTest
@Transactional
public class MemberServiceIntegrationTest {

	@Autowired MemberService service;
	
	@Test
	//@Commit
	void 회원가입() {
	
		// given
		Member m = new Member();
		m.setName("spring101");
		
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
		
		Assertions.assertEquals( e.getMessage(), "이미 존재하는 회원입니다." );
	}
}
