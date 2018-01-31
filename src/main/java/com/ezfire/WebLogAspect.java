package com.ezfire;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * Created by lcy on 2018/1/26.
 */
@Aspect
@Component
public class WebLogAspect {
	private static Logger s_logger = LoggerFactory.getLogger(WebLogAspect.class);

	//execution函数用于匹配方法执行的连接点，语法为：
	//execution(方法修饰符(可选)  返回类型  方法名(参数)  异常模式(可选))
	@Pointcut("execution(public * com.ezfire.web.*Controller.*(..))")
	public void logPointCut() {

	}

	@Before("logPointCut()")
	public void doBefore(JoinPoint joinPoint) throws Throwable {
		// 接收到请求，记录请求内容
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();

		// 记录下请求内容
		s_logger.info("请求地址 : " + request.getRequestURL().toString());
		s_logger.info("HTTP METHOD : " + request.getMethod());
		s_logger.info("IP : " + request.getRemoteAddr());
		s_logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "."
				+ joinPoint.getSignature().getName());
		s_logger.info("参数 : " + Arrays.toString(joinPoint.getArgs()));

	}

	@AfterReturning(returning = "ret", pointcut = "logPointCut()")// returning的值和doAfterReturning的参数名一致
	public void doAfterReturning(Object ret) throws Throwable {
		// 处理完请求，返回内容
		if(ret.getClass().equals(ResponseEntity.class)) {
			ResponseEntity<String> result = (ResponseEntity<String>) ret;
			s_logger.info("返回值 : " + result.getStatusCode());
		}
	}

	@Around("logPointCut()")
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		long startTime = System.currentTimeMillis();
		Object ob = pjp.proceed();// ob 为方法的返回值
		s_logger.info("耗时 : " + (System.currentTimeMillis() - startTime) + "ms");
		return ob;
	}
}
