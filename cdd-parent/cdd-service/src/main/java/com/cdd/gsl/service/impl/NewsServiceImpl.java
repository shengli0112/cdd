package com.cdd.gsl.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cdd.gsl.admin.NewsAdminConditionVo;
import com.cdd.gsl.common.constants.CddConstant;
import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.dao.NewsInfoDao;
import com.cdd.gsl.dao.NewsInfoDomainMapper;
import com.cdd.gsl.domain.NewsInfoDomain;
import com.cdd.gsl.domain.NewsInfoDomainExample;
import com.cdd.gsl.service.NewsService;
import com.cdd.gsl.vo.NewsInfoVo;
import com.cdd.gsl.vo.NewsParamVo;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {

    private Logger logger = LoggerFactory.getLogger(NewsServiceImpl.class);

    @Autowired
    private NewsInfoDomainMapper newsInfoDomainMapper;

    @Autowired
    private NewsInfoDao newsInfoDao;

    @Override
    public CommonResult createNews(NewsInfoDomain newsInfoDomain) {
        CommonResult commonResult = new CommonResult();
        try{
            //TODO 字段校验
           if(newsInfoDomain != null){
               newsInfoDomain.setCreateTs(new Date());
               newsInfoDomain.setUpdateTs(new Date());
               newsInfoDomainMapper.insertSelective(newsInfoDomain);
               commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
               commonResult.setMessage("创建成功");
           } else {
               commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
               commonResult.setMessage("参数不正确");
           }
        }catch (Exception e){
            logger.error("NewsServiceImpl createNews error");
            e.printStackTrace();
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("服务器异常");
        }
        return commonResult;
    }

    @Override
    public CommonResult findNewsList(NewsAdminConditionVo newsAdminConditionVo) {

        CommonResult commonResult = new CommonResult();
        try{
            List<NewsInfoDomain> newsInfoDomainList = newsInfoDao.findNewsList(newsAdminConditionVo);
            int total = newsInfoDao.findNewsCount(newsAdminConditionVo);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("newsList",newsInfoDomainList);
            jsonObject.put("total",total);
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("查询成功");
            commonResult.setData(jsonObject);
        }catch (Exception e){
            logger.error("NewsServiceImpl createNews error");
            e.printStackTrace();
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("服务器异常");
        }
        return commonResult;
    }

    @Override
    public CommonResult updateNewsStatus(NewsInfoDomain newsInfoDomain) {
        CommonResult commonResult = new CommonResult();
        newsInfoDao.updateNewsStatus(newsInfoDomain);
        commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
        commonResult.setMessage("更新成功");
        return commonResult;
    }

    @Override
    public CommonResult newsList(NewsParamVo newsParamVo) {
        CommonResult commonResult = new CommonResult();
        try{
            List<NewsInfoVo> newsInfoDomainList = newsInfoDao.newsList(newsParamVo);
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("查询成功");
            commonResult.setData(newsInfoDomainList);
        }catch (Exception e){
            logger.error("NewsServiceImpl createNews error");
            e.printStackTrace();
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("服务器异常");
        }
        return commonResult;
    }

    @Override
    public CommonResult findNewsDetail(Long newsId) {
        CommonResult commonResult = new CommonResult();
        try{
            List<NewsInfoVo> newsInfoDomainList = newsInfoDao.findNewsById(newsId);
            NewsInfoVo newsInfoVo = null;
            if(!CollectionUtils.isEmpty(newsInfoDomainList)){
                newsInfoVo = newsInfoDomainList.get(0);
            }
            commonResult.setFlag(CddConstant.RESULT_SUCCESS_CODE);
            commonResult.setMessage("查询成功");
            commonResult.setData(newsInfoVo);
        }catch (Exception e){
            logger.error("NewsServiceImpl createNews error");
            e.printStackTrace();
            commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
            commonResult.setMessage("服务器异常");
        }
        return commonResult;
    }
}
