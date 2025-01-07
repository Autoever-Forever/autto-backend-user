package com.autto.userservice.entity;

import com.autto.userservice.util.UUIDUtils;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.codec.Hex;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
// UserDetails 는 Spring Security 프레임워크에서 제공하는 인터페이스
public class User implements UserDetails {
    @Id
    @Column(name = "id", columnDefinition = "BINARY(16)", updatable = false, nullable = false)
    private byte[] uuid; // 고유 사용자 ID (UUID)

    @PrePersist
    public void prePersist() {
        if (uuid == null) {
            UUID generatedUuid = UUID.randomUUID(); // 새로운 UUID 생성
            System.out.println(generatedUuid);
            uuid = UUIDUtils.asBytes(generatedUuid); // UUID를 byte[]로 변환하여 uuid 필드에 저장
        }
    }
    //UUID 확인용
    public String getUuidHex() {
        return UUIDUtils.byteArrayToHex(uuid);  // byte[]를 Hex 문자열로 변환
    }

    //user_id -> email 변경
    @Column(name = "email", nullable = false, unique = true, length = 320)
    private String email; // 사용자 이메일

    @Column(name = "user_pw", nullable = false, length = 255)
    private String userPw; // 사용자 비밀번호 (암호화 적용 필요)

    @Column(nullable = false, length = 100)
    private String name; // 사용자 이름

    @Getter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Role role; // 역할 (기본값: 'user')

    @CreationTimestamp
    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate; // 계정 생성일

    @UpdateTimestamp
    @Column(name = "last_update")
    private LocalDateTime lastUpdate; // 마지막 업데이트

    @Enumerated(EnumType.STRING)
    @Column(name = "user_status", nullable = false, length = 10)
    private UserStatus userStatus; // 가입 상태 (기본값: 'active')

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // ROLE_USER, ROLE_ADMIN 을 권한으로 반환하도록 수정
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getPassword() {
        return userPw;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired(); // true 반환 : 계정 만료 제약 없음
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked(); // true 반환 : 계정 잠금 제약 없음
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired(); // true 반환 : 자격 증명 만료 제한 없음
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled(); // true 반환 : 모든 계정 활성화
    }

    public enum Role {
        ADMIN, USER
    }

    public enum UserStatus {
        ACTIVE, INACTIVE
    }

}
