package ec.com.todocompu.ShrimpSoftServer.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableAspectJAutoProxy
@EnableAutoConfiguration
@Import({PersistenceConfig.class})
@EnableWebMvc
public class AppConfig {

}
