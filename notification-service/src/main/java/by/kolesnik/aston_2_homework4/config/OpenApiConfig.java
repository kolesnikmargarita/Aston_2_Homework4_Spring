package by.kolesnik.aston_2_homework4.config;

import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.examples.Example;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OperationCustomizer globalResponseCustomizer() {
        return (operation, handlerMethod) -> {
            Example internalServerErrorExample = new Example();
            internalServerErrorExample.setValue("""
                    {
                        "message": "Internal Server Error: Connection timed out to database or an unexpected exception occurred"
                    }
                    """);

            MediaType mediaType = new MediaType()
                    .schema(new Schema<>().$ref("#/components/schemas/ErrorResponse"))
                    .addExamples("InternalServerErrorExample", internalServerErrorExample); // Привязываем пример

            Content content = new Content().addMediaType("application/json", mediaType);

            operation.getResponses().addApiResponse("500",
                    new ApiResponse()
                            .description("Internal Server Error")
                            .content(content)
            );
            return operation;
        };
    }
}
