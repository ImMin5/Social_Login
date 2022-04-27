package com.team.bf.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.inject.Inject;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.team.bf.service.LoginService;
import com.team.bf.service.MemberService;
import com.team.bf.vo.MemberVO;

@RestController
public class LoginController {
	private String REST_API_KEY ="f20eb18d7d37d79e45a5dff8cb9e3b9e";
	
	@Inject
	LoginService loginService;
	@Inject
	MemberService memberService;
	
	@GetMapping("/login/oauth")
	public ModelAndView loginOauth(@RequestParam(value = "code", required = false) String code) {
		ModelAndView mav = new ModelAndView();
		System.out.println("code --->"+code);
		getKakaoToken(code);
		mav.setViewName("redirect:/");
		return mav;
	}
	@PostMapping("/login/kakao")
	public void getUserInfo(@RequestParam(value = "token", required = false)String token) {
		JSONObject jsonObj = loginService.getUserInfo(token);
		MemberVO mvo = null;
		
		try {
			if(jsonObj == null) {
				System.out.println("잘못된 접근입니다.");
			}
			System.out.println(jsonObj.toString());
			
			if(!jsonObj.getJSONObject("kakao_account").getBoolean("has_email")) {
				System.out.println("이메일을 입력 받아야 합니다.");
			}
			mvo = memberService.memberSelectByEmail(jsonObj.getJSONObject("kakao_account").getString("email"));
			if(mvo == null) {
				mvo = new MemberVO();
				mvo.setKakao_id(jsonObj.getInt("id"));
				mvo.setNickname(jsonObj.getJSONObject("kakao_account").getJSONObject("profile").getString("nickname"));
				mvo.setEmail(jsonObj.getJSONObject("kakao_account").getString("email"));
				
				memberService.memberInsert(mvo);
				System.out.println("회원가입");
			}
			else {
				System.out.println("로그인");
			}
			
	
	            
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void getKakaoToken(String code) {
		StringBuilder urlBuilder = new StringBuilder("https://kauth.kakao.com/oauth/token");
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		JSONObject json = null;
		try {
			urlBuilder.append("?" + URLEncoder.encode("grant_type","UTF-8") + "="+"authorization_code"); /*REST_API_KEY*/
			urlBuilder.append("&" + URLEncoder.encode("client_id","UTF-8") + "="+REST_API_KEY); /*REST_API_KEY*/
			urlBuilder.append("&" + URLEncoder.encode("redirect_url","UTF-8") + "="+"http://localhost:8080/login/oauth"); /*REST_API_KEY*/
			urlBuilder.append("&" + URLEncoder.encode("code","UTF-8") + "="+code); /*REST_API_KEY*/
			
			URL url = new URL(urlBuilder.toString());
			System.out.println("url ---- > " + url.toString());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			String line;
            if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
           	 br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
            } else {
           	 br = new BufferedReader(new InputStreamReader(conn.getErrorStream(),"UTF-8"));
            }
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            System.out.println("sb token ---> :" + sb.toString());
            json = new JSONObject(sb.toString());
            String access_token = json.getString("access_token");
            System.out.println("token---> " + access_token);
            getUserInfo(access_token);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
//	@PostMapping("/login")
	public String login() {
		StringBuilder urlBuilder = new StringBuilder("https://kauth.kakao.com/oauth/authorize");
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		try {
			urlBuilder.append("?" + URLEncoder.encode("client_id","UTF-8") + "="+REST_API_KEY); /*REST_API_KEY*/
			urlBuilder.append("&" + URLEncoder.encode("redirect_uri","UTF-8") + "=" + "http://localhost:8080/login/oauth"); /*현재 페이지 번호*/
			urlBuilder.append("&" + URLEncoder.encode("response_type","UTF-8") + "=" + URLEncoder.encode("code", "UTF-8")); /*현재 페이지 번호*/
			
			URL url = new URL(urlBuilder.toString());
			System.out.println("url ---- > " + url.toString());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            
            String line;
            if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
           	 br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
            } else {
           	 br = new BufferedReader(new InputStreamReader(conn.getErrorStream(),"UTF-8"));
            }
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            
            //System.out.println("sb --- > " +sb.toString());
            
            
		}catch(Exception e) {
			e.printStackTrace();
			
		}
		
		return sb.toString();
	}

}
