package com.kakaopay.cafeservice.domain.order;

import com.kakaopay.cafeservice.common.entity.CommonEntity;
import com.kakaopay.cafeservice.common.entity.listener.MyEntityListener;
import com.kakaopay.cafeservice.domain.user.User;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
@EqualsAndHashCode(callSuper = true)
@EntityListeners(value = {AuditingEntityListener.class})
public class Order extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetailList = new ArrayList<>();

    public static Order create(User user, OrderDetail... orderDetails) {
        Order order = new Order();
        Arrays.stream(orderDetails).forEach(order::addOrderDetail);
        user.usePoint(order.getTotalPrice());
        order.setUser(user);

        return order;
    }

    public void addOrderDetail(OrderDetail orderDetail) {
        orderDetail.setOrder(this);
        orderDetailList.add(orderDetail);
    }

    public int getTotalPrice() {
        int totalPrice = 0;
        return orderDetailList.stream()
            .mapToInt(OrderDetail::getOrderPrice)
            .reduce(totalPrice, Integer::sum);
    }
}
