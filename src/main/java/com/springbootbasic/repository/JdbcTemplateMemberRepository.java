package com.springbootbasic.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import com.springbootbasic.domain.Member;

public class JdbcTemplateMemberRepository implements MemberRepository {

	private final JdbcTemplate jdbcTemplate;
	
//	@Autowired >> 생성자가 하나이면 생략 가능 ( Bean으로 생성해 줌 )
	public JdbcTemplateMemberRepository( DataSource dataSource ) {
		jdbcTemplate = new JdbcTemplate( dataSource );
	}
	
	@Override
	public Member save(Member member) {
		
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert( jdbcTemplate );
		jdbcInsert.withTableName( "member" ).usingGeneratedKeyColumns( "id" );
		
		Map<String, Object> map = new HashMap<>();
		map.put("name", member.getName() );
		
		Number key = jdbcInsert.executeAndReturnKey( new MapSqlParameterSource(map) );
		member.setId( key.longValue() );
		
		return member;
	}

	@Override
	public Optional<Member> findById(Long id) {
		
		List<Member> result = jdbcTemplate.query("SELECT * FROM member WHERE id = ?", memberRowMapper(), id );
		
		return result.stream().findAny();
	}

	@Override
	public Optional<Member> findByName(String name) {

		List<Member> result = jdbcTemplate.query("SELECT * FROM member WHERE name = ?", memberRowMapper(), name );
		
		return result.stream().findAny();
	}

	@Override
	public List<Member> findAll() {
		 return jdbcTemplate.query("SELECT * FROM member", memberRowMapper() );
	}

	private RowMapper<Member> memberRowMapper() {
		return (rs, rowNum) -> {
			
			Member member = new Member();
			member.setId( rs.getLong("id") );
			member.setName( rs.getString("name") );
			
			return member;
		};
		
//		return new RowMapper<Member>() {
//			@Override
//			public Member mapRow( ResultSet rs, int rowNum ) throws SQLException {
//				
//				Member member = new Member();
//				member.setId( rs.getLong("id") );
//				member.setName( rs.getString("name") );
//				return member;
//			}
//		};
	}
}
