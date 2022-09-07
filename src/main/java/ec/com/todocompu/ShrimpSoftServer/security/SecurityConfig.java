package ec.com.todocompu.ShrimpSoftServer.security;

import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
@EnableWebSecurity
@Order(1)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private TokenUtil tokenUtil;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .exceptionHandling().and()
                .anonymous().and()
                .csrf().disable()
                .addFilterBefore(new CorsFilter(), ChannelProcessingFilter.class)
                .addFilterBefore(new VerifyTokenFilter(tokenUtil), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .anyRequest().authenticated().and().sessionManagement().maximumSessions(1);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers(HttpMethod.POST, "/todocompuWS/sistemaController/*");
    }

}
