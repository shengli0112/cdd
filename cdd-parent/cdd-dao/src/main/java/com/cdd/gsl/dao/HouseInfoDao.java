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
            "single_price as singlePrice,use_area as useArea,create_ts as createTs,description as description " +
            " from t_house_info where status=1 and id=#{houseId}")
    public HouseInfoDetailVo selectHouseInfoById(Long houseId);

    @Select("<script> " +
            "select h.id as id, h.title as title, h.city as city, " +
            "h.county as county,h.town as town, h.street as street, h.area as area," +
            "h.house_number as houseNumber, h.selling_price as sellingPrice,concat(h.electricity,'KV') as electricity," +
            "(select dict_value from t_common_dict where dict_name='houseType' and dict_code=h.house_type) as houseType, " +
            "(select dict_value from t_common_dict where dict_name='houseUseType' and dict_code=h.house_use_type) as houseUseType, " +
            "(select dict_value from t_common_dict where dict_name='floor' and dict_code=h.floor) as floor, " +
            "(select dict_value from t_common_dict where dict_name='fireControl' and dict_code=h.fire_control) as fireControl, " +
            "(select dict_value from t_common_dict where dict_name='priceType' and dict_code=h.price_type) as priceType, " +
            "h.contacts as contacts,h.phone as phone, h.background as background, h.house_status as houseStatus," +
            "h.sign_contract as signContract,h.cover_area as coverArea,h.house_edge as houseEdge,h.user_id as userId," +
            "h.single_price as singlePrice,h.use_area as useArea,h.create_ts as createTs,u.username as username,u.portrait as portrait" +
            " from t_house_info h left join t_user_info u on h.user_id=u.id where h.status=1 " +
            "<if test=\"city != null\">" +
            " and h.city=#{city}"+
            "</if><if test=\"county != null\">" +
            " and h.county=#{county}"+
            "</if>" +
            "<if test=\"town != null\">" +
            " and h.town=#{town}"+
            "</if>" +
            "<if test=\"houseType != null\">"+
            " and h.house_type=#{houseType}"+
            "</if><if test=\"houseUseType != null\">"+
            " and h.house_use_type=#{houseUseType}"+
            "</if><if test=\"floor != null\">"+
            " and h.floor=#{floor}"+
            "</if><if test=\"areaFrom != null\">"+
            " and h.area <![CDATA[>= ]]> #{areaFrom}"+
            "</if><if test=\"areaTo != null\">"+
            " and h.area <![CDATA[<= ]]> #{areaTo}"+
            "</if><if test=\"priceFrom != null\">"+
            " and h.selling_price <![CDATA[>= ]]> #{priceFrom}"+
            "</if><if test=\"priceTo != null\">"+
            " and h.selling_price <![CDATA[<= ]]> #{priceTo}"+
            "</if>"+
            "<if test=\"keyword != null\">" +
            " and (title like '%#{keyword}%' or city like '%#{keyword}%' or county like '%#{keyword}%' or town like '%#{keyword}%' or street like '%#{keyword}%'" +
            " or house_number like '%#{keyword}%' or house_edge like '%#{keyword}%')"+
            "</if>"+
            " order by h.create_ts desc " +
            "<if test=\"areaOrder != null\">"+
            "<if test=\"areaOrder == 1\">,h.area</if>"+
            "<if test=\"areaOrder == 2\">,h.area desc</if>"+
            "</if>"+
            "<if test=\"priceOrder != null\">"+
            "<if test=\"priceOrder == 1\">,h.selling_price</if>"+
            "<if test=\"priceOrder == 2\">,h.selling_price desc</if>"+
            "</if>"+
            " limit #{from},#{pageSize}"+
            "</script>")
    public List<HouseInfoDomainVo> selectHouseInfoListByCondition(HouseConditionVo houseConditionVo);

    @Select("<script> " +
            "select h.id as id, h.title as title, h.city as city, " +
            "h.county as county,h.town as town, h.street as street, h.area as area," +
            "h.house_number as houseNumber, h.selling_price as sellingPrice,concat(h.electricity,'KV') as electricity," +
            "(select dict_value from t_common_dict where dict_name='houseType' and dict_code=h.house_type) as houseType, " +
            "(select dict_value from t_common_dict where dict_name='houseUseType' and dict_code=h.house_use_type) as houseUseType, " +
            "(select dict_value from t_common_dict where dict_name='floor' and dict_code=h.floor) as floor, " +
            "(select dict_value from t_common_dict where dict_name='fireControl' and dict_code=h.fire_control) as fireControl, " +
            "(select dict_value from t_common_dict where dict_name='priceType' and dict_code=h.price_type) as priceType, " +
            "h.contacts as contacts,h.phone as phone, h.background as background, h.house_status as houseStatus," +
            "h.sign_contract as signContract,h.cover_area as coverArea,h.house_edge as houseEdge,h.user_id as userId," +
            "h.single_price as singlePrice,h.use_area as useArea,h.create_ts as createTs,u.username as username,u.portrait as portrait" +
            " from t_house_info h left join t_user_info u on h.user_id=u.id where h.status=1 and h.user_id=#{userId} " +
            "<if test=\"city != null\">" +
            " and h.city=#{city}"+
            "</if><if test=\"county != null\">" +
            " and h.county=#{county}"+
            "</if>" +
            "<if test=\"town != null\">" +
            " and h.town=#{town}"+
            "</if>" +
            "<if test=\"houseType != null\">"+
            " and h.house_type=#{houseType}"+
            "</if><if test=\"houseUseType != null\">"+
            " and h.house_use_type=#{houseUseType}"+
            "</if><if test=\"floor != null\">"+
            " and h.floor=#{floor}"+
            "</if><if test=\"areaFrom != null\">"+
            " and h.area <![CDATA[>= ]]> #{areaFrom}"+
            "</if><if test=\"areaTo != null\">"+
            " and h.area <![CDATA[<= ]]> #{areaTo}"+
            "</if><if test=\"priceFrom != null\">"+
            " and selling_price <![CDATA[>= ]]> #{priceFrom}"+
            "</if><if test=\"priceTo != null\">"+
            " and h.selling_price <![CDATA[<= ]]> #{priceTo}"+
            "</if>"+
            " order by h.create_ts desc " +
            "<if test=\"areaOrder != null\">"+
            "<if test=\"areaOrder == 1\">,h.area</if>"+
            "<if test=\"areaOrder == 2\">,h.area desc</if>"+
            "</if>"+
            "<if test=\"priceOrder != null\">"+
            "<if test=\"priceOrder == 1\">,h.selling_price</if>"+
            "<if test=\"priceOrder == 2\">,h.selling_price desc</if>"+
            "</if>"+
            " limit #{from},#{pageSize}"+
            "</script>")
    public List<HouseInfoDomainVo> selectUserHouseInfoListByCondition(HouseConditionVo houseConditionVo);

    @Select("<script> " +
            "select h.id as id, h.title as title, h.city as city, " +
            "h.county as county,h.town as town, h.street as street, h.area as area," +
            "h.house_number as houseNumber, h.selling_price as sellingPrice,concat(h.electricity,'KV') as electricity," +
            "(select dict_value from t_common_dict where dict_name='houseType' and dict_code=h.house_type) as houseType, " +
            "(select dict_value from t_common_dict where dict_name='houseUseType' and dict_code=h.house_use_type) as houseUseType, " +
            "(select dict_value from t_common_dict where dict_name='floor' and dict_code=h.floor) as floor, " +
            "(select dict_value from t_common_dict where dict_name='fireControl' and dict_code=h.fire_control) as fireControl, " +
            "(select dict_value from t_common_dict where dict_name='priceType' and dict_code=h.price_type) as priceType, " +
            "h.contacts as contacts,h.phone as phone, h.background as background, h.house_status as houseStatus," +
            "h.sign_contract as signContract,h.cover_area as coverArea,h.house_edge as houseEdge,h.user_id as userId," +
            "h.single_price as singlePrice,h.use_area as useArea,h.create_ts as createTs,u.username as username,u.portrait as portrait" +
            " from t_house_info h left join t_user_info u on h.user_id=u.id where h.status=1 and h.house_status=1"+
            "<foreach collection=\"userIds\" index=\"index\" item=\"item\" open=\" and h.user_id in (\" close=\")\" separator=\",\">" +
            "#{item}"+
            "</foreach>"+
            "<if test=\"city != null\">" +
            " and h.city=#{city}"+
            "</if><if test=\"county != null\">" +
            " and h.county=#{county}"+
            "</if><if test=\"houseType != null\">"+
            " and h.house_type=#{houseType}"+
            "</if><if test=\"houseUseType != null\">"+
            " and h.house_use_type=#{houseUseType}"+
            "</if><if test=\"floor != null\">"+
            " and h.floor=#{floor}"+
            "</if><if test=\"areaFrom != null\">"+
            " and h.area <![CDATA[>= ]]> #{areaFrom}"+
            "</if><if test=\"areaTo != null\">"+
            " and h.area <![CDATA[<= ]]> #{areaTo}"+
            "</if><if test=\"priceFrom != null\">"+
            " and h.selling_price <![CDATA[>= ]]> #{priceFrom}"+
            "</if><if test=\"priceTo != null\">"+
            " and h.selling_price <![CDATA[<= ]]> #{priceTo}"+
            "</if>"+
            " order by h.create_ts desc " +
            "<if test=\"areaOrder != null\">"+
            "<if test=\"areaOrder == 1\">,h.area</if>"+
            "<if test=\"areaOrder == 2\">,h.area desc</if>"+
            "</if>"+
            "<if test=\"priceOrder != null\">"+
            "<if test=\"priceOrder == 1\">,h.selling_price</if>"+
            "<if test=\"priceOrder == 2\">,h.selling_price desc</if>"+
            "</if>"+
            " limit #{from},#{pageSize}"+
            "</script>")
    public List<HouseInfoDomainVo> selectCompanyHouseInfoList(HouseCompanyVo houseConditionVo);

    //主管能看到房源
    @Select("<script> " +
            "select h.id as id, h.title as title, h.city as city, " +
            "h.county as county,h.town as town, h.street as street, h.area as area," +
            "h.house_number as houseNumber, h.selling_price as sellingPrice,concat(h.electricity,'KV') as electricity," +
            "(select dict_value from t_common_dict where dict_name='houseType' and dict_code=h.house_type) as houseType, " +
            "(select dict_value from t_common_dict where dict_name='houseUseType' and dict_code=h.house_use_type) as houseUseType, " +
            "(select dict_value from t_common_dict where dict_name='floor' and dict_code=h.floor) as floor, " +
            "(select dict_value from t_common_dict where dict_name='fireControl' and dict_code=h.fire_control) as fireControl, " +
            "(select dict_value from t_common_dict where dict_name='priceType' and dict_code=h.price_type) as priceType, " +
            "h.contacts as contacts,h.phone as phone, h.background as background, h.house_status as houseStatus," +
            "h.sign_contract as signContract,h.cover_area as coverArea,h.house_edge as houseEdge,h.user_id as userId," +
            "h.single_price as singlePrice,h.use_area as useArea,h.create_ts as createTs,u.username as username,u.portrait as portrait" +
            " from t_house_info h left join t_user_info u on h.user_id=u.id where h.status=1"+
            "<foreach collection=\"userIds\" index=\"index\" item=\"item\" open=\" and h.user_id in (\" close=\")\" separator=\",\">" +
            "#{item}"+
            "</foreach>"+
            "<if test=\"city != null\">" +
            " and h.city=#{city}"+
            "</if><if test=\"county != null\">" +
            " and h.county=#{county}"+
            "</if><if test=\"houseType != null\">"+
            " and h.house_type=#{houseType}"+
            "</if><if test=\"houseUseType != null\">"+
            " and h.house_use_type=#{houseUseType}"+
            "</if><if test=\"floor != null\">"+
            " and h.floor=#{floor}"+
            "</if><if test=\"areaFrom != null\">"+
            " and h.area <![CDATA[>= ]]> #{areaFrom}"+
            "</if><if test=\"areaTo != null\">"+
            " and h.area <![CDATA[<= ]]> #{areaTo}"+
            "</if><if test=\"priceFrom != null\">"+
            " and h.selling_price <![CDATA[>= ]]> #{priceFrom}"+
            "</if><if test=\"priceTo != null\">"+
            " and h.selling_price <![CDATA[<= ]]> #{priceTo}"+
            "</if>"+
            " order by h.create_ts desc " +
            "<if test=\"areaOrder != null\">"+
            "<if test=\"areaOrder == 1\">,h.area</if>"+
            "<if test=\"areaOrder == 2\">,h.area desc</if>"+
            "</if>"+
            "<if test=\"priceOrder != null\">"+
            "<if test=\"priceOrder == 1\">,h.selling_price</if>"+
            "<if test=\"priceOrder == 2\">,h.selling_price desc</if>"+
            "</if>"+
            " limit #{from},#{pageSize}"+
            "</script>")
    public List<HouseInfoDomainVo> selectCompanyAllHouseInfoList(HouseCompanyVo houseConditionVo);

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
            " from t_house_info where id >= " +
            "(select floor(rand() * (select max(id) from t_house_info where status=1))) " +
            "and status=1 order by rand() limit 3")
    List<HouseInfoDomainVo> selectHouseInfoListByLike();

    @Select("select count(*) from t_house_info where user_id=#{userId} and status=1")
    int selectHouseCountByUserId(Long userId);

    @Select("select count(*) from t_house_info where status=1")
    int selectAllHouseCount();
}
