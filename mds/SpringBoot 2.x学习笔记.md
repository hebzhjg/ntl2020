## SpringBoot 2.x学习笔记



###	SpringBoot2.x 依赖环境和版本说明

1、jdk8以上，SpringBoot2.x用jdk8，底层框架是spring framework5

2、安装maven，3以上版本

3、idea开发环境

官网：https://spring.io/projects/spring-boot、



###	快速创建SpringBoot应用手工创建web应用

https://start.spring.io/



###	SpringBoot 接口http协议开发实战

1、@RestController 和 @RequestMapping 是springMVC的注解，不是SpringBoot特有的

2、@RestController = @Controller + ResponseBody

3、@SpringBootApplication = @Configuration + @EnableAutoConfiguration + @ComponentScan

spring中注解也是可以继承的



###	接口开发工具PostMan使用

官网：https://www.getpostman.com/downloads/

下载app安装

使用心得，创建request（接口名称），保存要保存到collections（大目录，如模块名称）



###	在Idea中不重启调试SpringBoot代码

方法一：（使用ide自动热部署，修改代码，保存就会热部署）

快捷键ctrl+alt+shift+/ --registry--compiler.automake.allow.when.app.running 勾选

file--setting--build,execution...compiler--Build project automactically 勾选

方法二：使用spring-boot-devtools工具

不被热部署的文件

1、/META-INF/maven,/META-INF/resources,/resources,/static,/public,or /templates

2、指定文件不进行热部署 spring.devtools.restart.exclude=static/** ,public/** 

3、手工触发热部署（监视一个文件）spring.devtools.restart.trigger-file=trigger.txt

当需要热部署时，修改trigger.txt（版本控制文件）即可





###	常用json框架介绍

阿里fastjson、谷歌gson等，各有优点，各有专长；spingBoot默认用Jackson

性能比较：Jackson 》FastJson 》Gson 》Json-lib

测试方法，循环序列化、百万次、次数达到一定才可以看出来



和Jackson框架处理的相关注释（常用）

@JsonIgnore 指定字段忽略，不返回值

@JsonFormat(patten="yyyy-MM-dd hh:mm:ss",locale="zh",timezone="GMT+8") 格式化日期

@JsonInclude(Include.NON_NULL) 如果字段为空值，则不返回数据

@JsonProperty 指定字段别名（隐藏后台数据库）



###	spingBoot中资源目录架构和加载顺序

src/main/java	存放java源代码

src/main/resources	存储资源文件

访问资源文件时：http:/localhost:8080/js/test.js (SpringBoot会在资源文件目录下，查找 js/test.js 文件；

资源文件的目录不用写，他会按指定顺序搜索

+ resources：资源文件

+ public：公共的资源文件

+ static：存放静态文件，如css、js、images

    + css：存放样式文件
    + js：存放js文件
    + images：存放图片（注意，必须是images）
    + 测试说明，自定义创建oth目录，js放进去可以访问，但png图片不行；说明SpringBoot对图片文件进行了优化处理，图片不能随意存放

+ templates：存放静态页面（对应mvc视图）jsp、html、tpl

    需要在pom中添加依赖

    ```xml
    <dependency>    
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-thymeleaf</artifactId>
    </dependency>
    ```

    + 加入thymeleaf（thyme [taim]百里香，一种植物；leaf叶子）就可以按MVC方式进行视图跳转
    + 如果要直接（/upload.html)访问时，需要把文件放到resources、public、static里

+ config：存放配置文件，application.properties

SpringBoot资源文件的加载顺序 META/resources > resources > static > public 即先到这些目录中查找，如果没有，再向下查找，直到对检查完成，报404错误

自定义目录（在application.properties）中添加配置

官网参考：https://docs.spring.io/spring-boot/docs/2.2.2.RELEASE/reference/html/appendix-application-properties.html#core-properties（Web properties）

```
spring.resources.static-locations=classpath:/META-INF/resources/,classpath:/resources/,classpath:/static/,classpath:/public/,classpath:/test/
```

大型公司都有CDN约定好目录结构，有效防止文件乱放；把资源文件从java项目中独立出来，使用高效的服务器处理。

全称是Content Delivery Network，即[内容分发网络](https://baike.baidu.com/item/内容分发网络/4034265)。CDN是构建在现有网络基础之上的智能虚拟网络，依靠部署在各地的边缘服务器，通过中心平台的负载均衡、内容分发、调度等功能模块，使用户就近获取所需内容，降低网络拥塞，提高用户访问响应速度和命中率。

###	SpringBoot文件上传MultipartFile的使用

源自springMVC，例子，使用upload.html上传一个图片文件到后台，然后在images下显示图片

+ 在public下创建一个html文件，upload.html

    ```html
    <!DOCTYPE html>
    <html lang="en">
        <head>
            <meta charset="UTF-8">
            <title>上传文件测试</title>
        </head>
        <body>
            <form enctype="multipart/form-data" method="post" action="/upload">
                文件：<input type="file" name="up_name">
                说明：<input type="text" name="name">
                <input type="submit" value="上传">
            </form>
        </body>
    </html>
    ```

+ 在java代码创建一个Controller类，添加一个方法

    ```java
    @RequestMapping("/upload")
        @ResponseBody
        public Object saveUpload(
                //与表单里的name要对应
                @RequestParam("up_name") MultipartFile file, HttpServletRequest request){
    
            //获取普通http参数
            String name = request.getParameter("name");
            System.out.println("name = "+name);
            //获得原始文件名
            String fileName = file.getOriginalFilename();
            System.out.println("orignal = "+fileName);
            //获取文件扩展名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            System.out.println("suffix = "+suffixName);
            //生产UUID保存文件名
            String saveName = UUID.randomUUID().toString() + suffixName;
            System.out.println("save ="+saveName);
    
            //
            File destFile = new File(UPLOAD_PATH+saveName);
            try {
                //这个方法spring进行了效率优化，比自己写高效
                file.transferTo(destFile);
                return new JsonData(1,"上传文件成功",destFile.getAbsolutePath());
            }catch (Exception ex){
                ex.printStackTrace();
            }
    
            return new JsonData(-1,"文件上传失败了");
        }
    ```

    + springboot的Application运行后会自动启动一个内置tomcat，默认端口8080，在windows下会把文件放到：C:\Users\Administrator\AppData\Local\Temp\tomcat.3977207045499270271.8080\work\Tomcat\localhost\ROOT
    + 上传成功后，使用http://localhost:8080/images/【UUID上传文件名】就能访问



###	以jar方式运行springBoot的web项目文件上传和访问

命令行：java - jar xxxxx.jar   --debug(调试模式)

使用maven打包项目，在pom文件中，需要有spring-boot-maven-plugin插件

如果不添加插件，运行jar文件时会保存，没有 manifest 文件，插件的作用就是生产这个文件

```xml
<plugin>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-maven-plugin</artifactId>
</plugin>
```

控制文件上传大小，在启动类里添加配置参数，在springBoot的启动类里添加

```java
    @Bean
    public MultipartConfigElement multipartConfigElement(){
        MultipartConfigFactory factory = new MultipartConfigFactory();

        //设置单个上传文件大小
        factory.setMaxFileSize(DataSize.parse("10240KB"));

        //设置总共允许上传文件大小
        factory.setMaxRequestSize(DataSize.parse("10240KB"));

        return factory.createMultipartConfig();
    }
```



###	springBoot的配置文件大全

1、常用的配置文件有xml、properties、json、yaml

+ YAML（yet another markup language）写YAML要比写xml快得多（无需关注标签和引号）

    使用空格space缩进表示分层，不同层次之间的缩进可以使用不同的空格数目

    注意：key后面的：冒号，后面一定

+ properties和YAML对比

    server.port=8080

    server.session-timeout=30

+ 用yml表示

    server: 

    ​		port: 8080

    ​		session-timeout: 30

2、特别说明，springboot默认配置文件已经做的很好了，不要把默认配置赋值过去，只添加需要修改默认配置的内容

3、springboot默认配置API文档https://docs.spring.io/spring-boot/docs/2.2.2.RELEASE/reference/html/appendix-application-properties.html#common-application-properties

###	springBoot配置文件自动映射到类对象

使用配置文件时，需要添加pom依赖，IDEA会提示：spring boot configuration annotation processor not found in classpath（在IDEA有一个红色提示条）

```xml
<dependency>
     <groupId>org.springframework.boot</groupId>
     <artifactId>spring-boot-configuration-processor</artifactId>
     <optional>true</optional>
</dependency>
```

方法一：在类代码中使用

使用注释在java代码中访问配置文件，在类上添加注释@PropertySource({"classpath:application.properties"})，然后在相应的字段上添加@Value("${user.loadfile.path}")注释访问

@PropertySource({file1,file1})	说明使用配置文件的位置，注意{}在外边，可以指定多个配置文件

@Value("${user.loadfile.path}")	让spring注入配置值${key}方式取值

方法二：实体类（配置文件）

在定义是，添加一下注释

```java
@Component
@PropertySource({"application.properties"})
public class ServerSettings {

    @Value("$name}")
    private String name;

    @Value("${domain}")
    private String domain;
....
```

启动时，报如下错误：Could not resolve placeholder 'domain' in value "${domain}"

注意：@ConfigurationProperties(prefix = "test") 使用前缀后，属性文件key和claas的属性名一只即可，不能再使用@Value("$name}")



###	SpringBoot单元测试

单元测试使用JUnit对代码进行单元测试，现在pom文件中添加 spring-boot-starter-test 依赖，测试代码放到 src/test/java/.....

```xml
<dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-test</artifactId>
      <scope>test</scope>
</dependency>
```

跑通单元测试一波三折

坑1：@Test（要用org.junit.Test）否则报 java.lang.Exception: No runnable methods 错误

抗2：@SpringBootTest 必须加 webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT，否则报Could not open ServletContext resource [/application.properties] 错误

```java
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = XdclassApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringBootTestDemo {
    /**
     * run as junit
     */
    @Test
    public void testOne(){
        //Assert.assertEquals(1,1);
        TestCase.assertEquals(1,1);
        System.out.println("Test OK");
    }
}
```

使用MockMvc对API进行单元测试（手工用PostMan测试），添加@AutoConfigureMockMvc   //这个注释，非常重要

```java
@RunWith(SpringRunner.class)
@SpringBootTest(classes = XdclassApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc   //这个注释，非常重要
public class MockMvcTest {
    /**
     * 注入 MockMvc对象
     */
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testApi() throws Exception {

        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.get("/v1/test_properties"))
                //.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn();
        int status = result.getResponse().getStatus();
        TestCase.assertEquals(200,status);
        System.out.println("测试完成");
    }
}
```





###	自定义SpringBoot启动banner

从官方文件 Spring官方，Projects-->SpringBoot-->Learn-->Reference Doc--> Application Properties-->Common Application properties（https://docs.spring.io/spring-boot/docs/2.2.2.RELEASE/reference/html/appendix-application-properties.html#common-application-properties）用Ctrl+F查找banner

| `spring.banner.charset`         | `UTF-8`                | Banner file encoding.                                        |
| ------------------------------- | ---------------------- | ------------------------------------------------------------ |
| `spring.banner.image.bitdepth`  | `4`                    | The bit depth to use for ANSI colors. Supported values are 4 (16 color) or 8 (256 color). |
| `spring.banner.image.height`    |                        | Height of the banner image in chars (default based on image height). |
| `spring.banner.image.invert`    | `false`                | Whether images should be inverted for dark terminal themes.  |
| `spring.banner.image.location`  | `classpath:banner.gif` | Banner image file location (jpg or png can also be used).    |
| `spring.banner.image.margin`    | `2`                    | Left hand image margin in chars.                             |
| `spring.banner.image.pixelmode` | `TEXT`                 | The pixel mode to use when rendering the image.              |
| `spring.banner.image.width`     | `76`                   | Width of the banner image in chars.                          |
| `spring.banner.location`        | `classpath:banner.txt` | Banner text resource location.                               |
| `spring.beaninfo.ignore`        | `true`                 | Whether to skip search of BeanInfo classes.                  |

###	SpringBoot配置全局异常处理

健壮的app后台应当对异常进行有效的处理，这样给用户比较好的提示，错误后会详细的错误返回（不友好）

1、定义一个domain类，使用 @RestControllerAdvice 注释

2、添加处理异常的方法，使用 @ExceptionHandler(value = 要处理的异常类型) ，方法可以返回json格式数据

```java
//使用 @ControllerAdvice时，要返回json格式，方法上加@ResponseBody
@RestControllerAdvice
public class CustomExceptionHandler {

    private static Logger LOG = LoggerFactory.getLogger(CustomExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    public Object handleException(Exception ex, HttpServletRequest request){
        //在不用写{}中间的序号
        LOG.error("url:{} error, msg:{}",request.getRequestURL(),ex.getMessage());
        Map<String,Object> map = new HashMap<>();
        map.put("code",1);
        map.put("msg",ex.getMessage());
        map.put("url",request.getRequestURL());
        return map;
    }
}
```

3、也可以在出现异常是，路由指定视图文件（使用thymeleaf模板引擎）



###	SpringBoot项目的war打包和部署

启动SringBoot项目有两种方式，jar或war

（1）java -jar xxxx.jar 方式，先打包jar项目文件

（2）打包war文件，然后部署到web容器（Tomcat9），修改pom文件添加或修改

+ <packaging>war</packaging>
+ <!-- 在build下 打包war的项目文件 -->
    <finalName>xdclass_springboot</finalName>

（3）执行maven install打包，输出war文件，复制到tomcat/webapps目录下

遇到的坑：

+ 没有修改入口文件，打包war部署成功，但springBoot没有加载

    ```java
    SpringBootApplication //一个顶下面三个
    //@SpringBootConfiguration
    //@EnableAutoConfiguration
    //@ComponentScan
    public class XdclassApplication
            //打包war，在容器中运行，要使用 SpringBootServletInitializer 入口
            extends SpringBootServletInitializer {
    
    
        /**
         * 以Web方式启动springBoot项目
         * @param builder
         * @return
         */
        @Override
        protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
            return builder.sources(XdclassApplication.class);
        }
    
        /**
         * 入口方法，jar方式运行
         * @param args
         */
        public static void main(String[] args) {
            SpringApplication.run(XdclassApplication.class, args);
        }
    }
    ```

+ 部署后启动SpringBoot失败，报Could not open ServletContext resource [/application.properties]

    检查源代码：发现有个类使用了资源文件配置 @PropertySource({"application.properties"}) 会然后spirng在启动时从（/）目录下加载，正确写法@PropertySource({"classpath:application.properties"})

    

## SpringBoot之Servlet3.0新特性



###	过滤器 Filter组件

作用，对访问的URL进行过滤（check），就是一个检票员的角色；user用户通过url访问服务时，请求通过filter时调用doFilter()方法，可以在这里对request请求中信息进行验证或处理，然后再决定是否继续，或重定向到其他URL。

1、SpringBoot启动默认加载的Filter（可以使用debug模式，看启动过程log信息）

+ characterEncodeingFilter
+ hiddenHttpMethodFilter
+ httpPutFormContentFilter
+ requestContextFilter

2、Filter的优先级

3、自定义Filter

+ 使用Servlet3.0的注释即可（不用再但web.xml中添加配置）

+ 启动类添加 @ServletComponentScan ，支持sevlet扫描

+ 创建自己的Filter 实现 Filter接口 public class CustomFilter implements Filter

+ 使用@WebFilter(urlPatterns = "/v1/*",filterName = "customFilter") 注释，urlPatterns 支持正则

+ 应用场景：登录验证、请求路由转发等

    

###	原生Servlet的使用

使用Servlet3.0的注释创建原生Servlet，免去了在web.xml中复杂的配置，使用一个注解就可创建

+ 启动类添加 @ServletComponentScan ，支持sevlet扫描

+ 创建自己的sevlet对象，extends HttpServlet 

+ 使用@WebServlet(urlPatterns = "/v1/mysvl",name = "customServlet")

+ 重新 doPost、doGet方法

+ 应用场景：加载或初始化组件

    

### 监听器Listener的使用

监听器有多种，ServletContextListener（容器上下文的变化）、HttpSessionListener（用户请求回话的创建和销毁）、ServletRequestListener（用户请求的创建和销毁）

监听器的作用好比一个，监控者，探出被监控对象的变化，就创建一个事件；如家里安装一个监控或烟雾探测器，会自动不断的对周围环境进行监听，有外人进来，或检测到，就能触发报警

+ 启动类添加 @ServletComponentScan ，支持sevlet扫描

+ 创建自己的listener对象，implements HttpSessionListener, ServletRequestListener

+ 使用@WebListener

+ 实现响应的事件方法，并进行处理

    

### 拦截器Interceptor的使用

拦截器基于AOP思想设计，与Servlet容器没有关系（区分Filter基于doFilter()只在Servlet前后起作用）可以在代码的任何位置中出现，方法前后、异常抛出前后等；

拦截器支持多个，形成一个链，按照注册顺序进行拦截，先注册，先被拦截处理

+ 创建配置文件类 ，并使用@Configuration

    ```java
    @Configuration
    public class CustomWebMvcConfigure implements WebMvcConfigurer {
    }
    ```

+ 创建自己的interceptor对象



















