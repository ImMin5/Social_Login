package com.team.bf.dao;

import org.apache.ibatis.annotations.Mapper;

import com.team.bf.vo.MemberVO;

@Mapper
public interface MemberDAO {
	public MemberVO memberSelectByEmail(String email);
	public int memberInsert(MemberVO vo);
}
