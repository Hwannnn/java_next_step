package core.nmvc;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class ControllerScannerTest {
    private static final Logger log = LoggerFactory.getLogger(ControllerScannerTest.class);

    private ControllerScanner controllerScanner;

    @Before
    public void setup() {
        controllerScanner = new ControllerScanner("next.controller");
    }

    @Test
    public void extract() {
        Map<Class<?>, Object> controllers = controllerScanner.extract();
        for(Class<?> controller : controllers.keySet()) {
            log.debug("controller : {}", controller);
        }
    }
}
