package smarthome.slimmemeter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackageClasses = { Application.class })
public class Application {

   public static void main(String[] args) {
      new SpringApplication(Application.class).run(args);
   }
}
