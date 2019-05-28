package com.cdd.gsl.dao;

import com.cdd.gsl.domain.LeaseParkInfoDomain;
import com.cdd.gsl.domain.SellParkInfoDomain;
import com.cdd.gsl.vo.LeaseParkCondition;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface LeaseParkDao {

    @Select("<script>" +
            "select h.id as id,h.city as city,h.county as county,h.town as town," +
            "h.address as address,h.park_name as parkName," +
            "h.total_area as totalArea,h.unit_price as unitPrice," +
            "(select group_concat(dict_value separator ' ') from t_common_dict where dict_name='tag' and dict_code in (h.tag) group by dict_name) as tag, " +
            "(select dict_value from t_common_dict where dict_name='priceType' and dict_code=h.price_type) as priceType " +
            "from t_lease_park_info h where h.status=1 " +
            "<if test='userId != null'>" +
            "  and h.user_id=#{userId}" +
            "</if> " +
            "<if test=\"city != null\">" +
            " and h.city=#{city}"+
            "</if><if test=\"county != null\">" +
            " and h.county=#{county}"+
            "</if>" +
            "<if test=\"town != null\">" +
            " and h.town=#{town}"+
            "</if>" +
            "<choose>" +
            "<when test='areaFrom != null and areaTo != null'>" +
            " and (h.total_area <![CDATA[>= ]]> #{areaFrom} and h.total_area <![CDATA[<= ]]> #{areaTo})" +
            "</when>" +
            "<otherwise>" +
            "<if test='areaFrom != null'>" +
            " and (h.total_area <![CDATA[>= ]]> #{areaFrom})" +
            "</if>" +
            "<if test='areaTo != null'>" +
            " and (h.total_area <![CDATA[<= ]]> #{areaTo})" +
            "</if>" +
            "</otherwise>"+
            "</choose>"+

            "<choose>" +
            "<when test='priceFrom != null and priceTo != null'>" +
            " and (h.total_price <![CDATA[>= ]]> #{priceFrom} and h.total_price <![CDATA[<= ]]> #{priceTo})" +
            "</when>" +
            "<otherwise>" +
            "<if test='priceFrom != null'>" +
            " and h.total_price <![CDATA[>= ]]> #{priceFrom}" +
            "</if>" +
            "<if test='priceTo != null'>" +
            " and (h.total_price <![CDATA[<= ]]> #{priceTo})" +
            "</if>" +
            "</otherwise>"+
            "</choose>"
            +
            "<if test='keyword != null'>" +
            " and (h.address like concat('%','${keyword}','%') or h.city like concat('%','${keyword}','%') or h.county like concat('%','${keyword}','%') or h.town like concat('%','${keyword}','%') " +
            " or h.park_name like concat('%','${keyword}','%') or h.description like concat('%','${keyword}','%')) or h.industry like concat('%','${keyword}','%'))"+
            "</if>"+
            " order by h.create_ts desc" +
            "<if test='areaOrder != null'>"+
            "<if test='areaOrder == 1'>,h.area,h.cover_area</if>"+
            "<if test='areaOrder == 2'>,h.area desc,h.cover_area desc</if>"+
            "</if>"+
            "<if test='priceOrder != null'>"+
            "<if test='priceOrder == 1'>,h.selling_price</if>"+
            "<if test='priceOrder == 2'>,h.selling_price desc</if>"+
            "</if>"+
            " limit #{from},#{pageSize}"+

            "</script>")
    public List<LeaseParkInfoDomain> selectLeaseParkInfoList(LeaseParkCondition leaseParkCondition);
}
