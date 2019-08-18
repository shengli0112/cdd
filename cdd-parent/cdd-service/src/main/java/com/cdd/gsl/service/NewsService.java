package com.cdd.gsl.service;

import com.cdd.gsl.admin.NewsAdminConditionVo;
import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.domain.NewsInfoDomain;

public interface NewsService {
    CommonResult createNews(NewsInfoDomain newsInfoDomain);

    public CommonResult findNewsList(NewsAdminConditionVo newsAdminConditionVo);

    CommonResult updateNewsStatus(NewsInfoDomain newsInfoDomain);
}
