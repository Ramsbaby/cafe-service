package com.kakaopay.cafeservice.domain.order;

import com.kakaopay.cafeservice.aop.SetDataSource;
import com.kakaopay.cafeservice.aop.SetDataSource.DataSourceType;
import com.kakaopay.cafeservice.component.CollectDataSender;
import com.kakaopay.cafeservice.domain.menu.Menu;
import com.kakaopay.cafeservice.domain.menu.MenuRepository;
import com.kakaopay.cafeservice.domain.user.User;
import com.kakaopay.cafeservice.domain.user.UserRepository;
import com.kakaopay.cafeservice.exception.NotExistMenuException;
import com.kakaopay.cafeservice.exception.NotExistUserException;
import java.time.LocalDateTime;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final MenuRepository menuRepository;

    @Transactional
    @SetDataSource(dataSourceType = DataSourceType.WRITER)
    public OrderResponse saveOrder(Long userId, Long menuId) {

        User user = userRepository.findById(userId).orElseThrow(
            () -> new NotExistUserException("유저가 존재하지 않습니다."));

        Menu menu = menuRepository.findById(menuId).orElseThrow(
            () -> new NotExistMenuException("메뉴가 존재하지 않습니다."));

        // 주문 생성
        Order order = Order.create(user, OrderDetail.create(menu));
        orderRepository.save(order);

        return OrderResponse.builder().orderId(order.getId())
            .userId(userId)
            .menuId(menuId)
            .price(order.getTotalPrice())
            .createdAt(order.getCreatedAt())
            .updatedAt(order.getUpdatedAt())
            .msg("주문이 완료되었습니다. 남은 포인트 : " + (user.getPoint()))
            .build();
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    static class OrderResponse {

        private Long orderId;
        private Long userId;
        private Long menuId;
        private int price;
        private String msg;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }

}
