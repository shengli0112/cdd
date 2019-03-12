package com.cdd.gsl.dao;

import com.cdd.gsl.domain.HouseInfoDomain;
import com.cdd.gsl.vo.HouseCompanyVo;
import com.cdd.gsl.vo.HouseConditionVo;
import com.cdd.gsl.vo.HouseInfoDetailVo;
import com.cdd.gsl.vo.HouseInfoDomainVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface HouseInfoDao {
    @Select("select id as id, title as title, city as city, " +
            "county as county,town as town, street as street, area as area," +
            "house_number as houseNumber, selling_price as sellingPrice,concat(electricity,'KV') as electricity," +
            "(select dict_value from t_common_dict where dict_name='houseType' and dict_code=house_type) as houseType, " +
            "(select dict_value from t_common_dict where dict_name='houseUseType' and dict_code=house_use_type) as houseUseType, " +
            "(select dict_value from t_common_dict where dict_name='floor' and dict_code=floor) as floor, " +
            "(select dict_value from t_common_dict where dict_name='fireControl' and dict_code=fire_control) as fireControl, " +
            "(select dict_value from t_common_dict where dict_name='priceType' and dict_code=price_type) as priceType, " +
            "contacts as contacts,phone as phone, background as background, house_status as houseStatus," +
            "sign_contract as signContract,cover_area as coverArea,house_edge as houseEdge,user_id as userId," +
            "single_price as singlePrice,use_area as useArea,create_ts as createTs " +
            "from t_house_info where status=1 and id=#{houseId}")
    public HouseInfoDetailVo selectHouseInfoById(Long houseId);

    @Select("<script> " +
            "select id as id, title as title, city as city, " +
            "county as county,town as town, street as street, area as area," +
            "house_number as houseNumber, selling_price as sellingPrice,concat(electricity,'KV') as electricity," +
            "(select dict_value from t_common_dict where dict_name='houseType' and dict_code=house_type) as houseType, " +
            "(select dict_value from t_common_dict where dict_name='houseUseType' and dict_code=house_use_type) as houseUseType, " +
            "(select dict_value from t_common_dict where dict_name='floor' and dict_code=floor) as floor, " +
            "(select dict_value from t_common_dict where dict_name='fireControl' and dict_code=fire_control) as fireControl, " +
            "(select dict_value from t_common_dict where dict_name='priceType' and dict_code=price_type) as priceType, " +
            "contacts as contacts,phone as phone, background as background, house_status as houseStatus," +
            "sign_contract as signContract,cover_area as coverArea,house_edge as houseEdge,user_id as userId," +
            "single_price as singlePrice,use_area as useArea,create_ts as createTs " +
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
            "county as county,town as town, street as street, area as area," +
            "house_number as houseNumber, selling_price as sellingPrice,concat(electricity,'KV') as electricity," +
            "(select dict_value from t_common_dict where dict_name='houseType' and dict_code=house_type) as houseType, " +
            "(select dict_value from t_common_dict where dict_name='houseUseType' and dict_code=house_use_type) as houseUseType, " +
            "(select dict_value from t_common_dict where dict_name='floor' and dict_code=floor) as floor, " +
            "(select dict_value from t_common_dict where dict_name='fireControl' and dict_code=fire_control) as fireControl, " +
            "(select dict_value from t_common_dict where dict_name='priceType' and dict_code=price_type) as priceType, " +
            "contacts as contacts,phone as phone, background as background, house_status as houseStatus," +
            "sign_contract as signContract,cover_area as coverArea,house_edge as houseEdge,user_id as userId," +
            "single_price as singlePrice,use_area as useArea,create_ts as createTs " +
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

    @Select("<script> " +
            "select id as id, title as title, city as city, " +
            "county as county,town as town, street as street, area as area," +
            "house_number as houseNumber, selling_price as sellingPrice,concat(electricity,'KV') as electricity," +
            "(select dict_value from t_common_dict where dict_name='houseType' and dict_code=house_type) as houseType, " +
            "(select dict_value from t_common_dict where dict_name='houseUseType' and dict_code=house_use_type) as houseUseType, " +
            "(select dict_value from t_common_dict where dict_name='floor' and dict_code=floor) as floor, " +
            "(select dict_value from t_common_dict where dict_name='fireControl' and dict_code=fire_control) as fireControl, " +
            "(select dict_value from t_common_dict where dict_name='priceType' and dict_code=price_type) as priceType, " +
            "contacts as contacts,phone as phone, background as background, house_status as houseStatus," +
            "sign_contract as signContract,cover_area as coverArea,house_edge as houseEdge,user_id as userId," +
            "single_price as singlePrice,use_area as useArea,create_ts as createTs " +
            "from t_house_info where status=1 and user_id=#{userId} " +
            "<foreach collection=\"userIds\" index=\"index\" item=\"item\" open=\" and user_id in (\" close=\")\" separator=\",\">" +
            "#{item}"+
            "</foreach>"+
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
    public List<HouseInfoDomainVo> selectCompanyHouseInfoList(HouseCompanyVo houseConditionVo);

    @Select("select id as id, title as title, city as city, " +
            "county as county,town as town, street as street, area as area," +
            "house_number as houseNumber, selling_price as sellingPrice,concat(electricity,'KV') as electricity," +
            "(select dict_value from t_common_dict where dict_name='houseType' and dict_code=house_type) as houseType, " +
            "(select dict_value from t_common_dict where dict_name='houseUseType' and dict_code=house_use_type) as houseUseType, " +
            "(select dict_value from t_common_dict where dict_name='floor' and dict_code=floor) as floor, " +
            "(select dict_value from t_common_dict where dict_name='fireControl' and dict_code=fire_control) as fireControl, " +
            "(select dict_value from t_common_dict where dict_name='priceType' and dict_code=price_type) as priceType, " +
            "contacts as contacts,phone as phone, background as background, house_status as houseStatus," +
            "sign_contract as signContract,cover_area as coverArea,house_edge as houseEdge,user_id as userId," +
            "single_price as singlePrice,use_area as useArea,create_ts as createTs " +
            "from t_house_info where id >= " +
            "(select floor(rand() * (select max(id) from t_house_info where status=1))) " +
            "and status=1 limit 3")
    List<HouseInfoDomainVo> selectHouseInfoListByLike();
}
