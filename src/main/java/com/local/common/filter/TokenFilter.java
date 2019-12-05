package com.local.common.filter;

import com.local.entity.sys.SYS_USER;
import com.local.service.UserService;
import com.local.util.Result;
import com.local.util.ResultCode;
import com.local.util.ResultMsg;
import org.apache.catalina.filters.CorsFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/***************
 * token验证拦截
 * @author wanghn
 */
@CrossOrigin
public class TokenFilter extends CorsFilter {
    // 返回ajax未登录信息
    private static final String _301_JSON =new Result(ResultCode.ERROR_USER_301.toString(), ResultMsg.LOGOUT_ERROR,null,null).getJson();
    //返回成功信息
    private static final String _200_JSON = new Result(ResultCode.SUCCESS.toString(),ResultMsg.SUCCESS,null,null).getJson();

    @Autowired
    private UserService userService;
    @Override
    public void destroy() {
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse rep = (HttpServletResponse) response;
        //跨域访问
        String curOrigin = rep.getHeader("Origin");
        rep.setHeader("Access-Control-Allow-Origin", curOrigin == null ? "true" : curOrigin);
        rep.setHeader("Access-Control-Allow-Credentials", "true");
        rep.setHeader("Access-Control-Allow-Methods", "POST, GET, PATCH, DELETE, PUT");
        rep.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        rep.setHeader("Access-Control-Allow-Origin", "*");
//        System.out.println("跨域了！！！！！！！");
        //允许的访问方法
        rep.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        //请求头允许参数
        rep.setHeader("Access-Control-Allow-Headers", "Content-Type,usertoken");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        if("OPTIONS".equals(req.getMethod())){//OPTIONS请求
            rep.setStatus(HttpServletResponse.SC_OK);
            return;
        }else{
            //从请求的header中取出当前登录的用户
            String token=req.getHeader("userToken");
//            System.out.println(token+"=>>>>>");
            if (token==null || "".equals(token)){
                //从请求的url中取出当前登录的用户
                token = req.getParameter("userToken");
            }
            SYS_USER user=userService.selectUserById(token);
            if (null == token || token.isEmpty()) {
                response.getWriter().print(_301_JSON);
            }else {
                if (user!=null){
                    try {
                        user=user;
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    if (user ==null) {
                        response.getWriter().print(_301_JSON);
                    }
                    chain.doFilter(request,response);
                }else {
                    response.getWriter().print(_301_JSON);
                }
            }
        }
    }
    @Override
    public void init(FilterConfig arg0) throws ServletException {
    }
}
