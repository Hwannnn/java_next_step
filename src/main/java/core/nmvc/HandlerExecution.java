package core.nmvc;

import core.mvc.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class HandlerExecution {
    private static final Logger log = LoggerFactory.getLogger(HandlerExecution.class);

    private Object controller;
    private Method method;

    public HandlerExecution(Object controller, Method method) {
        this.controller = controller;
        this.method = method;
    }

    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) {
        try {
            return (ModelAndView) method.invoke(controller, request, response);

        } catch (IllegalAccessException e) {
            log.error("HandlerExecution handle IllegalAccessException | method : {}, error : {}", method, e);
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            log.error("HandlerExecution handle InvocationTargetException | method : {}, error : {}", method, e);
            throw new RuntimeException(e);
        }
    }
}
