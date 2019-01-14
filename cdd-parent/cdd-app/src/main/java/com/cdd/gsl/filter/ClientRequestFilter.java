package com.cdd.gsl.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.cdd.gsl.common.util.EncryptionUtil;
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

//	@Autowired
//	private UserDao userDao;
//
//	@Autowired
//	private AdminDao adminDao;

	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
		response.setHeader("Access-Control-Allow-Headers",
				"authorization, content-type, x-tella-request-appversion, x-tella-request-provider, x-tella-request-timestamp, x-tella-request-token"
						+ ", x-tella-request-userid, x-tella-request-device, x-tella-request-usertype");
		String uri = request.getRequestURI();
		String regex = "[0-9]+";
		String method = request.getMethod();

		if ((uri.equals("/fbpark/v1/users/appsignup"))
				|| (uri.equals("/fbpark/v1/admin/signin"))
				|| (uri.equals("/fbpark/v1/admin/export_user"))
				|| (uri.equals("/fbpark/v1/admin/export_insure"))
				|| (uri.equals("/fbpark/v1/admin/export_order"))
				|| (uri.equals("/fbpark/v1/admin/orders_ticket/export_statistics"))
				|| (uri.matches("/fbpark/v1/admin/"+regex))
				|| (uri.equals("/fbpark/v1/users/signup"))
				|| (uri.equals("/fbpark/v1/users/webhooks"))
				|| (uri.equals("/fbpark/v1/basic_params"))
				|| (uri.equals("/fbpark/v1/admin/exception"))
				|| (uri.equals("/fbpark/v1/tickets/list"))
				|| (uri.equals("/fbpark/v1/users/get_code"))
				|| (uri.equals("/fbpark/v1/users/logout"))
				|| (uri.equals("/fbpark/v1/users/check_token"))) {

			filterChain.doFilter(request, response);
		} else {
			if (request.getHeader("Access-Control-Request-Method") != null && "OPTIONS".equals(method)) {
				filterChain.doFilter(request, response);
			} else {
				String userId = request.getHeader("X-Tella-Request-Userid") != null
						? request.getHeader("X-Tella-Request-Userid") : "";

				String token = request.getHeader("X-Tella-Request-Token") != null
						? request.getHeader("X-Tella-Request-Token") : "";


				if ((Strings.isNullOrEmpty(userId))
						|| (Strings.isNullOrEmpty(token))) {
					logger.error(String.format(
							"[**Auth Failure**] Missing params: userId:%s timestamp:%s token:%s",
							new Object[] { userId, token }));

					String url = request.getServletContext().getContextPath() + "/v1/users/error/noData";
					response.sendRedirect(url);
					logger.debug("*** no auth /v1/users/error/noData ****");
					return;
				}
				/*if(device.equals("10")){
					Admin admin = null;
					if (!Strings.isNullOrEmpty(userId)) {
						admin = (Admin) this.adminDao.getAdminById(Long.parseLong(userId));
					}

					if (admin == null) {
						logger.error(String.format("[**Auth Failure**] invalid userId:%s timestamp:%s token:%s",
								new Object[] { userId, timestamp, token }));
						JSONObject jo = new JSONObject();
						jo.put("status", "该用户不存在");
						jo.put("code", 10623);
						jo.put("error_message", "该用户不存在");
						PrintWriter writer = response.getWriter();
						writer.write(jo.toString());
						logger.debug("*** no User /v1/users/error/noData ****");
						return;
					}

					String raw = admin.getId() + admin.getPassword() + timestamp;
					String generatedToken = EncryptionUtil.hashMessage(raw);
					logger.debug("[**Debug Info**] raw: " + raw);
					logger.debug("[**Debug Info**] server calculated hash is: " + generatedToken);
					logger.debug("[**Debug Info**] provided hash is: " + token);

					if (!token.equals(generatedToken)) {
						logger.error(String.format(
								"[**Auth Failure**] invalid token for userId:%s timestamp:%s token:%s",
								new Object[] { userId, token }));
						JSONObject jo = new JSONObject();
						jo.put("status", "token失效");
						jo.put("code", 10624);
						jo.put("error_message", "token失效");
						PrintWriter writer = response.getWriter();
						writer.write(jo.toString());
						logger.debug("*** no token /v1/users/error/noData ****");
						return;
					}else{
						filterChain.doFilter(request, response);
					}
				}else{

					User user = null;
					if (!Strings.isNullOrEmpty(userId)) {
						user = (User) this.userDao.get(Long.parseLong(userId));
					}

					if (user == null) {
						logger.error(String.format("[**Auth Failure**] invalid userId:%s timestamp:%s token:%s",
								new Object[] { userId, token }));
						String url = getServletContext().getContextPath() + "/v1/users/error/noUser";
						response.sendRedirect(url);
						logger.debug("*** no User /v1/users/error/noData ****");
						return;
					}

					String raw = user.getId() + user.getPassword() + timestamp;
					String generatedToken = EncryptionUtil.hashMessage(raw);
					logger.debug("[**Debug Info**] raw: " + raw);
					logger.debug("[**Debug Info**] server calculated hash is: " + generatedToken);
					logger.debug("[**Debug Info**] provided hash is: " + token);

					if (!token.equals(generatedToken)) {
						logger.error(String.format(
								"[**Auth Failure**] invalid token for userId:%s timestamp:%s token:%s",
								new Object[] { userId, token }));
						String url = getServletContext().getContextPath() + "/v1/users/error/invalid_token";
						response.sendRedirect(url);
						logger.debug("*** no token /v1/users/error/noData ****");
						return;
					}else{
						filterChain.doFilter(request, response);
					}
				}*/
							

			}

		}
	}
	
}
