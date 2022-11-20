package com.kakaopay.cafeservice.domain.user;

import com.kakaopay.cafeservice.aop.SetDataSource;
import com.kakaopay.cafeservice.aop.SetDataSource.DataSourceType;
import com.kakaopay.cafeservice.exception.NotExistUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @SetDataSource(dataSourceType = DataSourceType.WRITER)
    public User getUserAfterChargePoint(Long userId, int point) {

        User user = userRepository.findById(userId).orElseThrow(
            () -> new NotExistUserException("유저가 존재하지 않습니다."));

        user.setPoint(user.getPoint() + point);

        return userRepository.save(user);
    }
}
