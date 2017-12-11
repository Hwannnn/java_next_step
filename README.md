#### 1. Tomcat 서버를 시작할 때 웹 애플리케이션이 초기화하는 과정을 설명하라.
* 톰캣이 시작하면 ServletContextListener 인터페이스를 구현하고 있는 ContextLoaderListener 클래스의 contextInitialized() 메소드가 실행된다.
* contextInitialized() 메소드는 H2 데이터베이스를 실행하기 위해, ConnectionManager와 스크립트를 읽어와서 실행하는 역할을 한다.
* 다음은 Filter 인터페이스를 구현하고 있는 CharacterEncodingFilter 클래스와 ResourceFilter 클래스의 init() 메소드를 호출하여 초기화한다.

* 또한, HttpServlet 클래스를 상속하는 클래스들을 찾아서 인스턴스를 만들고 init() 메소드를 호출하여 초기화한다.
* 그리고 @WebServlet 애노테이션의 값을 읽어 요청 URL과 서블릿을 연결하는 Map을 생성한다.
* 즉 DispatcherServlet 클래스를 찾아서 urlPatterns = "/"과 매핑한다.

* DispatcherServlet 클래스의 init() 메소드에는 RequestMapping 클래스의 인스턴스를 만들고 initMapping()을 실행하여 특정 url patter에 대한 Controller를 매핑한다.


#### 2. Tomcat 서버를 시작한 후 http://localhost:8080으로 접근시 호출 순서 및 흐름을 설명하라.
* 클라이언트의 요청이 들어오면, 먼저 CharacterEncodingFilter 클래스와 ResourceFilter 클래스의 doFilter() 메소드를 거친다.
* 다음은 DispatcherServlet 클래스로 가서 요청 URL을 추출하고, 초기화 해놓은 RequestMapping 인스턴스의 findController(String url) 메소드를 통해 요청 URL에 따른 Controller 인스턴스를 가져온다.
* 가져온 Controller의 execute(HttpServletRequest request, HttpServletResponse response) 메소드를 실행한다.

* 요청에 따른 execute(HttpServletRequest request, HttpServletResponse response) 메소드의 작업이 끝나면 응답할 뷰 객체를 ModelAndView 객체에 담아서 반환한다.
* 추가적으로 응답에 필요한 모델 객체가 있으면, ModelAndView의 addObject(String attributeName, Object attributeValue) 메소드를 통해 추가한다.

* 다시 DispatcherServlet 클래스로와서 Controller에서 반환한 ModelAndView 객체를 이용해, 뷰 객체를 꺼내어 View 인터페이스에 담는다.
* View 인터페이스를 구현한 각 뷰 객체의 render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response)를 통해 모델 객체나 response 객체의 처리를 하고 클라이언트로 응답한다.


#### 7. next.web.qna package의 ShowController는 멀티 쓰레드 상황에서 문제가 발생하는 이유에 대해 설명하라.
* 기존의 ShowController 클래스는 멤버 변수로 있는 Question 모델 인스턴스와 Answer 모델 인스턴스를 가진다.
* 멀티 쓰레드 환경에서 값이 변할 수 있는 변수로 멤버 변수를 가지는 경우, 설정되는 값이 엉킬 수 있는 상황이 올 수 있다.
* 따라서, 메소드 안에 지역변수로 주는 것이 멀티 쓰레드 환경에서 안전하다. 