package com.kakaopay.cafeservice.domain.user;

import com.kakaopay.cafeservice.common.entity.CommonEntity;
import com.kakaopay.cafeservice.common.entity.listener.MyEntityListener;
import com.kakaopay.cafeservice.exception.NotEnoughPointException;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@EqualsAndHashCode(callSuper = true)
@EntityListeners(value = {AuditingEntityListener.class})
public class User extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String name;

    private int point;

    public static User create(UserDto userDto) {
        return User.builder().name(userDto.getName()).point(userDto.getPoint()).build();
    }

    public void usePoint(int price) {
        if (this.point < price) {
            throw new NotEnoughPointException("포인트가 충분하지 않습니다.");
        }

        this.point -= price;
    }
}
