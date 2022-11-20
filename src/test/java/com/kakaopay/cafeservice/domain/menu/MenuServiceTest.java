package com.kakaopay.cafeservice.domain.menu;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.kakaopay.cafeservice.domain.order.OrderDetailRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MenuServiceTest {

    @InjectMocks
    private MenuService menuService;

    @Mock
    private MenuRepository menuRepository;

    @Mock
    private OrderDetailRepository orderDetailRepository;

    @Test
    public void 메뉴_조회_테스트() {
        //given
        String menuName = "ice americano";
        int menuPrice = 3000;

        List<Menu> menuList = new ArrayList<>();
        Menu menu = Menu.builder()
            .name(menuName)
            .price(menuPrice)
            .build();
        menuList.add(menu);

        //stub
        when(menuRepository.findAll()).thenReturn(menuList);

        //when
        List<MenuDto> menuDtoList = menuService.findAllMenu();

        //then
        assertEquals(menuName, menuDtoList.get(0).getName());
        assertEquals(menuPrice, menuDtoList.get(0).getPrice());
    }

    @Test
    public void 인기메뉴_3개조회_테스트() {
        //given
        List<PopularMenusDto> popularMenus = new ArrayList<>();
        PopularMenusDto menusDto = new PopularMenu(1L, 5000);
        PopularMenusDto menusDto2 = new PopularMenu(2L, 500);
        PopularMenusDto menusDto3 = new PopularMenu(3L, 50);
        popularMenus.add(menusDto);
        popularMenus.add(menusDto2);
        popularMenus.add(menusDto3);

        //stub
        when(orderDetailRepository.findTop3Menu()).thenReturn(popularMenus);

        //when
        List<MenuService.Top3MenuResponse> responses = menuService.findTop3Menu();

        //then
        assertEquals(1L, responses.get(0).getMenuId());
        assertEquals(2L, responses.get(1).getMenuId());
        assertEquals(3L, responses.get(2).getMenuId());
    }
}
