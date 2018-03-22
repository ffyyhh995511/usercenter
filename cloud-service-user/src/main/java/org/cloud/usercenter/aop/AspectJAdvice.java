package org.cloud.usercenter.aop;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import com.alibaba.fastjson.JSON;


/**
 *  通用API切面
 * <code>@Order</code> 指定切面的优先级，当有多个切面时，数值越小优先级越高
 * Created by linrufeng on 16/8/19.
 */
@Order(1)
@Component
@Aspect
@Slf4j
public class AspectJAdvice {

    /**
     * Pointcut
     * 定义Pointcut，Pointcut的名称为aspectjMethod()，此方法没有返回值和参数
     * 该方法就是一个标识，不进行调用
     */
    @Pointcut("execution(public * org.cloud.usercenter.controller.*.*(..))")
    private void aspectjMethod() {
    }
    
    

    /**
     * Around
     * 手动控制调用核心业务逻辑，以及调用前和调用后的处理,
     * 注意：当核心业务抛异常后，立即退出，转向AfterAdvice
     * 执行完AfterAdvice，再转到ThrowingAdvice
     * @throws Throwable
     */
    @Around(value = "aspectjMethod()")
    public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
        Long start = System.nanoTime();
       /* for (Object object : pjp.getArgs()) {
            if (object instanceof BindingResult) {
                BindingResult bindingResult = (BindingResult) object;
                if (bindingResult.hasErrors()) {
                    String errorMessage = ObjectUtil.checkParams(bindingResult);
                    log.info("调用接口参数错误：{}", errorMessage);
                    return Response.failedResponse(errorMessage);
                }
            }
        }*/

        //调用核心逻辑
        Object retVal = pjp.proceed();
        Long end = System.nanoTime() - start;
        Signature sig = pjp.getSignature();
        log.info("接口 {}.{} 运行时间：{} 毫秒({}纳秒)", sig.getDeclaringTypeName(), sig.getName(),(float) end / 1000000, end);
        log.info("接口 {}.{} 返回数据：{}",sig.getDeclaringTypeName(), sig.getName(),JSON.toJSONString(retVal));
        return retVal;
    }
    
    /** 
     * 核心业务逻辑调用异常退出后，执行此Advice，处理错误信息
     * 注意：执行顺序在Around Advice之后
     * @param joinPoint
     * @param ex
     */  
    @AfterThrowing(value = "aspectjMethod()", throwing = "ex")    
    public void afterThrowingAdvice(JoinPoint joinPoint, Exception ex) {
    	List<Object> list = new ArrayList<Object>();
        for (int i = 0; i < joinPoint.getArgs().length; i++) {  
        	list.add(joinPoint.getArgs()[i]);
        }  
        log.error("The interface parameter that causes the error:{}",JSON.toJSONString(list));  
    }

}