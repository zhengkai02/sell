//package indi.zk.mall.product.handle;
//
//import cn.hutool.db.Entity;
//import cn.hutool.db.ds.DSFactory;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang.RandomStringUtils;
//import org.aspectj.lang.ProceedingJoinPoint;
//import cn.hutool.db.SqlRunner;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.sql.DataSource;
//import java.util.Arrays;
//import java.util.Date;
//
///**
// * @author ZhengKai
// * @data 2019-12-19 17:04
// */
//@Deprecated
//@Aspect
//@Component
//@Slf4j
//public class AspectAPIMoinitor {
//
//    DataSource ds = ds = DSFactory.get();
//    SqlRunner runner = SqlRunner.create(ds);
//
//    /**
//     * 统计API接口调用耗时（方法调用的时间）
//     *
//     * @param joinPoint
//     * @throws Throwable
//     */
//
//    @Around("execution(public * indi.zk.mall.*.*(..))")
//    public Object logServiceMethodAccess(ProceedingJoinPoint joinPoint) throws Throwable {
//        log.info("joinPoint=【{}】",joinPoint);
//        // 接收到请求，记录请求内容
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = attributes.getRequest();
//
//        long start = System.currentTimeMillis();
//        Object object = joinPoint.proceed();
//        long end = System.currentTimeMillis();
//        long takeTime = end - start;
//        String className = joinPoint.getSignature().toString();
//
//        //接口监控保存
//        //interfaceMonitorService.insert(interfaceMonitor);
//
//        //runner既SqlRunner对象
//        int count = runner.insert(Entity.create("t_interface_monitor")
//                .set("monitor_id", RandomStringUtils.randomAlphanumeric(10))
//                .set("request_url", request.getRequestURL().toString())
//                .set("request_token", request.getHeader("token"))
//                .set("http_method", request.getMethod())
//                .set("class_name", className)
//                .set("reqest_paras", Arrays.toString(joinPoint.getArgs()))
//                .set("take_time", takeTime)
//                .set("create_time", new Date())
//                .set("module_name", "666"));
//        log.info("object=【{}】", object);
//        return object;
//    }
//}
