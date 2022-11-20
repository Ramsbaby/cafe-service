package com.kakaopay.cafeservice.domain.menu;

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
public class MenuDto {

    private Long id;
    private String name;
    private int price;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static MenuDto of(Menu menu) {
        return MenuDto.builder()
            .id(menu.getId())
            .name(menu.getName())
            .price(menu.getPrice())
            .createdAt(menu.getCreatedAt())
            .updatedAt(menu.getUpdatedAt())
            .build();
    }
}
