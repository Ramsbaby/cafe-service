package com.kakaopay.cafeservice.domain.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.kakaopay.cafeservice.exception.NotExistUserException;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Nested
    class 포인트 {

        @Test
        public void 포인트_충전시_없는_유저일경우() {
            //given
            Long userId = 23456L;
            int point = 50_000;
            String exceptionMessage = "유저가 존재하지 않습니다.";

            //stub
            when(userRepository.findById(userId)).thenThrow(new NotExistUserException(exceptionMessage));

            //when
            RuntimeException exception = assertThrows(NotExistUserException.class,
                () -> userService.getUserAfterChargePoint(userId, point));

            //then
            assertEquals(exception.getMessage(), exceptionMessage);
        }

        @Test
        public void 정상_충전() {
            //given
            Long userId = 1L;
            int addPoint = 50_000;
            User user = User.builder()
                .id(userId)
                .name("신세경")
                .point(5000)
                .build();
            assert user != null;

            //stub
            when(userRepository.findById(userId)).thenReturn(Optional.of(user));
            when(userRepository.save(user)).thenReturn(user);

            //when
            User result = userService.getUserAfterChargePoint(userId, addPoint);

            //then
            assertEquals(55_000, result.getPoint());
        }
    }

}