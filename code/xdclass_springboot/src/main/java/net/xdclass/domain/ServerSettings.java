package net.xdclass.domain;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
//注意，这里要加classpath:否则以war方式部署到tomcat下，报找不到配置文件
@PropertySource({"classpath:application.properties"})
//如果使用 prefix = "test" 就不要在用 @Value 除非name不一致时
@ConfigurationProperties(prefix = "test")
public class ServerSettings {

    //@Value("$name}")
    private String name;

    //@Value("${domain}")
    private String domain;

    //会报错 Could not resolve placeholder 'host' in value "${host}"
    //@Value("${host}")
    private String host;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
