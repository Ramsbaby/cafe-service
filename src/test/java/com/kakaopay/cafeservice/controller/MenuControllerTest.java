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
        //?????? 3??? ??????
        Menu menu1 = Menu.builder().name("americano").price(3000).build();
        Menu menu2 = Menu.builder().name("dolce latte").price(4000).build();
        Menu menu3 = Menu.builder().name("hot milk").price(5000).build();
        menuRepository.saveAll(List.of(menu1, menu2, menu3));

        //?????? 3??? ??????
        User user1 = User.builder().name("jordan").point(30000).build();
        User user2 = User.builder().name("musk").point(40000).build();
        User user3 = User.builder().name("sonny").point(50000).build();
        userRepository.saveAll(List.of(user1, user2, user3));

        //?????? 1??? ??????1 1??? ??????
        orderService.saveOrder(user1.getId(), menu1.getId());

        //?????? 2??? ??????2 2??? ??????
        orderService.saveOrder(user2.getId(), menu2.getId());
        orderService.saveOrder(user2.getId(), menu2.getId());

        //?????? 3??? ??????3 3??? ??????
        orderService.saveOrder(user3.getId(), menu3.getId());
        orderService.saveOrder(user3.getId(), menu3.getId());
        orderService.saveOrder(user3.getId(), menu3.getId());
    }

    @Test
    @DisplayName("?????? - ?????? ?????? ??????")
    void findAllMenu() throws Exception {
        // given
        //id:1, name:???????????????, point:3000 ??? ?????? ??????
        //id:2, name:???????????????, point:4000 ??? ?????? ??????

        // when
        // ?????? ?????? ???????????? ??????
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
                    fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("?????? ID"),
                    fieldWithPath("[].name").type(JsonFieldType.STRING).description("?????? ??????"),
                    fieldWithPath("[].price").type(JsonFieldType.NUMBER).description("??????"),
                    fieldWithPath("[].createdAt").type(JsonFieldType.STRING).description("?????????"),
                    fieldWithPath("[].updatedAt").type(JsonFieldType.STRING).description("?????????")
                )
            ))
        ;
    }


    @Test
    @DisplayName("?????? - ?????? ?????? ?????? ??????")
    void findPopularMenus() throws Exception {
        // given
        //?????? 1,2,3 ??????
        //?????? 1,2,3 ??????
        //?????? 1??? ??????1 1??? ??????
        //?????? 2??? ??????2 2??? ??????
        //?????? 3??? ??????3 3??? ??????

        // when
        // ?????? ?????? ?????? ??????
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
                    fieldWithPath("[].menuId").type(JsonFieldType.NUMBER).description("?????? ID"),
                    fieldWithPath("[].count").type(JsonFieldType.NUMBER).description("?????? ??????")
                )
            ))
        ;
    }
}
