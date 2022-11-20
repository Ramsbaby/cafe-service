package com.kakaopay.cafeservice.domain.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.kakaopay.cafeservice.domain.menu.Menu;
import com.kakaopay.cafeservice.domain.menu.MenuRepository;
import com.kakaopay.cafeservice.domain.user.User;
import com.kakaopay.cafeservice.domain.user.UserRepository;
import com.kakaopay.cafeservice.exception.NotEnoughPointException;
import com.kakaopay.cafeservice.exception.NotExistMenuException;
import com.kakaopay.cafeservice.exception.NotExistUserException;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MenuRepository menuRepository;

    @Nested
    class 주문하기 {

        @Test
        public void 주문시_잘못된_유저정보() {
            //given
            Long userId = 23453323L;
            Long menuId = 1L;
            //stub
            String exceptionMessage = "유저가 존재하지 않습니다.";
            when(userRepository.findById(userId)).thenThrow(new NotExistUserException(exceptionMessage));

            //when
            RuntimeException exception = assertThrows(NotExistUserException.class,
                () -> orderService.saveOrder(userId, menuId));

            //then
            assertEquals(exception.getMessage(), exceptionMessage);
        }

        @Test
        public void 주문시_잘못된_메뉴정보() {
            //given
            Long userId = 1L;
            Long menuId = 12634223L;
            User user = User.builder().name("jordan22222").point(5000).build();

            //stub
            String exceptionMessage = "메뉴가 존재하지 않습니다.";
            when(userRepository.findById(userId)).thenReturn(Optional.ofNullable(user));
            when(menuRepository.findById(menuId)).thenThrow(new NotExistMenuException(exceptionMessage));

            //when
            RuntimeException exception = assertThrows(NotExistMenuException.class,
                () -> orderService.saveOrder(userId, menuId));

            //then
            assertEquals(exception.getMessage(), exceptionMessage);
        }

        @Test
        public void 주문시_부족한_포인트() {
            //given
            Long userId = 1L;
            Long menuId = 1L;
            int userPoint = 2000;
            int coffeePrice = 5000;
            User user = User.builder().name("jordan").point(userPoint).build();
            Menu menu = Menu.builder().name("ice americano").price(coffeePrice).build();
            String exceptionMessage = "포인트가 충분하지 않습니다.";
            //stub
            when(userRepository.findById(userId)).thenReturn(Optional.ofNullable(user));
            when(menuRepository.findById(menuId)).thenReturn(Optional.ofNullable(menu));

            //when
            RuntimeException exception = assertThrows(NotEnoughPointException.class,
                () -> orderService.saveOrder(userId, menuId));

            //then
            assertEquals(exception.getMessage(), exceptionMessage);
        }

        @Test
        public void 정상주문() {
            //given
            Long userId = 1L;
            Long menuId = 1L;
            int userPoint = 5000;
            int coffeePrice = 5000;
            User user = User.builder().name("jordan").point(userPoint).build();
            Menu menu = Menu.builder().name("ice americano").price(coffeePrice).build();

            //stub
            when(userRepository.findById(userId)).thenReturn(Optional.ofNullable(user));
            when(menuRepository.findById(menuId)).thenReturn(Optional.ofNullable(menu));

            //when
            var orderResponse = orderService.saveOrder(userId, menuId);

            //then
            assertEquals(userId, orderResponse.getUserId());
            assertEquals(menuId, orderResponse.getMenuId());
            assertEquals(5000, orderResponse.getPrice());
        }
    }
}