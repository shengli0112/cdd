package com.cdd.gsl.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.cdd.gsl.common.constants.CddConstant;
import com.cdd.gsl.common.result.CommonResult;
import com.cdd.gsl.common.util.EncryptionUtil;
import com.cdd.gsl.dao.UrlInfoDao;
import com.cdd.gsl.dao.UrlInfoDomainMapper;
import com.cdd.gsl.dao.UserInfoDao;
import com.cdd.gsl.dao.UserTicketDomainMapper;
import com.cdd.gsl.domain.UrlInfoDomain;
import com.cdd.gsl.domain.UrlInfoDomainExample;
import com.cdd.gsl.domain.UserTicketDomain;
import com.cdd.gsl.domain.UserTicketDomainExample;
import com.cdd.gsl.vo.SingleUserInfoVo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.filter.OncePerRequestFilter;


import com.google.common.base.Strings;



@Transactional
public class ClientRequestFilter extends OncePerRequestFilter implements Filter {
	private static final Logger logger = LoggerFactory.getLogger(ClientRequestFilter.class);

	@Autowired
	private UserTicketDomainMapper userTicketDomainMapper;

	@Autowired
	private UrlInfoDao urlInfoDao;

	@Autowired
	private UserInfoDao userInfoDao;

	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		logger.info("ClientRequestFilter login info userId --{} ,token --{}",request.getHeader("X-Cdd-Request-Userid"),request.getHeader("X-Cdd-Request-Token"));
		response.setCharacterEncoding("utf-8");
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
		response.setHeader("Access-Control-Allow-Headers",
				"authorization, content-type, x-tella-request-appversion, x-tella-request-provider, x-tella-request-timestamp, x-tella-request-token"
						+ ", x-cdd-request-userid, x-tella-request-device, x-tella-request-usertype");
		String uri = request.getRequestURI();
		String regex = "[0-9]+";
		String method = request.getMethod();
		List<String> urlInfoDomains = urlInfoDao.selectUrlList();
		if (urlInfoDomains.contains(uri)) {
			filterChain.doFilter(request, response);
		} else {
			if (request.getHeader("Access-Control-Request-Method") != null && "OPTIONS".equals(method)) {
				filterChain.doFilter(request, response);
			} else {
				String userId = request.getHeader("X-Cdd-Request-Userid") != null
						? request.getHeader("X-Cdd-Request-Userid") : "";

				String token = request.getHeader("X-Cdd-Request-Token") != null
						? request.getHeader("X-Cdd-Request-Token") : "";


				if ((Strings.isNullOrEmpty(userId))
						|| (Strings.isNullOrEmpty(token))) {
					logger.error(
							"[**Auth Failure**] Missing params: userId：null token:null");

					//把返回值输出到客户端
					ServletOutputStream out = response.getOutputStream();
					CommonResult commonResult = new CommonResult();
					commonResult.setFlag(CddConstant.RESULT_FAILD_CODE);
					commonResult.setMessage("参数不完整");
					out.write(JSONObject.toJSONString(commonResult).getBytes("utf-8"));
					out.flush();
					return;
				}else{

					UserTicketDomainExample userTicketDomainExample = new UserTicketDomainExample();
					userTicketDomainExample.createCriteria().andTokenEqualTo(token).andUserIdEqualTo(Long.parseLong(userId));
					List<UserTicketDomain> userTicketDomainList = userTicketDomainMapper.selectByExample(userTicketDomainExample);
					SingleUserInfoVo singleUserInfoVo = userInfoDao.findUserInfoById(Long.parseLong(userId));
					if(singleUserInfoVo != null){
						if(userTicketDomainList != null && userTicketDomainList.size() > 0){
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
					}else{
						//把返回值输出到客户端
						ServletOutputStream out = response.getOutputStream();
						CommonResult commonResult = new CommonResult();
						commonResult.setFlag(2);
						commonResult.setMessage("该用户已删除");
						out.write(JSONObject.toJSONString(commonResult).getBytes("utf-8"));
						out.flush();
					}

				}

			}

		}
	}
	
}
