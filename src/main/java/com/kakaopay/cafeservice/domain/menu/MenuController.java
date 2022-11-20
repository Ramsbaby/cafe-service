package com.kakaopay.cafeservice.domain.menu;

import com.kakaopay.cafeservice.domain.menu.MenuService.Top3MenuResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "menu-api/v1")
public class MenuController {

    private final MenuService menuService;

    @GetMapping("/menus")
    public List<MenuDto> findAllMenu() {
        return menuService.findAllMenu();
    }

    @GetMapping("/menus/popular")
    public List<Top3MenuResponse> findPopularMenus() {
        return menuService.findTop3Menu();
    }

}
