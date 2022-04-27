package com.team.bf.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.team.bf.dao.MemberDAO;
import com.team.bf.vo.MemberVO;

@Service
public class MemberServiceImpl implements MemberService {
	@Inject
	MemberDAO dao;
	
	public MemberVO memberSelectByEmail(String email) {
		return dao.memberSelectByEmail(email);
	}
	public int memberInsert(MemberVO vo) {
		return dao.memberInsert(vo);
	}
}
