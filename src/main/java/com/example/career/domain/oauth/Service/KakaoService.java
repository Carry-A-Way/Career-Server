package com.example.career.domain.oauth.Service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import com.example.career.domain.oauth.Dto.SnsSignUpReqDto;
import com.example.career.domain.user.Dto.LoginDto;
import com.example.career.domain.user.Dto.SignUpReqDto;
import com.example.career.domain.user.Dto.TokenDto;
import com.example.career.domain.user.Entity.User;
import com.example.career.domain.user.Service.UserService;
import com.example.career.global.jwt.JwtFilter;
import com.example.career.global.jwt.TokenProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class KakaoService {
    @Value("${oauth.kakao.redirect}")
    private String redirect;

    @Value("${oauth.kakao.clientId}")
    private String restId;
    public String getReturnAccessToken(String code) {
        String access_token = "";
        String refresh_token = "";
        try {
            URL url = new URL("https://kauth.kakao.com/oauth/token");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //HttpURLConnection 설정 값 셋팅
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);


            // buffer 스트림 객체 값 셋팅 후 요청
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=");  //앱 KEY VALUE
            sb.append(restId);
            sb.append("&redirect_uri="); // 앱 CALLBACK 경로
            sb.append(redirect);
            sb.append("&code=" + code);
            sb.append("&code=" + code);
            bw.write(sb.toString());
            bw.flush();

//              RETURN 값 result 변수에 저장
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String br_line = "";
            String result = "";

            while ((br_line = br.readLine()) != null) {
                result += br_line;
            }

//            String result = IOUtils.toString(conn.getInputStream(), StandardCharsets.UTF_8);

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            // 토큰 값 저장 및 리턴
            access_token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_token = element.getAsJsonObject().get("refresh_token").getAsString();

            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return access_token;
    }

    public Map<String,Object> getUserInfo(String access_token) {
        Map<String,Object> resultMap =new HashMap<>();
        String reqURL = "https://kapi.kakao.com/v2/user/me";
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            //요청에 필요한 Header에 포함될 내용
            conn.setRequestProperty("Authorization", "Bearer " + access_token);

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String br_line = "";
            String result = "";

            while ((br_line = br.readLine()) != null) {
                result += br_line;
            }
            System.out.println("response:" + result);


            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);
            log.warn("element:: " + element);

            JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();
            String id = element.getAsJsonObject().get("id").getAsString();
            String email = kakao_account.getAsJsonObject().get("email").getAsString();
            JsonObject profile = kakao_account.getAsJsonObject("profile");
            String nickname = profile.get("nickname").getAsString();
            String gender = kakao_account.get("gender").getAsString();

//            JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
//            JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();
//            log.warn("id:: "+element.getAsJsonObject().get("id").getAsString());
//            String id = element.getAsJsonObject().get("id").getAsString();
//            String nickname = properties.getAsJsonObject().get("nickname").getAsString();
//            String email = kakao_account.getAsJsonObject().get("email").getAsString();
//            log.warn("email:: " + email);
//            resultMap.put("nickname", nickname);

            resultMap.put("id", id);
            resultMap.put("email", email);
            resultMap.put("name", nickname);
            resultMap.put("gender", gender);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return resultMap;
    }



}