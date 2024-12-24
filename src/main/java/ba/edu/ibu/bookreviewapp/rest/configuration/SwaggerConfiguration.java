package ba.edu.ibu.bookreviewapp.rest.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "IBU AI-Driven Software Engineering",
                version = "1.0.0",
                description = "AI-Driven Software Engineering Backend Application",
                contact = @Contact(name = "AI-Driven Software Engineering", email = "amina.meric@stu.ibu.edu.ba")
        ),
        servers = {
                @Server(url = "/", description = "Default Server URL")
        }
)

public class SwaggerConfiguration {

}