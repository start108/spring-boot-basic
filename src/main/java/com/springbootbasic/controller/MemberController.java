package com.springbootbasic.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.springbootbasic.domain.Member;
import com.springbootbasic.service.MemberService;

@Controller
public class MemberController {

	private final MemberService memberService;

	/*
	 * 필드 주입(DI)
	 * */
//	@Autowired private MemberService memberService;
	
	/*
	 * setter 주입(DI)
	 * */
//	@Autowired
//	public void setMemberService(MemberService memberService) {
//		this.memberService = memberService;
//	}
	
	/*
	 * 생성자 주입(DI)
	 * 의존관계가 실행 중에 동적으로 변하는 경우는 거의 없으므로 생성자 주입을 권장
	 * */
	@Autowired
	public MemberController( MemberService memberService ) {
		this.memberService = memberService;
	}
	
	/*
	 * 회원 등록화면 조회
	 * */
	@GetMapping("/members/new")
	public String createForm() {
		return "members/createMemberForm";
	}
	
	/*
	 * 회원 등록
	 * */
	@PostMapping("/members/new")
	public String create( MemberForm form ) {
		
		Member member = new Member();
		
		member.setName( form.getName() );
		
		memberService.join( member );
		
		return "redirect:/";
	}
	
	/*
	 * 회원 목록 조회
	 * */
	@GetMapping("/members")
	public String list( Model model ) {
		
		List<Member> members = memberService.findMembers();
		
		model.addAttribute("members", members);
		
		return "members/memberList";
	}
}
