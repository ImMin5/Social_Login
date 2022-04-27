package com.team.bf.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.team.bf.vo.MemberVO;

@Service
public class LoginService {
	public JSONObject getUserInfo(String token) {
		StringBuilder urlBuilder = new StringBuilder("https://kapi.kakao.com/v2/user/me");
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		JSONObject json = null;
		MemberVO mvo = new MemberVO();
		System.out.println(token);
		try {
			// header 설정
			URL url = new URL(urlBuilder.toString());
			System.out.println("url --> : " + url.toString());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			//conn.setRequestMethod("GET");
			//header 설정
			conn.setRequestProperty("Authorization", "Bearer " + token);
			
			  int responseCode = conn.getResponseCode();
              System.out.println("responseCode : " + responseCode);
              
			String line;
            if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
           	 br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
            } else {
           	 br = new BufferedReader(new InputStreamReader(conn.getErrorStream(),"UTF-8"));
            }
            while ((line = br.readLine()) != null) {
                sb.append(line);
                //System.out.println("line -- >"+ line);
            }
            json = new JSONObject(sb.toString());
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return json;
		
	}

}
