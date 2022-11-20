package com.kakaopay.cafeservice.domain.order;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.kakaopay.cafeservice.domain.menu.Menu;
import com.kakaopay.cafeservice.domain.menu.MenuRepository;
import com.kakaopay.cafeservice.domain.menu.PopularMenusDto;
import com.kakaopay.cafeservice.domain.user.User;
import com.kakaopay.cafeservice.domain.user.UserRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class OrderDetailRepositoryTest {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MenuRepository menuRepository;

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Test
    public void 인기메뉴_탑3_조회() {
        //given
        커피주문(1L, 1L, 300);
        커피주문(2L, 2L, 5000);
        커피주문(3L, 3L, 9000);

        //when
        List<PopularMenusDto> popularMenusDtoList = orderDetailRepository.findTop3Menu();

        //then
        Long top1 = popularMenusDtoList.get(0).getMenuId();
        Long top2 = popularMenusDtoList.get(1).getMenuId();
        Long top3 = popularMenusDtoList.get(2).getMenuId();

        assertEquals(3L, top1);
        assertEquals(2L, top2);
        assertEquals(1L, top3);
    }

    public void 커피주문(Long userId, Long menuId, int orderCnt) {

        User user = userRepository.findById(userId).orElse(null);
        Menu menu = menuRepository.findById(menuId).orElse(null);
        assert menu != null;

        List<OrderDetail> orderDetailList = new ArrayList<>();

        OrderDetail coffee = OrderDetail.builder()
            .menu(menu)
            .orderPrice(menu.getPrice())
            .count(orderCnt)
            .build();
        orderDetailList.add(coffee);

        Order order = new Order();
        order.setUser(user);
        order.setOrderDetailList(orderDetailList);

        orderRepository.save(order);
    }
}