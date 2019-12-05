package com.local.common.slog;

import com.local.cell.UserManager;
import com.local.common.slog.annotation.SLog;
import com.local.entity.sys.SYS_Log;
import com.local.entity.sys.SYS_USER;
import com.local.service.PeopleService;
import com.local.service.UnitService;
import com.local.service.UserService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.condition.RequestConditionHolder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.logging.Logger;

@Aspect
@Service
public class SLogAopInterceptor {
    private static Logger log = Logger.getLogger(SLogAopInterceptor.class.toString());

    @Resource
    protected SLogService sLogService;

    @Autowired
    private UserService userService;

    @Autowired
    private UnitService unitService;

    @Autowired
    private PeopleService peopleService;

    /**
     * @After 后置通知, 在方法执行之后执行 。
     * 未添加before和exception，记录方法开始和异常
     * 未添加returning="rvt"，因查询结果过多，日志表未采用分表设计
     *  execution(): 表达式主体。
     *   第一个*号：表示返回类型，*号表示所有的类型。
     *   包名：表示需要拦截的包名，后面的两个句点表示当前包和当前包的所有子包，com.sample.service.impl包、子孙包下所有类的方法，*号表示所有的类。
     *    *(..):最后这个星号表示方法名，*号表示所有的方法，后面括弧里面表示方法的参数，两个句点表示任何参数
     * @param joinPoint
     * @param slog
     */
    @After(("execution(* com.local..*.*(..)) && @annotation(slog)"))
    protected void doLog(JoinPoint joinPoint, SLog slog){
        HttpServletRequest request= ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ip=request.getRemoteAddr();
        SYS_USER user = UserManager.getUserToken(request, userService, unitService, peopleService);
        if (user!=null){
            if (sLogService ==null){
                sLogService=new SLogService();
            }else {
               try {
                   sLogService.log(slog.type(),slog.tag(),joinPoint.getSignature().getName(),ip,user,joinPoint.getArgs(),slog.async());
               }catch (Exception e){
                   e.printStackTrace();
               }
            }
        }
    }

}
