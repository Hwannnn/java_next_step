package core.nmvc;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import core.annotation.Controller;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;

public class ControllerScanner {
    private static final Logger log = LoggerFactory.getLogger(ControllerScanner.class);

    private Reflections reflections;
    private Set<Class<?>> controllers;

    public ControllerScanner(Object... packageBasePath) {
        reflections = new Reflections(packageBasePath);
        controllers = Sets.newHashSet();
    }

    public Map<Class<?>, Object> scan() {
        Map<Class<?>, Object> controllersMap = Maps.newHashMap();

        try {
            for (Class<?> controllerClass : reflections.getTypesAnnotatedWith(Controller.class)) {
                controllersMap.put(controllerClass, controllerClass.newInstance());
            }
        } catch (InstantiationException e) {
            log.error("ControllerScanner mappingController InstantiationException", e);

        } catch (IllegalAccessException e) {
            log.error("ControllerScanner mappingController IllegalAccessException", e);
        }

        return controllersMap;
    }

}
