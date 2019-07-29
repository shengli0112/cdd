package com.cdd.gsl.filter;

import com.alibaba.fastjson.JSONObject;
import com.cdd.gsl.common.constants.CddConstant;
import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.dao.AdminTicketDomainMapper;
import com.cdd.gsl.dao.UrlInfoDao;
import com.cdd.gsl.dao.UserTicketDomainMapper;
import com.cdd.gsl.domain.AdminTicketDomain;
import com.cdd.gsl.domain.AdminTicketDomainExample;
import com.cdd.gsl.domain.UserTicketDomain;
import com.cdd.gsl.domain.UserTicketDomainExample;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@Transactional
public class ClientRequestFilter extends OncePerRequestFilter implements Filter {
	private static final Logger logger = LoggerFactory.getLogger(ClientRequestFilter.class);

	@Autowired
	private AdminTicketDomainMapper adminTicketDomainMapper;

	@Autowired
	private UrlInfoDao urlInfoDao;

	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		logger.info("ClientRequestFilter login info userId --{} ,token --{}",request.getHeader("X-Cdd-Request-Userid"),request.getHeader("X-Cdd-Request-Token"));
		response.setCharacterEncoding("utf-8");
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
		response.setHeader("Access-Control-Allow-Headers","Content-Type,X-Token");
		String uri = request.getRequestURI();
		String regex = "[0-9]+";
		String method = request.getMethod();

		if ((uri.equals("/admin/user/login")) || "OPTIONS".equals(method)) {
			filterChain.doFilter(request, response);
		} else {
			String token = request.getHeader("X-Token");
			/*if (request.getHeader("Access-Control-Request-Method") != null && "OPTIONS".equals(method)) {
				filterChain.doFilter(request, response);
			} else {



			}*/

			if (Strings.isNullOrEmpty(token)) {
				logger.error(
						"[**Auth Failure**] Missing params: userId：null token:null");

				//把返回值输出到客户端
				ServletOutputStream out = response.getOutputStream();
				CommonResult commonResult = new CommonResult();
				commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
				commonResult.setMessage("token为空没有权限");
				out.write(JSONObject.toJSONString(commonResult).getBytes("utf-8"));
				out.flush();
				return;
			}else{
				AdminTicketDomainExample userTicketDomainExample = new AdminTicketDomainExample();
				userTicketDomainExample.createCriteria().andTokenEqualTo(token);
				List<AdminTicketDomain> adminTicketDomainList = adminTicketDomainMapper.selectByExample(userTicketDomainExample);
				if(adminTicketDomainList != null && adminTicketDomainList.size() > 0){
					filterChain.doFilter(request, response);
				}else{
					//把返回值输出到客户端
					ServletOutputStream out = response.getOutputStream();
					CommonResult commonResult = new CommonResult();
					commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
					commonResult.setMessage("token验证失败");
					out.write(JSONObject.toJSONString(commonResult).getBytes("utf-8"));
					out.flush();
				}
			}

		}
	}
	
}
