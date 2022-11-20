package com.kakaopay.cafeservice.domain.user;

import com.kakaopay.cafeservice.exception.InvalidParameterException;
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
@RequestMapping(path = "user-api/v1")
public class UserController {

    private final UserService userService;

    @PostMapping("/point")
    public UserDto chargePoint(@RequestBody @Valid ChargePointRequest request, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new InvalidParameterException("invalid parameter", bindingResult.getAllErrors());
        }

        User user = userService.getUserAfterChargePoint(request.getUserId(), request.getPoint());
        return UserDto.of(user);
    }

    @Getter
    @Setter
    static class ChargePointRequest {
        @Min(0)
        private Long userId;
        @Min(0)
        private int point;
    }

}
