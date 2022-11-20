package com.kakaopay.cafeservice.domain.order;

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
public class OrderDataSendDto {

    private Long userId;

    private Long menuId;

    private int price;

    public static OrderDataSendDto of(Long userId, Long menuId, int price) {
        return OrderDataSendDto.builder().userId(userId).menuId(menuId).price(price).build();
    }
}
