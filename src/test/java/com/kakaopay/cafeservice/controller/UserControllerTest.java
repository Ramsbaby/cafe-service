package com.kakaopay.cafeservice.controller;

import static com.kakaopay.cafeservice.ApiDocumentUtil.getDocumentRequest;
import static com.kakaopay.cafeservice.ApiDocumentUtil.getDocumentResponse;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
    }

    @Test
    @DisplayName("?????? - ????????? ??????")
    void chargePoint() throws Exception {
        // given
        //id:1, name:?????????, point:30000 ??? ????????? ???????????????
        User user = User.builder().name("sonny").point(30000).build();
        userRepository.save(user);

        // when
        // ?????? 1?????? ????????? 10000?????? ???????????? ??????
        ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders
                .post("/user-api/v1/point")
                .header("Content-Type", "application/json")
                .content("{\"userId\":" + user.getId() + ",\"point\":10000}"))
            .andDo(MockMvcResultHandlers.print());

        // then
        resultActions
            .andExpect(status().isOk())
            .andDo(document(
                "chargePoint",
                getDocumentRequest(),
                getDocumentResponse(),
                requestFields(
                    fieldWithPath("userId").type(JsonFieldType.NUMBER).description("????????? ID"),
                    fieldWithPath("point").type(JsonFieldType.NUMBER).description("????????? ?????????")
                ),
                responseFields(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("????????? ID"),
                    fieldWithPath("name").type(JsonFieldType.STRING).description("????????? ??????"),
                    fieldWithPath("point").type(JsonFieldType.NUMBER).description("???????????????"),
                    fieldWithPath("createdAt").type(JsonFieldType.STRING).description("?????????"),
                    fieldWithPath("updatedAt").type(JsonFieldType.STRING).description("?????????")
                )
            ))
        ;
    }
}
