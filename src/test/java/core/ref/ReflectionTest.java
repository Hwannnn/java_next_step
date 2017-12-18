package core.ref;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import next.model.Question;
import next.model.User;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    public void showClass() {
        Class<Question> questionClass = Question.class;
        logger.debug("! Question Class Name : {}", questionClass.getName());

        for(Field questionField : questionClass.getDeclaredFields()) {
            logger.debug("! Question Field Name : {}", questionField.getName());
        }

        for(Method questionMethod : questionClass.getMethods()) {
            logger.debug("! Question Method Name : {}", questionMethod.getName());
        }
    }
    
    @Test
    public void newInstanceWithConstructorArgs() throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Class<User> userClass = User.class;

        for(Constructor<?> constructor : userClass.getConstructors()) {
            logger.debug("New User Class Instance : {}", constructor.newInstance("tprp5950","password","신창환","tprp5950@naver.com"));
        }

    }
    
    @Test
    public void privateFieldAccess() throws NoSuchFieldException, IllegalAccessException {
        Student student = new Student();
        Class<?> studentClass = student.getClass();

        Field name = studentClass.getDeclaredField("name");
        name.setAccessible(true);
        name.set(student, "창환");

        logger.debug("New Student Name : {}", student.getName());

    }
}
