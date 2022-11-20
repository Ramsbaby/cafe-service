package com.kakaopay.cafeservice.domain.order;

import com.kakaopay.cafeservice.domain.order.OrderService.OrderResponse;
import com.kakaopay.cafeservice.exception.InvalidParameterException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "order-api/v1")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/order")
    public OrderResponse order(HttpServletRequest httpServletRequest,
        @RequestBody @Valid OrderRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidParameterException("invalid parameter", bindingResult.getAllErrors());
        }

        OrderResponse orderResponse = orderService.saveOrder(request.getUserId(), request.getMenuId());

        httpServletRequest.setAttribute("dataSendDto",
            OrderDataSendDto.of(orderResponse.getUserId(), orderResponse.getMenuId(), orderResponse.getPrice()));

        return orderResponse;
    }

    @Getter
    @Setter
    static class OrderRequest {

        @Min(0)
        private Long userId;
        @Min(0)
        private Long menuId;
    }

}
