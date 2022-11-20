package com.kakaopay.cafeservice.domain.menu;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class MenuRepositoryTest {

    @Autowired
    MenuRepository menuRepository;

    @Test
    public void 메뉴_조회_테스트() {
        //given
        int menuTotalCnt = 4;

        //when
        List<Menu> menuList = menuRepository.findAll();

        //then
        assertEquals(menuTotalCnt, menuList.size());
    }
}