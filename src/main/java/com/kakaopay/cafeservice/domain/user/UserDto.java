package com.kakaopay.cafeservice.domain.user;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode()
public class UserDto {

    private Long id;
    private String name;
    private int point;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static UserDto of(User user) {
        return UserDto.builder()
            .id(user.getId())
            .name(user.getName())
            .point(user.getPoint())
            .createdAt(user.getCreatedAt())
            .updatedAt(user.getUpdatedAt())
            .build();
    }
}
