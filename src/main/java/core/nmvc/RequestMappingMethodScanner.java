package core.nmvc;

import com.google.common.base.Predicate;
import com.google.common.collect.Sets;
import core.annotation.RequestMapping;
import org.reflections.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

public class RequestMappingMethodScanner {
    private Set<Method> requestMappingMethods;

    public RequestMappingMethodScanner() {
        requestMappingMethods = Sets.newHashSet();
    }

    public Set<Method> scan(Map<Class<?>, Object> controllers) {
        Predicate requestMappingPredicate = ReflectionUtils.withAnnotation(RequestMapping.class);
        for (Class<?> controller : controllers.keySet()) {
            this.requestMappingMethods.addAll(ReflectionUtils.getAllMethods(controller, requestMappingPredicate));
        }

        return this.requestMappingMethods;
    }

}
