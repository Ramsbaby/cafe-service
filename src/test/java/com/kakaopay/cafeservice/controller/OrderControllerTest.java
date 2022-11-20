package com.kakaopay.cafeservice.controller;

import static com.kakaopay.cafeservice.ApiDocumentUtil.getDocumentRequest;
import static com.kakaopay.cafeservice.ApiDocumentUtil.getDocumentResponse;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.kakaopay.cafeservice.component.CollectDataSender;
import com.kakaopay.cafeservice.domain.menu.Menu;
import com.kakaopay.cafeservice.domain.menu.MenuRepository;
import com.kakaopay.cafeservice.domain.user.User;
import com.kakaopay.cafeservice.domain.user.UserRepository;
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
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MenuRepository menuRepository;

    @BeforeEach
    public void setUp() {
        CollectDataSender collectDataSender = new CollectDataSender();
    }

    @Test
    @DisplayName("주문 - 커피 주문/결제 하기")
    void order() throws Exception {
        // given
        //id:1, name:황정민, point:30000 인 유저 저장
        User user = User.builder().name("jordan").point(30000).build();
        userRepository.save(user);

        //id:1, name:아메리카노, price:3000 인 메뉴 저장
        Menu menu = Menu.builder().name("americano").price(3000).build();
        menuRepository.save(menu);

        // when
        // 유저 1이 메뉴1을 주문
        ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders
                .post("/order-api/v1/order")
                .header("Content-Type", "application/json")
                .content("{\"userId\":" + user.getId() + ",\"menuId\":" + menu.getId() + "}"))
            .andDo(MockMvcResultHandlers.print());

        // then
        resultActions
            .andExpect(status().isOk())
            .andDo(document(
                "order",
                getDocumentRequest(),
                getDocumentResponse(),
                requestFields(
                    fieldWithPath("userId").type(JsonFieldType.NUMBER).description("사용자 ID"),
                    fieldWithPath("menuId").type(JsonFieldType.NUMBER).description("메뉴 ID")
                ),
                responseFields(
                    fieldWithPath("orderId").type(JsonFieldType.NUMBER).description("주문 ID"),
                    fieldWithPath("userId").type(JsonFieldType.NUMBER).description("사용자 ID"),
                    fieldWithPath("menuId").type(JsonFieldType.NUMBER).description("메뉴 ID"),
                    fieldWithPath("price").type(JsonFieldType.NUMBER).description("주문가격"),
                    fieldWithPath("msg").type(JsonFieldType.STRING).description("주문메시지"),
                    fieldWithPath("createdAt").type(JsonFieldType.STRING).description("생성일"),
                    fieldWithPath("updatedAt").type(JsonFieldType.STRING).description("수정일")
                )
            ))
        ;

        //비동기 전송 응답을 받기 위한 sleep
        //없을 경우 테스트코드 즉시 종료라 응답 못 받고 테스트 fail 처리됨.
        Thread.sleep(3000);
    }
}
