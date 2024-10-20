package travel.w2m.techproof;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@OpenAPIDefinition(info = @Info(title = "${info.app.name}", description = "${info.app.description}", version = "${info.app.version}"))
@SpringBootApplication
public class SpacecraftsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpacecraftsApplication.class, args);
	}

}
