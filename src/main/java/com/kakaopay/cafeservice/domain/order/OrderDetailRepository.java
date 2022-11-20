package com.kakaopay.cafeservice.domain.order;

import com.kakaopay.cafeservice.domain.menu.PopularMenusDto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    @Query(value =
        "select menu_id as menuId, sum(count) as count \n"
            + "from order_details\n"
            + "where created_at between TIMESTAMPADD(DAY, -7, NOW())  and NOW()\n"
            + "group by menu_id\n"
            + "order by sum(count) desc\n"
            + "limit 3", nativeQuery = true)
    List<PopularMenusDto> findTop3Menu();
}