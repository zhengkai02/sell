package indi.zk.mall.product.handle;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iwhalecloud.retail.common.utils.UidGeneator;
import indi.zk.mall.product.DO.TblServiceLog;
import indi.zk.mall.product.repository.TblServiceLogService;
import indi.zk.mall.product.service.impl.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Stream;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ZhengKai
 * @data 2019-12-19 18:31
 */
@Aspect
@Component
@Slf4j
@WebFilter(filterName = "logFilter", urlPatterns = "/*")
public class WebLogAspect {

    @Value("${spring.application.name}")
    private String microserviceName;

    private static final String ignoreUrlRegex = ".*((pay/)|(/index)|(/index/.*)|([.]((html)|(jsp)|(css)|(js)|(gif)|(png))))$";

    private final ObjectMapper mapper;

    private String logId;

    @Autowired
    public WebLogAspect(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Autowired
    private TblServiceLogService tblServiceLogService;

    private static String[] types = {"java.lang.Integer", "java.lang.Double",
            "java.lang.Float", "java.lang.Long", "java.lang.Short",
            "java.lang.Byte", "java.lang.Boolean", "java.lang.Char",
            "java.lang.String", "int", "double", "long", "short", "byte",
            "boolean", "char", "float"};

    private static ThreadLocal<Long> startTime = new ThreadLocal<Long>();


    /**
     * 定义规则：com.dxyl.controller..*(..))包下面的所有类中，有@PostMapping注解的方法
     */
    @Pointcut("execution(public * indi.zk.mall.product.controller.*.*(..))")
    public void controllerMethodPointcut() {
    }

    @Before("controllerMethodPointcut()")
    public String controller(JoinPoint point) throws IOException {
        Object requestMsg = null;
        for (Object object : point.getArgs()) {
            if (object instanceof MultipartFile ||
                    object instanceof HttpServletRequest ||
                    object instanceof HttpServletResponse) {
                continue;
            }
            if (log.isInfoEnabled()) {
                requestMsg = object;
                log.info(point.getTarget().getClass().getName() + "." + point.getSignature().getName() + " : request parameter : " + requestMsg);
            }
        }

        startTime.set(System.currentTimeMillis());
        MethodSignature signature = (MethodSignature) point.getSignature();
        Long count = Stream.of(signature.getMethod().getDeclaredAnnotations())
                .filter(annotation -> annotation.annotationType() == PostMapping.class)
                .count();
        String requestPath = count >= 1 ? signature.getMethod().getAnnotation(PostMapping.class).value()[0] : "";

        String info = String.format("\n =======> 请求路径: %s  %s", requestPath, getMethodInfo(point));
        //获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        //从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        HttpServletResponse response = ((ServletRequestAttributes) requestAttributes).getResponse();
        TblServiceLog tblServiceLog = getMethodInfo(point);
        tblServiceLog.setRequestPath(request.getRequestURI());
        tblServiceLog.setIp(UserService.getRemoteIp(request));
        tblServiceLog.setRequestId(request.getHeader("requestId"));
        tblServiceLog.setSystemCode(String.valueOf(response.getStatus()));
        if (null != requestMsg) {
            String str = JSON.toJSONString(requestMsg);
            tblServiceLog.setRequestMsg(str);
        }
        tblServiceLogService.save(tblServiceLog);
        log.info(info);
        return requestPath;
    }

    private TblServiceLog getMethodInfo(JoinPoint point) {

        String className = point.getSignature().getDeclaringType().getSimpleName();
        String methodName = point.getSignature().getName();
        String[] parameterNames = ((MethodSignature) point.getSignature()).getParameterNames();
        StringBuilder sb = null;
        if (Objects.nonNull(parameterNames)) {
            sb = new StringBuilder();
            for (int i = 0; i < parameterNames.length; i++) {
                // 对参数解析(参数有可能为基础数据类型，也可能为一个对象，若为对象则需要解析对象中变量名以及值)
                String value = "";
                if (point.getArgs()[i] == null) {
                    value = "null";
                } else {
                    // 获取对象类型
                    String typeName = point.getArgs()[i].getClass().getTypeName();
                    boolean flag = false;
                    for (String t : types) {
                        //1 判断是否是基础类型
                        if (t.equals(typeName)) {
                            value = point.getArgs()[i].toString();
                            flag = true;
                        }
                        if (flag) {
                            break;
                        }
                    }
                    if (!flag) {
                        //2 通过反射获取实体类属性
                        value = getFieldsValue(point.getArgs()[i]);
                    }
                }
                sb.append(parameterNames[i] + ":" + value + "; ");
            }
        }
        sb = sb == null ? new StringBuilder() : sb;
        String info = String.format("\n =======> 请求类名: %s \n =======> 请求方法: %s \n =======> 请求参数: %s", className, methodName, sb.toString());
        TblServiceLog tblServiceLog = new TblServiceLog();
        logId= UidGeneator.getUIDStr();
        tblServiceLog.setLogId(logId);
        tblServiceLog.setMicroserviceName(microserviceName);
        tblServiceLog.setClassName(className);
        tblServiceLog.setInterfaceName(methodName);
        tblServiceLog.setCreateBy("system");
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now = formatter.format(currentTime);
        tblServiceLog.setCreateTime(now);
        tblServiceLog.setModifyBy("system");
        tblServiceLog.setModifyTime(now);
        return tblServiceLog;
    }

    /**
     * 解析实体类，获取实体类中的属性
     */
    public static String getFieldsValue(Object obj) {
        //通过反射获取所有的字段，getFileds()获取public的修饰的字段
        //getDeclaredFields获取private protected public修饰的字段
        Field[] fields = obj.getClass().getDeclaredFields();
        String typeName = obj.getClass().getTypeName();
        for (String t : types) {
            if (t.equals(typeName)) {
                return "";
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (Field f : fields) {
            //在反射时能访问私有变量
            f.setAccessible(true);
            try {
                for (String str : types) {
                    //这边会有问题，如果实体类里面继续包含实体类，这边就没法获取。
                    //其实，我们可以通递归的方式去处理实体类包含实体类的问题。
                    if (f.getType().getName().equals(str)) {
                        sb.append(f.getName() + " : " + f.get(obj) + ", ");
                    }
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        sb.append("}");
        return sb.toString();
    }

    @AfterReturning(returning = "response", pointcut = "controllerMethodPointcut()")
    public void doAfterReturning(Object response) throws Throwable {
        String responseMsg = null;
        if (response != null) {
            responseMsg = mapper.writeValueAsString(response);
            log.info("response parameter : " + mapper.writeValueAsString(response));
        }
        long costTime = System.currentTimeMillis() - startTime.get();
        Optional<TblServiceLog> tblServiceLogOptional = tblServiceLogService.findById(logId);
        TblServiceLog tblServiceLog = tblServiceLogOptional.get();
        tblServiceLog.setResponseMsg(responseMsg);
        tblServiceLog.setCostTime(costTime);
        tblServiceLogService.save(tblServiceLog);

    }
}
