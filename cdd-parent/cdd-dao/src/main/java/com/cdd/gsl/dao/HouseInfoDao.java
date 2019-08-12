package com.cdd.gsl.dao;

import com.cdd.gsl.admin.HouseAdminConditionVo;
import com.cdd.gsl.domain.HouseInfoDomain;
import com.cdd.gsl.vo.*;
import org.apache.ibatis.annotations.Param;
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
            "single_price as singlePrice,use_area as useArea,create_ts as createTs,description as description,trade as trade,expire_date as expireDate," +
            "company_name as companyName,staff_number as staffNumber,tax as tax " +
            " from t_house_info where status=1 and id=#{houseId}")
    public HouseInfoDetailVo selectHouseInfoById(Long houseId);

    @Select("<script> " +
            "select * from ((select h.id as id, h.title as title, h.city as city, " +
            "h.county as county,h.town as town, h.street as street, h.area as area," +
            "h.house_number as houseNumber, h.selling_price as sellingPrice,concat(h.electricity,'KV') as electricity," +
            "(select dict_value from t_common_dict where dict_name='houseType' and dict_code=h.house_type) as houseType, " +
            "(select dict_value from t_common_dict where dict_name='houseUseType' and dict_code=h.house_use_type) as houseUseType, " +
            "(select dict_value from t_common_dict where dict_name='floor' and dict_code=h.floor) as floor, " +
            "(select dict_value from t_common_dict where dict_name='fireControl' and dict_code=h.fire_control) as fireControl, " +
            "(select dict_value from t_common_dict where dict_name='priceType' and dict_code=h.price_type) as priceType, " +
            "h.contacts as contacts,h.phone as phone, h.background as background, h.house_status as houseStatus," +
            "h.sign_contract as signContract,h.cover_area as coverArea,h.house_edge as houseEdge,h.user_id as userId," +
            "h.single_price as singlePrice,h.use_area as useArea,top.create_ts as createTs,u.username as username,u.portrait as portrait,h.trade as trade,1 as top," +
            "h.company_name as companyName,h.staff_number as staffNumber,h.tax as tax " +
            " from t_house_top top left join t_house_info h on top.obj_id=h.id left join t_user_info u on h.user_id=u.id " +
            " where h.status=1 and top.status=1 and top.type='house' " +
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
            "<if test='houseUseType == null'>"+
            " and (h.house_use_type=3 or h.house_use_type=4) "+
            "</if>"+
            "<if test=\"houseType != null\">"+
            " and h.house_type=#{houseType}"+
            "</if><if test=\"houseUseType != null\">"+
            " and h.house_use_type=#{houseUseType}"+
            "</if><if test=\"floor != null\">"+
            " and h.floor=#{floor}"+
            "</if>"+
            "<choose>" +
            "<when test='areaFrom != null and areaTo != null'>" +
            " and ((h.area <![CDATA[>= ]]> #{areaFrom} and h.area <![CDATA[<= ]]> #{areaTo}) or (h.cover_area <![CDATA[>= ]]> #{areaFrom} and h.cover_area <![CDATA[<= ]]> #{areaTo}))" +
            "</when>" +
            "<otherwise>" +
            "<if test='areaFrom != null'>" +
            " and (h.area <![CDATA[>= ]]> #{areaFrom} or h.cover_area <![CDATA[>= ]]> #{areaFrom})" +
            "</if>" +
            "<if test='areaTo != null'>" +
            " and (h.area <![CDATA[<= ]]> #{areaTo} or h.cover_area <![CDATA[<= ]]> #{areaTo})" +
            "</if>" +
            "</otherwise>"+
            "</choose>"+

            "<choose>" +
            "<when test='priceFrom != null and priceTo != null'>" +
            " and ((h.selling_price <![CDATA[>= ]]> #{priceFrom} and h.selling_price <![CDATA[<= ]]> #{priceTo}) or (h.single_price <![CDATA[>= ]]> #{priceFrom} and h.single_price <![CDATA[<= ]]> #{priceTo}))" +
            "</when>" +
            "<otherwise>" +
            "<if test='priceFrom != null'>" +
            " and (h.selling_price <![CDATA[>= ]]> #{priceFrom} or h.single_price <![CDATA[>= ]]> #{priceFrom})" +
            "</if>" +
            "<if test='priceTo != null'>" +
            " and (h.selling_price <![CDATA[<= ]]> #{priceTo} or h.single_price <![CDATA[<= ]]> #{priceTo})" +
            "</if>" +
            "</otherwise>"+
            "</choose>"
            +
            "<if test='keyword != null'>" +
            " and (h.title like concat('%','${keyword}','%') or h.city like concat('%','${keyword}','%') or h.county like concat('%','${keyword}','%') or h.town like concat('%','${keyword}','%') or h.street like concat('%','${keyword}','%')" +
            " or h.house_number like concat('%','${keyword}','%') or h.house_edge like concat('%','${keyword}','%'))"+
            "</if>"+
            " order by " +
            "<if test='areaOrder != null'>"+
            "<if test='areaOrder == 1'>h.area,h.cover_area,</if>"+
            "<if test='areaOrder == 2'>h.area desc,h.cover_area desc,</if>"+
            "</if>"+

            "<if test='priceOrder != null'>"+
            "<if test='priceOrder == 1'>h.selling_price,</if>"+
            "<if test='priceOrder == 2'>h.selling_price desc,</if>"+
            "</if>" +
            " top.create_ts desc  )"+
            " union all "+
            "(select h.id as id, h.title as title, h.city as city, " +
            "h.county as county,h.town as town, h.street as street, h.area as area," +
            "h.house_number as houseNumber, h.selling_price as sellingPrice,concat(h.electricity,'KV') as electricity," +
            "(select dict_value from t_common_dict where dict_name='houseType' and dict_code=h.house_type) as houseType, " +
            "(select dict_value from t_common_dict where dict_name='houseUseType' and dict_code=h.house_use_type) as houseUseType, " +
            "(select dict_value from t_common_dict where dict_name='floor' and dict_code=h.floor) as floor, " +
            "(select dict_value from t_common_dict where dict_name='fireControl' and dict_code=h.fire_control) as fireControl, " +
            "(select dict_value from t_common_dict where dict_name='priceType' and dict_code=h.price_type) as priceType, " +
            "h.contacts as contacts,h.phone as phone, h.background as background, h.house_status as houseStatus," +
            "h.sign_contract as signContract,h.cover_area as coverArea,h.house_edge as houseEdge,h.user_id as userId," +
            "h.single_price as singlePrice,h.use_area as useArea,h.create_ts as createTs,u.username as username," +
            "u.portrait as portrait,h.trade as trade,h.top as top," +
            "h.company_name as companyName,h.staff_number as staffNumber,h.tax as tax " +
            " from t_house_info h left join t_user_info u on h.user_id=u.id where h.status=1 and h.top=0 " +
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
            "<if test='houseUseType == null'>"+
            " and (h.house_use_type=3 or h.house_use_type=4) "+
            "</if>"+
            "<if test=\"houseType != null\">"+
            " and h.house_type=#{houseType}"+
            "</if><if test=\"houseUseType != null\">"+
            " and h.house_use_type=#{houseUseType}"+
            "</if><if test=\"floor != null\">"+
            " and h.floor=#{floor}"+
            "</if>"+
            "<choose>" +
            "<when test='areaFrom != null and areaTo != null'>" +
            " and ((h.area <![CDATA[>= ]]> #{areaFrom} and h.area <![CDATA[<= ]]> #{areaTo}) or (h.cover_area <![CDATA[>= ]]> #{areaFrom} and h.cover_area <![CDATA[<= ]]> #{areaTo}))" +
            "</when>" +
            "<otherwise>" +
            "<if test='areaFrom != null'>" +
            " and (h.area <![CDATA[>= ]]> #{areaFrom} or h.cover_area <![CDATA[>= ]]> #{areaFrom})" +
            "</if>" +
            "<if test='areaTo != null'>" +
            " and (h.area <![CDATA[<= ]]> #{areaTo} or h.cover_area <![CDATA[<= ]]> #{areaTo})" +
            "</if>" +
            "</otherwise>"+
            "</choose>"+

            "<choose>" +
            "<when test='priceFrom != null and priceTo != null'>" +
            " and ((h.selling_price <![CDATA[>= ]]> #{priceFrom} and h.selling_price <![CDATA[<= ]]> #{priceTo}) or (h.single_price <![CDATA[>= ]]> #{priceFrom} and h.single_price <![CDATA[<= ]]> #{priceTo}))" +
            "</when>" +
            "<otherwise>" +
            "<if test='priceFrom != null'>" +
            " and (h.selling_price <![CDATA[>= ]]> #{priceFrom} or h.single_price <![CDATA[>= ]]> #{priceFrom})" +
            "</if>" +
            "<if test='priceTo != null'>" +
            " and (h.selling_price <![CDATA[<= ]]> #{priceTo} or h.single_price <![CDATA[<= ]]> #{priceTo})" +
            "</if>" +
            "</otherwise>"+
            "</choose>"
           +
            "<if test='keyword != null'>" +
            " and (h.title like concat('%','${keyword}','%') or h.city like concat('%','${keyword}','%') or h.county like concat('%','${keyword}','%') or h.town like concat('%','${keyword}','%') or h.street like concat('%','${keyword}','%')" +
            " or h.house_number like concat('%','${keyword}','%') or h.house_edge like concat('%','${keyword}','%'))"+
            "</if>"+
            " order by  " +

            "<if test='areaOrder != null'>"+
            "<if test='areaOrder == 1'>h.area,h.cover_area,</if>"+
            "<if test='areaOrder == 2'>h.area desc,h.cover_area desc,</if>"+
            "</if>"+

            "<if test='priceOrder != null'>"+
            "<if test='priceOrder == 1'>h.selling_price,</if>"+
            "<if test='priceOrder == 2'>h.selling_price desc,</if>"+
            "</if>" +
            " h.create_ts desc " +
            ")) tmp order by top desc," +
            "<if test='areaOrder != null'>"+
            "<if test='areaOrder == 1'>area,coverArea,</if>"+
            "<if test='areaOrder == 2'>area desc,coverArea desc,</if>"+
            "</if>"+

            "<if test='priceOrder != null'>"+
            "<if test='priceOrder == 1'>sellingPrice,</if>"+
            "<if test='priceOrder == 2'>sellingPrice desc,</if>"+
            "</if>" +
            " createTs desc" +
            " limit #{from},#{pageSize} "+
            "</script>")
    public List<HouseInfoDomainVo> selectHouseInfoListByCondition(HouseConditionVo houseConditionVo);

    @Select("<script> " +
            "select count(*) " +
            " from t_house_top top left join t_house_info h on top.obj_id=h.id left join t_user_info u on h.user_id=u.id " +
            " where h.status=1 and top.status=1 and top.type='house' " +
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
            "<if test=\"houseType != null\">"+
            " and h.house_type=#{houseType}"+
            "</if><if test=\"houseUseType != null\">"+
            " and h.house_use_type=#{houseUseType}"+
            "</if><if test=\"floor != null\">"+
            " and h.floor=#{floor}"+
            "</if>"+
            "<choose>" +
            "<when test='areaFrom != null and areaTo != null'>" +
            " and ((h.area <![CDATA[>= ]]> #{areaFrom} and h.area <![CDATA[<= ]]> #{areaTo}) or (h.cover_area <![CDATA[>= ]]> #{areaFrom} and h.cover_area <![CDATA[<= ]]> #{areaTo}))" +
            "</when>" +
            "<otherwise>" +
            "<if test='areaFrom != null'>" +
            " and (h.area <![CDATA[>= ]]> #{areaFrom} or h.cover_area <![CDATA[>= ]]> #{areaFrom})" +
            "</if>" +
            "<if test='areaTo != null'>" +
            " and (h.area <![CDATA[<= ]]> #{areaTo} or h.cover_area <![CDATA[<= ]]> #{areaTo})" +
            "</if>" +
            "</otherwise>"+
            "</choose>"+

            "<choose>" +
            "<when test='priceFrom != null and priceTo != null'>" +
            " and ((h.selling_price <![CDATA[>= ]]> #{priceFrom} and h.selling_price <![CDATA[<= ]]> #{priceTo}) or (h.single_price <![CDATA[>= ]]> #{priceFrom} and h.single_price <![CDATA[<= ]]> #{priceTo}))" +
            "</when>" +
            "<otherwise>" +
            "<if test='priceFrom != null'>" +
            " and (h.selling_price <![CDATA[>= ]]> #{priceFrom} or h.single_price <![CDATA[>= ]]> #{priceFrom})" +
            "</if>" +
            "<if test='priceTo != null'>" +
            " and (h.selling_price <![CDATA[<= ]]> #{priceTo} or h.single_price <![CDATA[<= ]]> #{priceTo})" +
            "</if>" +
            "</otherwise>"+
            "</choose>"
            +
            "<if test='keyword != null'>" +
            " and (h.title like concat('%','${keyword}','%') or h.city like concat('%','${keyword}','%') or h.county like concat('%','${keyword}','%') or h.town like concat('%','${keyword}','%') or h.street like concat('%','${keyword}','%')" +
            " or h.house_number like concat('%','${keyword}','%') or h.house_edge like concat('%','${keyword}','%'))"+
            "</if>"+
            " order by top.create_ts desc " +

            "<if test='areaOrder != null'>"+
            "<if test='areaOrder == 1'>,h.area,h.cover_area</if>"+
            "<if test='areaOrder == 2'>,h.area desc,h.cover_area desc</if>"+
            "</if>"+

            "<if test='priceOrder != null'>"+
            "<if test='priceOrder == 1'>,h.selling_price</if>"+
            "<if test='priceOrder == 2'>,h.selling_price desc</if>"+
            "</if>"+
            "</script>")
    public int selectTopHouseInfoListByCondition(HouseConditionVo houseConditionVo);


    @Select("<script> " +
            "select * from ((select h.id as id, h.title as title, h.city as city, " +
            "h.county as county,h.town as town, h.street as street, h.area as area," +
            "h.house_number as houseNumber, h.selling_price as sellingPrice,concat(h.electricity,'KV') as electricity," +
            "(select dict_value from t_common_dict where dict_name='houseType' and dict_code=h.house_type) as houseType, " +
            "(select dict_value from t_common_dict where dict_name='houseUseType' and dict_code=h.house_use_type) as houseUseType, " +
            "(select dict_value from t_common_dict where dict_name='floor' and dict_code=h.floor) as floor, " +
            "(select dict_value from t_common_dict where dict_name='fireControl' and dict_code=h.fire_control) as fireControl, " +
            "(select dict_value from t_common_dict where dict_name='priceType' and dict_code=h.price_type) as priceType, " +
            "h.contacts as contacts,h.phone as phone, h.background as background, h.house_status as houseStatus," +
            "h.sign_contract as signContract,h.cover_area as coverArea,h.house_edge as houseEdge,h.user_id as userId," +
            "h.single_price as singlePrice,h.use_area as useArea,top.create_ts as createTs,u.username as username,u.portrait as portrait,h.trade as trade,1 as top," +
            "h.company_name as companyName,h.staff_number as staffNumber,h.tax as tax " +
            " from t_house_top top left join t_house_info h on top.obj_id=h.id left join t_user_info u on h.user_id=u.id " +
            " where h.status=1 and top.status=1 and top.type='house' " +
            "<if test=\"houseType != null\">"+
            " and h.house_type=#{houseType}"+
            "</if>"+
            " order by top.create_ts desc) " +
            " union all "+
            "(select h.id as id, h.title as title, h.city as city, " +
            "h.county as county,h.town as town, h.street as street, h.area as area," +
            "h.house_number as houseNumber, h.selling_price as sellingPrice,concat(h.electricity,'KV') as electricity," +
            "(select dict_value from t_common_dict where dict_name='houseType' and dict_code=h.house_type) as houseType, " +
            "(select dict_value from t_common_dict where dict_name='houseUseType' and dict_code=h.house_use_type) as houseUseType, " +
            "(select dict_value from t_common_dict where dict_name='floor' and dict_code=h.floor) as floor, " +
            "(select dict_value from t_common_dict where dict_name='fireControl' and dict_code=h.fire_control) as fireControl, " +
            "(select dict_value from t_common_dict where dict_name='priceType' and dict_code=h.price_type) as priceType, " +
            "h.contacts as contacts,h.phone as phone, h.background as background, h.house_status as houseStatus," +
            "h.sign_contract as signContract,h.cover_area as coverArea,h.house_edge as houseEdge,h.user_id as userId," +
            "h.single_price as singlePrice,h.use_area as useArea,h.create_ts as createTs,u.username as username," +
            "u.portrait as portrait,h.trade as trade,top as top," +
            "h.company_name as companyName,h.staff_number as staffNumber,h.tax as tax " +
            " from t_house_info h left join t_user_info u on h.user_id=u.id " +
            " where h.status=1 and h.top=0 and (h.house_use_type=3 or h.house_use_type=4)" +
            "<if test=\"houseType != null\">"+
            " and h.house_type=#{houseType}"+
            "</if>"+
            " order by h.create_ts desc)) tmp order by createTs desc" +
            " limit #{from},#{pageSize} " +
            "</script>")
    public List<HouseInfoDomainVo> selectHomeHouseListByCondition(HouseConditionVo houseConditionVo);

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
            "h.single_price as singlePrice,h.use_area as useArea,top.create_ts as createTs,u.username as username,u.portrait as portrait,h.trade as trade,1 as top," +
            "h.company_name as companyName,h.staff_number as staffNumber,h.tax as tax " +
            " from t_house_top top left join t_house_info h on top.obj_id=h.id left join t_user_info u on h.user_id=u.id " +
            " where h.status=1 and top.status=1 and top.type='house' " +
            "<if test=\"houseType != null\">"+
            " and h.house_type=#{houseType}"+
            "</if>"+
            " order by top.create_ts desc " +

            "</script>")
    public List<HouseInfoDomainVo> selectTopHomeHouseListByCondition(HouseConditionVo houseConditionVo);

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
            "h.single_price as singlePrice,h.use_area as useArea,h.create_ts as createTs,u.username as username," +
            "u.portrait as portrait,h.trade as trade,h.expire_date as expireDate,h.top as top" +
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
            "</if>"+
            "<choose>" +
            "<when test='areaFrom != null and areaTo != null'>" +
            " and ((h.area <![CDATA[>= ]]> #{areaFrom} and h.area <![CDATA[<= ]]> #{areaTo}) or (h.cover_area <![CDATA[>= ]]> #{areaFrom} and h.cover_area <![CDATA[<= ]]> #{areaTo}))" +
            "</when>" +
            "<otherwise>" +
            "<if test='areaFrom != null'>" +
            " and (h.area <![CDATA[>= ]]> #{areaFrom} or h.cover_area <![CDATA[>= ]]> #{areaFrom})" +
            "</if>" +
            "<if test='areaTo != null'>" +
            " and (h.area <![CDATA[<= ]]> #{areaTo} or h.cover_area <![CDATA[<= ]]> #{areaTo})" +
            "</if>" +
            "</otherwise>"+
            "</choose>"+
            "<choose>" +
            "<when test='priceFrom != null and priceTo != null'>" +
            " and ((h.selling_price <![CDATA[>= ]]> #{priceFrom} and h.selling_price <![CDATA[<= ]]> #{priceTo}) or (h.single_price <![CDATA[>= ]]> #{priceFrom} and h.single_price <![CDATA[<= ]]> #{priceTo}))" +
            "</when>" +
            "<otherwise>" +
            "<if test='priceFrom != null'>" +
            " and (h.selling_price <![CDATA[>= ]]> #{priceFrom} or h.single_price <![CDATA[>= ]]> #{priceFrom})" +
            "</if>" +
            "<if test='priceTo != null'>" +
            " and (h.selling_price <![CDATA[<= ]]> #{priceTo} or h.single_price <![CDATA[<= ]]> #{priceTo})" +
            "</if>" +
            "</otherwise>"+
            "</choose>"+
            " order by h.house_status asc,h.create_ts desc " +
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
    public List<HouseInfoDomainVo> selectUserHouseInfoListByCondition(HouseConditionVo houseConditionVo);

    @Select("<script> " +
            "select count(h.id) " +
            " from t_house_info h where h.status=1 and h.top=0 " +
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
            "<if test=\"houseType != null\">"+
            " and h.house_type=#{houseType}"+
            "</if><if test=\"houseUseType != null\">"+
            " and h.house_use_type=#{houseUseType}"+
            "</if><if test=\"floor != null\">"+
            " and h.floor=#{floor}"+
            "</if>"+
            "<choose>" +
            "<when test='areaFrom != null and areaTo != null'>" +
            " and ((h.area <![CDATA[>= ]]> #{areaFrom} and h.area <![CDATA[<= ]]> #{areaTo}) or (h.cover_area <![CDATA[>= ]]> #{areaFrom} and h.cover_area <![CDATA[<= ]]> #{areaTo}))" +
            "</when>" +
            "<otherwise>" +
            "<if test='areaFrom != null'>" +
            " and (h.area <![CDATA[>= ]]> #{areaFrom} or h.cover_area <![CDATA[>= ]]> #{areaFrom})" +
            "</if>" +
            "<if test='areaTo != null'>" +
            " and (h.area <![CDATA[<= ]]> #{areaTo} or h.cover_area <![CDATA[<= ]]> #{areaTo})" +
            "</if>" +
            "</otherwise>"+
            "</choose>"+
            "<choose>" +
            "<when test='priceFrom != null and priceTo != null'>" +
            " and ((h.selling_price <![CDATA[>= ]]> #{priceFrom} and h.selling_price <![CDATA[<= ]]> #{priceTo}) or (h.single_price <![CDATA[>= ]]> #{priceFrom} and h.single_price <![CDATA[<= ]]> #{priceTo}))" +
            "</when>" +
            "<otherwise>" +
            "<if test='priceFrom != null'>" +
            " and (h.selling_price <![CDATA[>= ]]> #{priceFrom} or h.single_price <![CDATA[>= ]]> #{priceFrom})" +
            "</if>" +
            "<if test='priceTo != null'>" +
            " and (h.selling_price <![CDATA[<= ]]> #{priceTo} or h.single_price <![CDATA[<= ]]> #{priceTo})" +
            "</if>" +
            "</otherwise>"+
            "</choose>"+
             "<if test=\"keyword != null\">" +
            " and (title like concat('%','${keyword}','%') or city like concat('%','${keyword}','%') or county like concat('%','${keyword}','%') or town like concat('%','${keyword}','%') or street like concat('%','${keyword}','%')" +
            " or house_number like concat('%','${keyword}','%') or house_edge like concat('%','${keyword}','%'))"+
            "</if>"+
            "</script>")
    int countUserHouseInfoListByCondition(HouseConditionVo houseConditionVo);

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
            "h.single_price as singlePrice,h.use_area as useArea,h.create_ts as createTs,u.username as username," +
            "u.portrait as portrait,h.trade as trade,h.expire_date as expireDate,h.top as top" +
            " from t_house_info h left join t_user_info u on h.user_id=u.id where h.status=1 "+
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
            "</if>"+
            "<choose>" +
            "<when test='areaFrom != null and areaTo != null'>" +
            " and ((h.area <![CDATA[>= ]]> #{areaFrom} and h.area <![CDATA[<= ]]> #{areaTo}) or (h.cover_area <![CDATA[>= ]]> #{areaFrom} and h.cover_area <![CDATA[<= ]]> #{areaTo}))" +
            "</when>" +
            "<otherwise>" +
            "<if test='areaFrom != null'>" +
            " and (h.area <![CDATA[>= ]]> #{areaFrom} or h.cover_area <![CDATA[>= ]]> #{areaFrom})" +
            "</if>" +
            "<if test='areaTo != null'>" +
            " and (h.area <![CDATA[<= ]]> #{areaTo} or h.cover_area <![CDATA[<= ]]> #{areaTo})" +
            "</if>" +
            "</otherwise>"+
            "</choose>"+
            "<choose>" +
            "<when test='priceFrom != null and priceTo != null'>" +
            " and ((h.selling_price <![CDATA[>= ]]> #{priceFrom} and h.selling_price <![CDATA[<= ]]> #{priceTo}) or (h.single_price <![CDATA[>= ]]> #{priceFrom} and h.single_price <![CDATA[<= ]]> #{priceTo}))" +
            "</when>" +
            "<otherwise>" +
            "<if test='priceFrom != null'>" +
            " and (h.selling_price <![CDATA[>= ]]> #{priceFrom} or h.single_price <![CDATA[>= ]]> #{priceFrom})" +
            "</if>" +
            "<if test='priceTo != null'>" +
            " and (h.selling_price <![CDATA[<= ]]> #{priceTo} or h.single_price <![CDATA[<= ]]> #{priceTo})" +
            "</if>" +

            "</otherwise>"+
            "</choose>"+
            "<if test='keyword != null'>"+
            " and (h.title like concat('%','${keyword}','%') or h.city like concat('%','${keyword}','%') or h.county like concat('%','${keyword}','%') or h.town like concat('%','${keyword}','%') or h.street like concat('%','${keyword}','%')" +
            " or h.house_number like concat('%','${keyword}','%') or h.house_edge like concat('%','${keyword}','%'))"+
            "</if>"+
            " order by h.house_status asc,h.create_ts desc " +
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
            "h.single_price as singlePrice,h.use_area as useArea,h.create_ts as createTs,u.username as username," +
            "u.portrait as portrait,h.trade as trade,h.expire_date as expireDate" +
            " from t_house_info h left join t_user_info u on h.user_id=u.id where h.status=1 "+
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
            "</if>"+
            "<choose>" +
            "<when test='areaFrom != null and areaTo != null'>" +
            " and ((h.area <![CDATA[>= ]]> #{areaFrom} and h.area <![CDATA[<= ]]> #{areaTo}) or (h.cover_area <![CDATA[>= ]]> #{areaFrom} and h.cover_area <![CDATA[<= ]]> #{areaTo}))" +
            "</when>" +
            "<otherwise>" +
            "<if test='areaFrom != null'>" +
            " and (h.area <![CDATA[>= ]]> #{areaFrom} or h.cover_area <![CDATA[>= ]]> #{areaFrom})" +
            "</if>" +
            "<if test='areaTo != null'>" +
            " and (h.area <![CDATA[<= ]]> #{areaTo} or h.cover_area <![CDATA[<= ]]> #{areaTo})" +
            "</if>" +
            "</otherwise>"+
            "</choose>"+
            "<choose>" +
            "<when test='priceFrom != null and priceTo != null'>" +
            " and ((h.selling_price <![CDATA[>= ]]> #{priceFrom} and h.selling_price <![CDATA[<= ]]> #{priceTo}) or (h.single_price <![CDATA[>= ]]> #{priceFrom} and h.single_price <![CDATA[<= ]]> #{priceTo}))" +
            "</when>" +
            "<otherwise>" +
            "<if test='priceFrom != null'>" +
            " and (h.selling_price <![CDATA[>= ]]> #{priceFrom} or h.single_price <![CDATA[>= ]]> #{priceFrom})" +
            "</if>" +
            "<if test='priceTo != null'>" +
            " and (h.selling_price <![CDATA[<= ]]> #{priceTo} or h.single_price <![CDATA[<= ]]> #{priceTo})" +
            "</if>" +
            "</otherwise>"+
            "</choose>"+
            "<if test='keyword != null'>"+
            " and (h.title like concat('%','${keyword}','%') or h.city like concat('%','${keyword}','%') or h.county like concat('%','${keyword}','%') or h.town like concat('%','${keyword}','%') or h.street like concat('%','${keyword}','%')" +
            " or h.house_number like concat('%','${keyword}','%') or h.house_edge like concat('%','${keyword}','%'))"+
            "</if>"+
            " order by h.house_status asc,h.create_ts desc " +
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
            "single_price as singlePrice,use_area as useArea,create_ts as createTs,trade as trade " +
            " from t_house_info where id >= " +
            "(select floor(rand() * (select max(id) from t_house_info where status=1))) " +
            " and (house_use_type=3 or house_use_type=4) and status=1 order by rand() limit 3")
    List<HouseInfoDomainVo> selectHouseInfoListByLike();


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
            "single_price as singlePrice,use_area as useArea,create_ts as createTs,trade as trade " +
            " from t_house_info where id >= " +
            "(select floor(rand() * (select max(id) from t_house_info where status=1))) " +
            " and (house_use_type=3 or house_use_type=4) and status=1 and county=#{county} order by rand() limit 3")
    List<HouseInfoDomainVo> selectHouseInfoListByDetailLike(String county);

    @Select("select count(id) from t_house_info where user_id=#{userId} and status=1")
    int selectHouseCountByUserId(Long userId);

    @Select("select count(id) from t_house_info where user_id=#{userId} and house_type=#{houseType} and status=1")
    int selectHouseCountByUserIdAndHouseUseType(@Param("userId") Long userId,@Param("houseType") Integer houseType);

    @Select("select count(id) from t_house_info where status=1")
    int selectAllHouseCount();

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
            "single_price as singlePrice,use_area as useArea,create_ts as createTs,trade as trade " +
            " from t_house_info where user_id=#{userId} " +
            "and status=1 and (house_use_type =3 or house_use_type =4) order by rand() limit 5")
    List<HouseInfoDomainVo> selectHouseInfoListByRecommend(@Param("userId") Long userId);

    @Select("select id as id, user_id as userId "+
            " from t_house_info where (area between #{fromArea} and #{toArea}) " +
            "or (city=#{city} and county=#{county} and town=#{town}) or house_type=#{houseType} " +
            "or house_use_type=#{houseUseType} and status=1" )
    List<HouseInfoDomainVo> selectHouseInfoListByMatch(@Param("fromArea") Integer fromArea,@Param("toArea") Integer toArea,
                                                       @Param("city")String city,@Param("county")String county,@Param("town")String town,
                                                       @Param("houseType")Integer houseType,@Param("houseUseType")Integer houseUseType);

    @Select("select h.id as id, h.title as title, h.city as city, " +
            "h.county as county,h.town as town, h.street as street, h.area as area," +
            "h.house_number as houseNumber, h.selling_price as sellingPrice,concat(h.electricity,'KV') as electricity," +
            "(select dict_value from t_common_dict where dict_name='houseType' and dict_code=h.house_type) as houseType, " +
            "(select dict_value from t_common_dict where dict_name='houseUseType' and dict_code=h.house_use_type) as houseUseType, " +
            "(select dict_value from t_common_dict where dict_name='floor' and dict_code=h.floor) as floor, " +
            "(select dict_value from t_common_dict where dict_name='fireControl' and dict_code=h.fire_control) as fireControl, " +
            "(select dict_value from t_common_dict where dict_name='priceType' and dict_code=h.price_type) as priceType, " +
            "h.contacts as contacts,h.phone as phone, h.background as background, h.house_status as houseStatus," +
            "h.sign_contract as signContract,h.cover_area as coverArea,h.house_edge as houseEdge,h.user_id as userId," +
            "h.single_price as singlePrice,h.use_area as useArea,h.create_ts as createTs,h.trade as trade,u.portrait as portrait " +
            " from t_house_info h left join t_user_info u on h.user_id=u.id where h.id=#{houseId} " +
            "and h.status=1")
    HouseInfoDomainVo selectHouseInfoListById(@Param("houseId") Long houseId);

    //判断是否有发布同区域的房源
    @Select("<script>" +
            "select id from t_house_info where status=1 and city=#{houseInfoDomain.city} and county=#{houseInfoDomain.county}" +
            " and street=#{houseInfoDomain.street} and house_number=#{houseInfoDomain.houseNumber} " +
            "<foreach collection='userIds' item='userId' index='index' open=' and user_id in (' close=')' separator=','>" +
            "#{userId}" +
            "</foreach>"+
            "</script>")
    List<Long> selectHouseByRegionAndUserId(@Param("houseInfoDomain") HouseInfoDomain houseInfoDomain,@Param("userIds") List<Long> userIds);

    //修改判断是否有发布同区域的房源
    @Select("<script>" +
            "select id from t_house_info where status=1 and city=#{houseInfoDomain.city} and county=#{houseInfoDomain.county}" +
            " and street=#{houseInfoDomain.street} and house_number=#{houseInfoDomain.houseNumber} and id != #{houseInfoDomain.id}" +
            "<foreach collection='userIds' item='userId' index='index' open=' and user_id in (' close=')' separator=','>" +
            "#{userId}" +
            "</foreach>"+
            "</script>")
    List<Long> selectHouseByRegionAndUserIdAndHouseId(@Param("houseInfoDomain") HouseInfoDomain houseInfoDomain,@Param("userIds") List<Long> userIds);

    @Select("select u.id as userId,u.username as username,u.phone as phone,u.portrait as portrait,a.company_name as companyName " +
            "from t_user_info u left join t_house_info h on u.id=h.user_id left join t_apply_broker_info a on a.user_id=h.user_id" +
            " where h.city=#{city} and h.county=#{county} " +
            " and h.street=#{street} and h.house_number=#{houseNumber} and h.status=1 and u.status=1 order by h.id limit 3")
    List<UserBrokerVo> selectUserByHouseInfo(HouseInfoDetailVo houseInfoDetailVo);

    @Select("select concact(city,county,town) from t_house_info limit 1")
    String selectRegionFromHouseByUserId(Long userId);

    /**************************管理员项目使用的房源********************************************/
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
            "h.single_price as singlePrice,h.use_area as useArea,h.create_ts as createTs," +
            "h.trade as trade,h.expire_date as expireDate,h.status as status," +
            "h.company_name as companyName,h.staff_number as staffNumber,h.tax as tax " +
            " from t_house_info h  where (h.status=1 or h.status=0) "+
            "<if test='keyword != null'>"+
            " and (h.title like concat('%','${keyword}','%') or h.city like concat('%','${keyword}','%') or h.county like concat('%','${keyword}','%') or h.town like concat('%','${keyword}','%') or h.street like concat('%','${keyword}','%')" +
            " or h.house_number like concat('%','${keyword}','%') or h.house_edge like concat('%','${keyword}','%'))"+
            "</if>"+
            " order by h.status desc,h.create_ts desc " +
            " limit #{from},#{limit}"+
            "</script>")
    public List<HouseInfoDomainVo> selectAdminHouseInfoList(HouseAdminConditionVo houseConditionVo);

    @Select("<script>"+
            "select count(h.id)" +
            " from t_house_info h  where (h.status=1 or h.status=0) "+
            "<if test='keyword != null'>"+
            " and (h.title like concat('%','${keyword}','%') or h.city like concat('%','${keyword}','%') or h.county like concat('%','${keyword}','%') or h.town like concat('%','${keyword}','%') or h.street like concat('%','${keyword}','%')" +
            " or h.house_number like concat('%','${keyword}','%') or h.house_edge like concat('%','${keyword}','%'))"+
            "</if></script>")
    public int countAdminHouseInfo(HouseAdminConditionVo houseConditionVo);

    /**
     * 不同房源类型的数量
     */
    @Select("select count(*) from t_house_info where status=1 and house_type=#{houseType}")
    public int countHouseByHouseType(int houseType);

    /*******************************************************************************************/

}
