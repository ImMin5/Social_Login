package com.team.bf.service;
import com.team.bf.vo.MemberVO;


public interface MemberService {
	public MemberVO memberSelectByEmail(String email);
	public int memberInsert(MemberVO vo);
}
