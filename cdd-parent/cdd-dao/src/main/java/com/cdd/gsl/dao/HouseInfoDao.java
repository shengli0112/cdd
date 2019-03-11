package com.cdd.gsl.dao;

import com.cdd.gsl.domain.HouseInfoDomain;
import com.cdd.gsl.vo.HouseConditionVo;
import com.cdd.gsl.vo.HouseInfoDetailVo;
import com.cdd.gsl.vo.HouseInfoDomainVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface HouseInfoDao {
    @Select("select id as id, title as title, city as city, " +
            "county as county, street as street, area as area,house_number as houseNumber, concat(selling_price,'万元') as sellingPrice,concat(electricity,'KV') as electricity," +
            "(select dict_value from t_common_dict where dict_name='houseType' and dict_code=house_type) as houseType, " +
            "(select dict_value from t_common_dict where dict_name='houseUseType' and dict_code=house_use_type) as houseUseType, " +
            "(select dict_value from t_common_dict where dict_name='floor' and dict_code=floor) as floor, " +
            "(select dict_value from t_common_dict where dict_name='fireControl' and dict_code=fire_control) as fireControl, " +
            "contacts as contacts,phone as phone, background as background, house_status as houseStatus " +
            "from t_house_info where status=1 and id=#{houseId}")
    public HouseInfoDetailVo selectHouseInfoById(Long houseId);

    @Select("<script> " +
            "select id as id, title as title, city as city, " +
            "county as county, street as street, area as area,house_number as houseNumber, concat(selling_price,\"万元\") as sellingPrice, " +
            "(select dict_value from t_common_dict where dict_name=\"houseType\" and dict_code=house_type) as houseType, " +
            "(select dict_value from t_common_dict where dict_name=\"houseUseType\" and dict_code=house_use_type) as houseUseType, " +
            "(select dict_value from t_common_dict where dict_name=\"floor\" and dict_code=floor) as houseUseType, " +
            " background as background, house_status as houseStatus " +
            "from t_house_info where status=1 " +
            "<if test=\"city != null\">" +
            " and city=#{city}"+
            "</if><if test=\"county != null\">" +
            " and county=#{county}"+
            "</if><if test=\"houseType != null\">"+
            " and houseType=#{houseType}"+
            "</if><if test=\"houseUseType != null\">"+
            " and houseUseType=#{houseUseType}"+
            "</if><if test=\"floor != null\">"+
            " and floor=#{floor}"+
            "</if><if test=\"areaFrom != null\">"+
            " and area <![CDATA[>= ]]> #{areaFrom}"+
            "</if><if test=\"areaTo != null\">"+
            " and area <![CDATA[<= ]]> #{areaTo}"+
            "</if><if test=\"priceFrom != null\">"+
            " and selling_price <![CDATA[>= ]]> #{priceFrom}"+
            "</if><if test=\"priceTo != null\">"+
            " and selling_price <![CDATA[<= ]]> #{priceTo}"+
            "</if>"+
            " order by create_ts desc " +
            "<if test=\"areaOrder != null\">"+
            "<if test=\"areaOrder == 1\">,area</if>"+
            "<if test=\"areaOrder == 2\">,area desc</if>"+
            "</if>"+
            "<if test=\"priceOrder != null\">"+
            "<if test=\"priceOrder == 1\">,selling_price</if>"+
            "<if test=\"priceOrder == 2\">,selling_price desc</if>"+
            "</if>"+
            " limit #{from},#{pageSize}"+
            "</script>")
    public List<HouseInfoDomainVo> selectHouseInfoListByCondition(HouseConditionVo houseConditionVo);

    @Select("<script> " +
            "select id as id, title as title, city as city, " +
            "county as county, street as street, area as area,house_number as houseNumber, concat(selling_price,\"万元\") as sellingPrice, " +
            "(select dict_value from t_common_dict where dict_name=\"houseType\" and dict_code=house_type) as houseType, " +
            "(select dict_value from t_common_dict where dict_name=\"houseUseType\" and dict_code=house_use_type) as houseUseType, " +
            "(select dict_value from t_common_dict where dict_name=\"floor\" and dict_code=floor) as houseUseType, " +
            " background as background, house_status as houseStatus " +
            "from t_house_info where status=1 and user_id=#{userId} " +
            "<if test=\"city != null\">" +
            " and city=#{city}"+
            "</if><if test=\"county != null\">" +
            " and county=#{county}"+
            "</if><if test=\"houseType != null\">"+
            " and house_type=#{houseType}"+
            "</if><if test=\"houseUseType != null\">"+
            " and house_use_type=#{houseUseType}"+
            "</if><if test=\"floor != null\">"+
            " and floor=#{floor}"+
            "</if><if test=\"areaFrom != null\">"+
            " and area <![CDATA[>= ]]> #{areaFrom}"+
            "</if><if test=\"areaTo != null\">"+
            " and area <![CDATA[<= ]]> #{areaTo}"+
            "</if><if test=\"priceFrom != null\">"+
            " and selling_price <![CDATA[>= ]]> #{priceFrom}"+
            "</if><if test=\"priceTo != null\">"+
            " and selling_price <![CDATA[<= ]]> #{priceTo}"+
            "</if>"+
            " order by create_ts desc " +
            "<if test=\"areaOrder != null\">"+
            "<if test=\"areaOrder == 1\">,area</if>"+
            "<if test=\"areaOrder == 2\">,area desc</if>"+
            "</if>"+
            "<if test=\"priceOrder != null\">"+
            "<if test=\"priceOrder == 1\">,selling_price</if>"+
            "<if test=\"priceOrder == 2\">,selling_price desc</if>"+
            "</if>"+
            " limit #{from},#{pageSize}"+
            "</script>")
    public List<HouseInfoDomainVo> selectUserHouseInfoListByCondition(HouseConditionVo houseConditionVo);

    @Select("select id as id, title as title, city as city, " +
            "county as county, street as street, area as area,house_number as houseNumber, concat(selling_price,\"万元\") as sellingPrice, " +
            "(select dict_value from t_common_dict where dict_name=\"houseType\" and dict_code=house_type) as houseType, " +
            "(select dict_value from t_common_dict where dict_name=\"houseUseType\" and dict_code=house_use_type) as houseUseType, " +
            "(select dict_value from t_common_dict where dict_name=\"floor\" and dict_code=floor) as floor, " +
            " background as background, house_status as houseStatus " +
            "from t_house_info where id >= " +
            "(select floor(rand() * (select max(id) from t_house_info where status=1))) " +
            "and status=1 limit 3")
    List<HouseInfoDomainVo> selectHouseInfoListByLike();
}
