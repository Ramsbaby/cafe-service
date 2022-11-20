package com.kakaopay.cafeservice.interceptor;

import com.kakaopay.cafeservice.component.CollectDataSender;
import com.kakaopay.cafeservice.domain.order.OrderDataSendDto;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderInterceptor implements HandlerInterceptor {

    private final CollectDataSender collectDataSender;

    private void sendDataCollectPlatform(OrderDataSendDto orderDataSendDto) {
        collectDataSender
            .sendData(orderDataSendDto)
            .subscribe(res -> log.info("response : " + res.toString()));
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) {
        if (Objects.equals(request.getMethod(), HttpMethod.POST.toString())) {
            OrderDataSendDto orderDataSendDto = (OrderDataSendDto) request.getAttribute("dataSendDto");

            if (!ObjectUtils.isEmpty(orderDataSendDto)) {
                sendDataCollectPlatform(OrderDataSendDto.of(orderDataSendDto.getUserId(), orderDataSendDto.getMenuId(),
                    orderDataSendDto.getPrice()));
            }
        }
    }
}
