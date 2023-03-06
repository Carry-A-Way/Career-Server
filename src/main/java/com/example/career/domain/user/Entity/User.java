package com.example.career.domain.user.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
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

    @Column(columnDefinition = "VARCHAR(30)", unique = true, nullable = false)
    private String nickname;

    @Column(columnDefinition = "CHAR(13)")
    private String telephone;

    @Column(nullable = false)
    private int authType = 1;

    @Column(columnDefinition = "MEDIUMTEXT", nullable = false)
    private String password;

    @Column(columnDefinition = "CHAR(7)", nullable = false)
    private String role = "USER";

    @Column(nullable = false)
    private int status = 0;

    @Column(nullable = false)
    private Boolean gender = true;

    @Lob
    private String profileImg;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String introduce;

    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private TutorDetail tutorDetail;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private StudentDetail studentDetail;

    @PrePersist // 데이터 생성이 이루어질때 사전 작업
    public void prePersist() {
        this.createAt = LocalDateTime.now();
        this.updateAt = this.createAt;
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
