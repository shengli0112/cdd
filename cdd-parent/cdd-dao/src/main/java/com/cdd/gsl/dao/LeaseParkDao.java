package com.cdd.gsl.dao;

import com.cdd.gsl.domain.LeaseParkInfoDomain;
import com.cdd.gsl.domain.SellParkInfoDomain;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface LeaseParkDao {
    @Select("<script>" +
            "select id as id,city as city,county as county,address as address,park_name as parkName,title as title," +
            "total_area as totalArea,total_price as totalPrice," +
            "(select group_concat(dict_value separator ' ') from t_common_dict where dict_name='tag' and dict_code in (tag) group by dict_name) as tag from t_lease_park_info where status=1 " +
            "<if test='region != null' and " +

            "</script>")
    public List<LeaseParkInfoDomain> selectLeaseParkInfoList(String region, String price, String area, Long sellParkId);
}
