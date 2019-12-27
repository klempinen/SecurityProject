package sec.project;

import java.util.Collections;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.SessionCookieConfig;
import javax.servlet.SessionTrackingMode;
import org.apache.catalina.Context;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@EnableAutoConfiguration
@SpringBootApplication
public class CyberSecurityBaseProjectApplication extends SpringBootServletInitializer implements EmbeddedServletContainerCustomizer  {

    public static void main(String[] args) throws Throwable {
        SpringApplication.run(CyberSecurityBaseProjectApplication.class);
    }
 
    @Override
    public void customize(final ConfigurableEmbeddedServletContainer container)
    {
        ((TomcatEmbeddedServletContainerFactory) container).addContextCustomizers(new TomcatContextCustomizer()
            {
                @Override
                public void customize(Context context)
                {
                    context.setUseHttpOnly(false);
                }
            }
        );
    } 
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        // This can be done here or as the last step in the method
        // Doing it in this order will initialize the Spring
        // Framework first, doing it as last step will initialize
        // the Spring Framework after the Servlet configuration is 
        // established
        super.onStartup(servletContext);

        // This will set to use COOKIE only
        servletContext
            .setSessionTrackingModes(
                Collections.singleton(SessionTrackingMode.URL)
        );
        // This will prevent any JS on the page from accessing the
        // cookie - it will only be used/accessed by the HTTP transport
        // mechanism in use
        //SessionCookieConfig sessionCookieConfig=
        //        servletContext.getSessionCookieConfig();
        //sessionCookieConfig.setHttpOnly(true);
    }

}
