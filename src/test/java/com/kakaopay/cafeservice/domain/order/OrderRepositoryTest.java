package com.kakaopay.cafeservice.domain.order;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.kakaopay.cafeservice.domain.menu.Menu;
import com.kakaopay.cafeservice.domain.menu.MenuRepository;
import com.kakaopay.cafeservice.domain.user.User;
import com.kakaopay.cafeservice.domain.user.UserRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class OrderRepositoryTest {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MenuRepository menuRepository;

    @Test
    public void 주문내역_저장_테스트() {
        //given
        User user = userRepository.findById(1L).orElse(null);
        Menu menu = menuRepository.findById(1L).orElse(null);
        assert user != null;
        assert menu != null;

        List<OrderDetail> orderDetailList = new ArrayList<>();

        OrderDetail coffee1 = OrderDetail.builder()
            .menu(menu)
            .orderPrice(menu.getPrice())
            .count(1)
            .build();
        orderDetailList.add(coffee1);

        Order order = new Order();
        order.setUser(user);
        order.setOrderDetailList(orderDetailList);

        //when
        orderRepository.save(order);

        //then
        List<Order> orderList = orderRepository.findAll();
        assertEquals(user.getName(), orderList.get(0).getUser().getName());
    }
}