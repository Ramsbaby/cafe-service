package com.kakaopay.cafeservice.controller;

import static com.kakaopay.cafeservice.ApiDocumentUtil.getDocumentRequest;
import static com.kakaopay.cafeservice.ApiDocumentUtil.getDocumentResponse;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.kakaopay.cafeservice.domain.menu.Menu;
import com.kakaopay.cafeservice.domain.menu.MenuRepository;
import com.kakaopay.cafeservice.domain.order.OrderService;
import com.kakaopay.cafeservice.domain.user.User;
import com.kakaopay.cafeservice.domain.user.UserRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
@TestPropertySource(properties = {"spring.config.location=classpath:application-test.yml"})
@AutoConfigureMockMvc
@Transactional
@AutoConfigureRestDocs
public class MenuControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderService orderService;

    @BeforeEach
    public void setUp() {
        //메뉴 3개 저장
        Menu menu1 = Menu.builder().name("americano").price(3000).build();
        Menu menu2 = Menu.builder().name("dolce latte").price(4000).build();
        Menu menu3 = Menu.builder().name("hot milk").price(5000).build();
        menuRepository.saveAll(List.of(menu1, menu2, menu3));

        //유저 3명 저장
        User user1 = User.builder().name("jordan").point(30000).build();
        User user2 = User.builder().name("musk").point(40000).build();
        User user3 = User.builder().name("sonny").point(50000).build();
        userRepository.saveAll(List.of(user1, user2, user3));

        //유저 1이 매뉴1 1번 주문
        orderService.saveOrder(user1.getId(), menu1.getId());

        //유저 2가 매뉴2 2번 주문
        orderService.saveOrder(user2.getId(), menu2.getId());
        orderService.saveOrder(user2.getId(), menu2.getId());

        //유저 3이 매뉴3 3번 주문
        orderService.saveOrder(user3.getId(), menu3.getId());
        orderService.saveOrder(user3.getId(), menu3.getId());
        orderService.saveOrder(user3.getId(), menu3.getId());
    }

    @Test
    @DisplayName("메뉴 - 전체 메뉴 조회")
    void findAllMenu() throws Exception {
        // given
        //id:1, name:아메리카노, point:3000 인 메뉴 저장
        //id:2, name:프라푸치노, point:4000 인 메뉴 저장

        // when
        // 모든 메뉴 조회하는 경우
        ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders
                .get("/menu-api/v1/menus")
                .header("Content-Type", "application/json"))
            .andDo(MockMvcResultHandlers.print());

        // then
        resultActions
            .andExpect(status().isOk())
            .andDo(document(
                "findAllMenu",
                getDocumentRequest(),
                getDocumentResponse(),
                responseFields(
                    fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("메뉴 ID"),
                    fieldWithPath("[].name").type(JsonFieldType.STRING).description("메뉴 이름"),
                    fieldWithPath("[].price").type(JsonFieldType.NUMBER).description("가격"),
                    fieldWithPath("[].createdAt").type(JsonFieldType.STRING).description("생성일"),
                    fieldWithPath("[].updatedAt").type(JsonFieldType.STRING).description("수정일")
                )
            ))
        ;
    }


    @Test
    @DisplayName("메뉴 - 인기 메뉴 목록 조회")
    void findPopularMenus() throws Exception {
        // given
        //유저 1,2,3 저장
        //메뉴 1,2,3 저장
        //유저 1이 매뉴1 1번 주문
        //유저 2가 매뉴2 2번 주문
        //유저 3이 매뉴3 3번 주문

        // when
        // 인기 메뉴 목록 조회
        ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders
                .get("/menu-api/v1/menus/popular")
                .header("Content-Type", "application/json"))
            .andDo(MockMvcResultHandlers.print());

        // then
        resultActions
            .andExpect(status().isOk())
            .andDo(document(
                "findPopularMenus",
                getDocumentRequest(),
                getDocumentResponse(),
                responseFields(
                    fieldWithPath("[].menuId").type(JsonFieldType.NUMBER).description("메뉴 ID"),
                    fieldWithPath("[].count").type(JsonFieldType.NUMBER).description("주문 횟수")
                )
            ))
        ;
    }
}
