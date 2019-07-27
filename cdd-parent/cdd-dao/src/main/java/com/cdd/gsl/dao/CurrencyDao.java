package com.cdd.gsl.dao;

import com.cdd.gsl.domain.CurrencyInfoDomain;
import com.cdd.gsl.vo.CurrencyVo;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface CurrencyDao {
    @Select("select id as id,integral as integral,duration as duration,month as month from t_currency_info where status=1")
    List<CurrencyVo> currencyList();

    @Select("select id as id,integral as integral,duration as duration,month as month " +
            "from t_currency_info where status=1 and id=#{id}")
    List<CurrencyVo> currencyListById(Long id);

}
