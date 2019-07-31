package com.cdd.gsl.dao;

import com.cdd.gsl.vo.UserCurrencyVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserCurrencyDao {
    @Select("select id as id,user_id as userId,currency_id as currencyId,create_ts as createTs " +
            "from t_user_currency_mapping where user_id=#{userId} order by create_ts desc")
    List<UserCurrencyVo> findUserCurrencyByUserId(Long userId);
}
