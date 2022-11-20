package com.kakaopay.cafeservice.domain.user;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    public void 유저조회() {
        //given
        Long userId = 1L;

        //when
        User user = userRepository.findById(userId).orElse(null);

        //then
        assert user != null;
        assertEquals(1L, user.getId());
    }

    @Test
    public void 포인트_충전하기() {
        //given
        Long userId = 1L;
        int addPoint = 5000;

        //when
        User user = userRepository.findById(userId).orElse(null);
        assert user != null;

        user.setPoint(user.getPoint() + addPoint);

        //then
        assertEquals(15000, user.getPoint());
    }
}