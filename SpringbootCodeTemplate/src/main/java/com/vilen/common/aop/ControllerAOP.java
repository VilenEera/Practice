package com.vilen.common.aop;

import com.vilen.common.beans.ResultBean;
import com.vilen.common.exceptions.CheckException;
import com.vilen.common.exceptions.UnloginException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;



/**
 * 处理和包装异常
 * Created by vilen on 2017/10/18.
 */
@Aspect
@Component
public class ControllerAOP {
    private static final Logger logger = LoggerFactory.getLogger(ControllerAOP.class);

    @Pointcut("execution(public com.vilen.common.beans.ResultBean *(..))")
    public void controllerMethod(){}

    @Around("controllerMethod()")
    public Object handlerControllerMethod(ProceedingJoinPoint point) {
        long startTime = System.currentTimeMillis();
        ResultBean<?> result;
        try {
            result = (ResultBean<?>) point.proceed();
            logger.info(point.getSignature() + "use time:" + (System.currentTimeMillis() - startTime));
        } catch (Throwable throwable) {
            result = handlerException(point, throwable);
        }
        return result;
    }

    private ResultBean<?> handlerException(ProceedingJoinPoint point, Throwable e) {
        ResultBean<?> result = new ResultBean<>();

        // 已知异常
        if (e instanceof CheckException) {
            result.setMsg(e.getLocalizedMessage());
            result.setCode(ResultBean.FAIL);
        } else if (e instanceof UnloginException) {
            result.setMsg("Unlogin");
            result.setCode(ResultBean.NO_LOGIN);
        } else {
            logger.error(point.getSignature() + " error ", e);
            //TODO 未知的异常，应该格外注意，可以发送邮件通知等等
            result.setMsg(e.toString());
            result.setCode(ResultBean.FAIL);
        }
        return result;
    }
}
