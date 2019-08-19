package com.cdd.gsl.controller;

import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.service.NewsService;
import com.cdd.gsl.vo.NewsParamVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @RequestMapping("/newsList")
    public CommonResult newsList(NewsParamVo newsParamVo){
        CommonResult commonResult = newsService.newsList(newsParamVo);
        return commonResult;
    }

    @RequestMapping("/findNewsDetail")
    public CommonResult findNewsDetail(Long newsId){
        CommonResult commonResult = newsService.findNewsDetail(newsId);
        return commonResult;
    }
}
