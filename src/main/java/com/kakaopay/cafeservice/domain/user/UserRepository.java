package com.kakaopay.cafeservice.domain.user;

import java.util.Optional;
import javax.persistence.LockModeType;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @NotNull
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<User> findById(@NotNull Long id);
}
