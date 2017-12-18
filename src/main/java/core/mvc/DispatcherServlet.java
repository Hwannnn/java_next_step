package core.mvc;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.nmvc.RequestMappingHandler;
import core.nmvc.HandlerExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(DispatcherServlet.class);

    private RequestMappingHandler requestMappingHandler;

    @Override
    public void init() throws ServletException {
        requestMappingHandler = new RequestMappingHandler("next.controller");
        requestMappingHandler.initialize();
        requestMappingHandler.linkRequestMapping();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Object controllerHandler = requestMappingHandler.getHandler(request);
        if(controllerHandler == null) {
            log.debug("존재하지 않는 URL입니다.", request.getRequestURI());
            return;
        }

        ModelAndView modelAndView = ((HandlerExecution) controllerHandler).handle(request, response);

        try {
            View view = modelAndView.getView();
            view.render(modelAndView.getModel(), request, response);
        } catch (Throwable e) {
            log.error("DispatcherServlet service View Process Exception : {}", e);
            throw new ServletException(e.getMessage());
        }
    }
}
