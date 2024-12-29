package com.autto.userservice.entity;

import com.autto.userservice.util.UUIDUtils;
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
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @Column(name = "id", columnDefinition = "BINARY(16)", updatable = false, nullable = false)
    private byte[] uuid; // 고유 사용자 ID (UUID)

    @PrePersist
    public void prePersist() {
        if (uuid == null) {
            UUID generatedUuid = UUID.randomUUID();  // 새로운 UUID 생성
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

    public enum Role {
        ADMIN, USER
    }

    public enum UserStatus {
        ACTIVE, INACTIVE
    }

}
