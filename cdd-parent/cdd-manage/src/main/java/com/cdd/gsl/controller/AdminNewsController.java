package com.cdd.gsl.controller;

import com.alibaba.fastjson.JSONObject;
import com.cdd.gsl.admin.HouseAdminConditionVo;
import com.cdd.gsl.admin.NewsAdminConditionVo;
import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.domain.HouseInfoDomain;
import com.cdd.gsl.domain.NewsInfoDomain;
import com.cdd.gsl.service.AdminService;
import com.cdd.gsl.service.HouseService;
import com.cdd.gsl.service.NewsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 多多头条
 */
@RestController
@RequestMapping("news")
public class AdminNewsController {

    private Logger logger = LoggerFactory.getLogger(AdminNewsController.class);

    @Autowired
    private NewsService newsService;


    /**
     * 创建新闻
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/createNews")
    public CommonResult createNews(@RequestBody NewsInfoDomain newsInfoDomain) throws Exception {
        logger.info("AdminNewsController createNews start");
        return newsService.createNews(newsInfoDomain);
    }

    /**
     * 新闻列表
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/findNewsList")
    public CommonResult findNewsList(NewsAdminConditionVo newsAdminConditionVo) throws Exception {
        logger.info("AdminNewsController findNewsList start");
        return newsService.findNewsList(newsAdminConditionVo);
    }

    /**
     * 删除新闻
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/deleteNews")
    public CommonResult deleteNews(@RequestBody JSONObject json) throws Exception {
        logger.info("AdminNewsController deleteNews start");
        CommonResult commonResult = new CommonResult();
        if(json != null){
            NewsInfoDomain newsInfoDomain = new NewsInfoDomain();
            newsInfoDomain.setId(json.getLong("newsId"));
            newsInfoDomain.setStatus(0);
            newsInfoDomain.setUpdateTs(new Date());
            newsService.updateNewsStatus(newsInfoDomain);
            commonResult.setFlag(1);
            commonResult.setMessage("删除成功");
        }else{
            commonResult.setFlag(0);
            commonResult.setMessage("删除失败，参数不正确");
        }
        return commonResult;
    }

    /**
     * 恢复新闻
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/recoverNews")
    public CommonResult recoverNews(@RequestBody JSONObject json) throws Exception {
        logger.info("AdminNewsController deleteNews start");
        CommonResult commonResult = new CommonResult();
        if(json != null){
            NewsInfoDomain newsInfoDomain = new NewsInfoDomain();
            newsInfoDomain.setId(json.getLong("newsId"));
            newsInfoDomain.setStatus(1);
            newsInfoDomain.setUpdateTs(new Date());
            newsService.updateNewsStatus(newsInfoDomain);
            commonResult.setFlag(1);
            commonResult.setMessage("恢复成功");
        }else{
            commonResult.setFlag(0);
            commonResult.setMessage("恢复失败，参数不正确");
        }
        return commonResult;
    }

}
