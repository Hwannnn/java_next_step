package core.nmvc;

import com.google.common.base.Predicate;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import org.reflections.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

public class RequestMappingHandler {
    private static final Logger log = LoggerFactory.getLogger(RequestMappingHandler.class);

    private Object[] basePackage;

    private Map<Class<?>, Object> controllers;
    private Set<Method> requestMappingMethods;
    private Map<HandlerKey, HandlerExecution> requestMappings;

    public RequestMappingHandler(Object... basePackage) {
        this.basePackage = basePackage;

        controllers = Maps.newHashMap();
        requestMappingMethods = Sets.newHashSet();
        requestMappings = Maps.newHashMap();
    }

    public void initialize() {
        log.debug("RequestMappingHandler initialize start");

        this.controllers = new ControllerScanner(this.basePackage).scan();
        this.requestMappingMethods = new RequestMappingMethodScanner().scan(this.controllers);
    }

    public void linkRequestMapping() {
        for (Method method : this.requestMappingMethods) {
            this.requestMappings.put(createHandleKey(method), createHandleExecution(method));
        }
    }

    private HandlerKey createHandleKey(Method method) {
        RequestMapping requestMappingAnnotation = method.getAnnotation(RequestMapping.class);

        return (new HandlerKey(requestMappingAnnotation.value(), requestMappingAnnotation.method()));
    }

    private HandlerExecution createHandleExecution(Method method) {
        Object methodDeclaredClass = this.controllers.get(method.getDeclaringClass());

        return new HandlerExecution(methodDeclaredClass, method);
    }

    public HandlerExecution getHandler(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        RequestMethod requestMethod = RequestMethod.valueOf(request.getMethod().toUpperCase());

        return this.requestMappings.get(new HandlerKey(requestUri, requestMethod));
    }

}
