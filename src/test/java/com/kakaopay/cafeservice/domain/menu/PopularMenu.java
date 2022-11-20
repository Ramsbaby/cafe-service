package com.kakaopay.cafeservice.domain.menu;

class PopularMenu implements PopularMenusDto {

    Long menuId;
    int orderCnt;

    PopularMenu(Long menuId, int orderCnt) {
        this.menuId = menuId;
        this.orderCnt = orderCnt;
    }

    @Override
    public Long getMenuId() {
        return this.menuId;
    }

    @Override
    public int getCount() {
        return this.orderCnt;
    }
}
