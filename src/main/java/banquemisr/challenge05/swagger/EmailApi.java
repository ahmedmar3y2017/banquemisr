package banquemisr.challenge05.swagger;

import banquemisr.challenge05.models.email.EmailDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "EmailTaskApi", description = "the EmailTaskApi")
// JWT Swagger Auth
@SecurityRequirement(name = "Authorization")

public interface EmailApi {

    @Operation(
            summary = "Fetch all upcoming Tasks",
            description = " get all tasks that due date < 24 h and status = todo , sorted by priority")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    public ResponseEntity sendMail();
}
