package com.cdd.gsl.dao;

import com.cdd.gsl.domain.SellParkInfoDomain;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SellParkDao {
    @Select("<script>" +
            "select id as id,city as city,county as county,address as address,park_name as parkName,title as title," +
            "total_area as totalArea,total_price as totalPrice,tag as tag from t_sell_park_info where status=1 " +
            "<if test='region != null' and " +

            "</script>")
    public List<SellParkInfoDomain> selectSellParkInfoList(String region, String price, String area, Long sellParkId);
}
