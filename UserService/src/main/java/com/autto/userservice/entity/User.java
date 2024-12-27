package com.autto.userservice.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class User {
    @Id
    @Column(length = 36)
    private String id; // UUID 형식의 고유 사용자 ID

    @Column(name = "user_id", nullable = false, unique = true, length = 255)
    private String userId; // 사용자 아이디

    @Column(name = "user_pw", nullable = false, length = 255)
    private String userPw; // 사용자 비밀번호 (암호화 적용 필요)

    @Column(nullable = false, length = 255)
    private String name; // 사용자 이름

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Role role; // 역할 (기본값: 'member')

    @CreationTimestamp
    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate; // 계정 생성일

    @UpdateTimestamp
    @Column(name = "last_update")
    private LocalDateTime lastUpdate; // 마지막 업데이트

    @Column(name = "last_login_at", precision = 6)
    private LocalDateTime lastLoginAt; // 마지막 로그인 시간

    @Enumerated(EnumType.STRING)
    @Column(name = "user_status", nullable = false, length = 10)
    private UserStatus userStatus; // 가입 상태 (기본값: 'active')

    public enum Role {
        ADMIN, MEMBER
    }

    public enum UserStatus {
        ACTIVE, INACTIVE
    }

    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
    }
}
