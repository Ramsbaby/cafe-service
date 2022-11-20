package com.kakaopay.cafeservice.domain.menu;

import com.kakaopay.cafeservice.domain.order.OrderDetailRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final OrderDetailRepository orderDetailRepository;

    public List<MenuDto> findAllMenu() {
        List<Menu> menuList = menuRepository.findAll();

        return menuList.stream().map(MenuDto::of).collect(Collectors.toList());
    }

    public List<Top3MenuResponse> findTop3Menu() {
        List<Top3MenuResponse> responseList = new ArrayList<>();
        List<PopularMenusDto> orderDetailList = orderDetailRepository.findTop3Menu();

        orderDetailList.forEach(detail ->
            responseList.add(Top3MenuResponse.builder()
                .menuId(detail.getMenuId())
                .count(detail.getCount())
                .build())
        );

        return responseList;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    static class Top3MenuResponse {

        private Long menuId;
        private int count;
    }

}
