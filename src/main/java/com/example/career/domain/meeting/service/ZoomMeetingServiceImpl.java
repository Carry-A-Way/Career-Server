package com.example.career.domain.meeting.service;

import com.example.career.domain.meeting.EncodeUtil;
import com.example.career.domain.meeting.dto.ZoomMeetingObjectDTO;
import com.example.career.domain.meeting.dto.ZoomMeetingSettingsDTO;
import com.example.career.domain.meeting.dto.ZoomMeetingsListResponseDTO;
import com.example.career.domain.meeting.entity.ZoomMeetingObjectEntity;
import com.example.career.domain.meeting.entity.ZoomToken;
import com.example.career.domain.meeting.repository.ZoomMeetingRepository;
import com.example.career.domain.meeting.repository.ZoomTokenRepository;
import com.example.career.global.time.KoreaTime;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;



@Service
@RequiredArgsConstructor
public class ZoomMeetingServiceImpl implements ZoomMeetingService{
    private final ZoomTokenRepository zoomTokenRepository;

    private final ZoomMeetingRepository zoomMeetingRepository;

    @Value("${zoom.secret-key}")
    private String secretKey;
    public ZoomMeetingObjectEntity createMeeting(ZoomMeetingObjectDTO zoomMeetingObjectDTO) throws IOException {
        // refresh 검
        isExpired();

        System.out.println("Request to create a Zoom meeting");
        // replace zoomUserId with your user ID
        String apiUrl = "https://api.zoom.us/v2/users/" + "jongminshin373@gmail.com" + "/meetings";

        // replace with your password or method
        // replace email with your email
        zoomMeetingObjectDTO.setHost_email("jongminshin373@gmail.com");

        // Optional Settings for host and participant related options
        ZoomMeetingSettingsDTO settingsDTO = new ZoomMeetingSettingsDTO();
        settingsDTO.setJoin_before_host(false);
        settingsDTO.setParticipant_video(true);
        settingsDTO.setHost_video(false);
        settingsDTO.setAuto_recording("cloud");
        settingsDTO.setMute_upon_entry(true);
        zoomMeetingObjectDTO.setSettings(settingsDTO);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + zoomTokenRepository.findById(0L).get().getAccessToken());
        headers.add("content-type", "application/json");
        HttpEntity<ZoomMeetingObjectDTO> httpEntity = new HttpEntity<ZoomMeetingObjectDTO>(zoomMeetingObjectDTO, headers);
        ResponseEntity<ZoomMeetingObjectDTO> zEntity = restTemplate.exchange(apiUrl, HttpMethod.POST, httpEntity, ZoomMeetingObjectDTO.class);
        if(zEntity.getStatusCodeValue() == 201) {
            System.out.println("Zooom meeeting response {}" + zEntity);
            return zoomMeetingRepository.save(zEntity.getBody().toEntity());
        } else {
            System.out.println("Error while creating zoom meeting {}" + zEntity.getStatusCode());
        }
        zoomMeetingObjectDTO.setSettings(null);
        return null;
    }

    // 재발급 확인
    public void isExpired() throws IOException {

        // 현재 시간 가져오기
        LocalDateTime now = KoreaTime.now();

        // updatedAt과 현재 시간의 차이 계산 (단위: 분)
        long minutesSinceUpdate = ChronoUnit.MINUTES.between(zoomTokenRepository.findById(0L).get().getUpdatedAt(), now);

        // 만료 시간을 60분으로 설정
        long expirationTimeInMinutes = 60;

        // 현재 시간과 updatedAt의 차이가 expirationTimeInMinutes 이상인 경우 만료되었다고 판단
        // updatedAt 시간이 현재 시간으로부터 60분 이상 경과했다면 true를 반환하며, 그렇지 않으면 false를 반환합니다.
        boolean checkExpired = minutesSinceUpdate >= expirationTimeInMinutes;
        // 토큰 재발급
        if(checkExpired) {
            refreshToken();
        }
    }



    // 토큰 재생성
    @Override
    @Transactional
    public String refreshToken() throws IOException {
        String zoomUrl = "https://zoom.us/oauth/token";
        //통신을 위한 okhttp 사용 maven 추가 필요
        OkHttpClient client = new OkHttpClient();
        ObjectMapper mapper = new ObjectMapper();

        FormBody formBody = new FormBody.Builder()
                .add("grant_type", "refresh_token") // 문서에 명시 된 grant_type
                .add("refresh_token", zoomTokenRepository.findById(0L).get().getRefreshToken()) //
                .build();
        Request zoomRequest = new Request.Builder()
                .url(zoomUrl) // 호출 url
                .addHeader("Content-Type", "application/x-www-form-urlencoded") // 공식 문서에 명시 된 type
                .addHeader("Authorization","Basic " + secretKey) // Client_ID:Client_Secret 을  Base64-encoded 한 값
                .post(formBody)
                .build();

        Response zoomResponse = client.newCall(zoomRequest).execute();
        String zoomText = zoomResponse.body().string();


        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

        Map<String, String> list = mapper.readValue(zoomText, new TypeReference<>() {});
        String accessToken = list.get("access_token");
        String refreshToken = list.get("refresh_token");


        // DB 수정
        ZoomToken zoomToken = zoomTokenRepository.findById(0L).get();
        System.out.println(zoomToken);
        zoomToken.setAccessToken(accessToken);
        zoomToken.setRefreshToken(refreshToken);
        zoomToken.setUpdatedAt(KoreaTime.now());

        System.out.println(zoomToken);
        return accessToken;
    }

}
