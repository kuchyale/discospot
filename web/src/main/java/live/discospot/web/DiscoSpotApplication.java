package live.discospot.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * DiscoSpot application's entry point.
 *
 * @author <a href="mailto:o.kuchynski@gmail.com">Aleh Kuchynski</a>
 * @since 1.0
 */
@SpringBootApplication
@ComponentScan(basePackages = {"live.discospot"})
public class DiscoSpotApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiscoSpotApplication.class, args);
    }
}
