package com.example.career.domain.user.Entity;

import com.example.career.domain.consult.Dto.ConsultMenteeRespDto;
import com.example.career.domain.consult.Dto.ConsultMentorRespDto;
import com.example.career.domain.user.Dto.MenteeRespDto;
import com.example.career.domain.user.Dto.MentorHomeRespDto;
import com.example.career.global.time.KoreaTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@DynamicUpdate
@Table(name = "User")
public class User
//        implements UserDetails
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(columnDefinition = "VARCHAR(30)", nullable = false)
    private String name;

    @Column(columnDefinition = "VARCHAR(30)", nullable = false)
    private String username;

    @Column(nullable = false)
    private String birth;
    @Column(columnDefinition = "VARCHAR(30)", unique = true, nullable = false)
    private String nickname;

    @Column(nullable = false)
    private Boolean activated = false;

    @Column(nullable = false)
    private Boolean isTutor;

    @Column(columnDefinition = "CHAR(13)")
    private String telephone;

    @Column(nullable = false)
    private int authType = 1;

    @Column(columnDefinition = "MEDIUMTEXT", nullable = false)
    private String password;

    @Column(columnDefinition = "CHAR(30)")
    private String email;

    @Column(columnDefinition = "CHAR(7)", nullable = false)
    private String role = "USER";

    @Column(nullable = false)
    private int status = 0;

    @Column(nullable = false)
    private Boolean gender = true;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String introduce;

    @Column(columnDefinition = "VARCHAR(20)")
    private String hobby;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String profileImg;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

//    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Long tutorDetail;

//    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Long studentDetail;

    @PrePersist // 데이터 생성이 이루어질때 사전 작업
    public void prePersist() {
        this.createdAt = KoreaTime.now();
        this.updatedAt = this.createdAt;
    }

    @ManyToMany
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name= "id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name="authority_name", referencedColumnName = "authority_name")})
    private Set<Authority> authorities;

    public MentorHomeRespDto toHomeDto() {
        return MentorHomeRespDto.builder().nickname(nickname)
                .id(id)
                .role(role)
                .status(status)
                .profileImg(profileImg)
                .build();

    }
    public MenteeRespDto toMenteeRespDto(){
        return MenteeRespDto.builder()
                .stuId(id)
                .nickname(nickname)
                .gender(gender)
                .birth(birth)
                .profileImg(profileImg)
                .stuUrl("https://test.com")
                .build();
    }
    public ConsultMenteeRespDto toConsultMenteeRespDto(){
        return ConsultMenteeRespDto.builder()
                .stuId(id)
                .nickname(nickname)
                .gender(gender)
                .birth(birth)
                .profileImg(profileImg)
                .stuUrl(null)
                .build();
    }
    public ConsultMentorRespDto toConsultMentorRespDto(){
        return ConsultMentorRespDto.builder()
                .mentorId(id)
                .nickname(nickname)
                .gender(gender)
                .birth(birth)
                .profileImg(profileImg)
                .mentorUrl(null)
                .build();
    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        Set<GrantedAuthority> roles = new HashSet<>();
//        for (String role : role.split(",")) {
//            roles.add(new SimpleGrantedAuthority(role));
//        }
//
//        System.out.println("이 유저의 역할: " + roles);
//        return roles;
//    }
//    @Override
//    public String getPassword() {
//        return password;
//    }
//
//    @Override
//    public String getUsername() {
//        return username;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        // 만료되었는지 확인하는 로직
//        return true; // true -> 만료되지 않았음
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        // 계정 잠금되었는지 확인하는 로직
//        return true; // true -> 잠금되지 않았음
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        // 패스워드가 만료되었는지 확인하는 로직
//        return true; // true -> 만료되지 않았음
//    }
//
//    @Override
//    public boolean isEnabled() {
//        // 계정이 사용 가능한지 확인하는 로직
//        return true; // true -> 사용 가능
//    }
}
