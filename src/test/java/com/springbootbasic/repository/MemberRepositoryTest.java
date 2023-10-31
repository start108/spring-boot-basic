package com.springbootbasic.repository;

import java.util.List;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.springbootbasic.domain.Member;

class MemberRepositoryTest {

	MemoryMemberRepository repository = new MemoryMemberRepository();
	
	// 테스트 순서는 보장되지 않으므로 메소드가 실행될 때 객체를 clear 되도록 afterEach() 메소드를 실행
	@AfterEach
	public void afterEach() {
		repository.clearStore();
	}
	
	@Test
	public void save() {
		
		Member mb = new Member();
		
		mb.setName("spring");
		
		repository.save(mb);
		
		Member result = repository.findById( mb.getId() ).get();
		
		Assertions.assertEquals( mb, result );
		//org.assertj.core.api.Assertions.assertThat( mb ).isEqualTo( result );
	}
	
	@Test
	public void findByName() {
		
		Member mb1 = new Member();
		mb1.setName( "spring1" );
		repository.save( mb1 );
		
		Member mb2 = new Member();
		mb2.setName( "spring2" );
		repository.save( mb2 );
		
		Member result = repository.findByName("spring1").get();

		Assertions.assertEquals( mb1.getName(), result.getName() );
	}
	
	@Test
	public void findAll() {
		
		Member mb1 = new Member();
		mb1.setName( "spring1" );
		repository.save( mb1 );
		
		Member mb2 = new Member();
		mb2.setName( "spring2" );
		repository.save( mb2 );
		
		List<Member> resultList = repository.findAll();
		
		Assertions.assertEquals( resultList.size(), 2 );
	}
}
