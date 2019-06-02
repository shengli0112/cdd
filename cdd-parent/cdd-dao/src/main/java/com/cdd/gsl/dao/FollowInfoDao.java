package com.cdd.gsl.dao;

import com.cdd.gsl.vo.*;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface FollowInfoDao {
    @Delete("delete from t_follow_info where user_id=#{userId} and id in (${followIds})")
    public void deleteFollowInfos(@Param("userId")Long userId,@Param("followIds")String followIds);

    @Select(
            "select f.id as followId,h.id as houseId,h.title as title, h.city as city," +
            "h.county as county,h.town as town, h.street as street, h.area as area," +
            "h.house_number as houseNumber, h.selling_price as sellingPrice,concat(h.electricity,'KV') as electricity,"+
            "(select dict_value from t_common_dict where dict_name='houseType' and dict_code=h.house_type) as houseType, " +
            "(select dict_value from t_common_dict where dict_name='houseUseType' and dict_code=h.house_use_type) as houseUseType, " +
            "(select dict_value from t_common_dict where dict_name='floor' and dict_code=h.floor) as floor, " +
            "(select dict_value from t_common_dict where dict_name='fireControl' and dict_code=h.fire_control) as fireControl, " +
            "(select dict_value from t_common_dict where dict_name='priceType' and dict_code=h.price_type) as priceType, " +
            "h.contacts as contacts,h.phone as phone, h.background as background, h.house_status as houseStatus," +
            "h.sign_contract as signContract,h.cover_area as coverArea,h.house_edge as houseEdge,h.user_id as userId," +
            "h.single_price as singlePrice,h.use_area as useArea,h.create_ts as createTs,u.username as username,u.portrait as portrait" +
            " from t_user_info u left join t_house_info h on u.id=h.user_id " +
                    "left join t_follow_info f on h.id=f.follow_id where h.status=1 and f.user_id=#{userId} and f.follow_type=#{followType}" +
                    " order by f.create_ts desc limit #{from},#{pageSize} ")
    public List<FollowHouseVo> findFollowHouse(FollowConditionVo followConditionVo);

    @Select("select f.id as followId,s.id as sellParkId,s.city as city,s.county as county,s.address as address,s.park_name as parkName," +
            "s.total_area as totalArea,s.total_price as totalPrice,s.tag as tag,s.background as background"+
            " from t_follow_info f left join t_sell_park_info s on f.follow_id=s.id" +
            " where s.status=1 and f.follow_type=#{followType} and f.user_id=#{userId}" +
            " order by f.create_ts desc limit #{from},#{pageSize} ")
    public List<FollowSellParkVo> findFollowSellPark(FollowConditionVo followConditionVo);

    @Select("select f.id as followId,s.id as id,s.city as city,s.county as county,s.address as address,s.park_name as parkName," +
            "s.total_area as totalArea,s.unit_price as unitPrice,"+
            "s.tag as tag,(select dict_value from t_common_dict where dict_name='priceType' and dict_code=s.price_type) as priceType,s.background as background " +
            " from t_follow_info f left join t_lease_park_info s on f.follow_id=s.id" +
            " where s.status=1 and f.follow_type=#{followType} and f.user_id=#{userId}" +
            " order by f.create_ts desc limit #{from},#{pageSize} ")
    public List<FollowLeaseParkVo> findFollowLeasePark(FollowConditionVo followConditionVo);

    @Select("select id from t_follow_info where user_id=#{userId} and follow_id=#{followId} and follow_type=#{followType}")
    List<Long> isFollow(IsFollowVo isFollowVo);
}
