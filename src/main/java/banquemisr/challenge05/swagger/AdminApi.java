package banquemisr.challenge05.swagger;

import banquemisr.challenge05.dtos.RegisterUserDto;
import banquemisr.challenge05.models.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "AdminApi", description = "the Admin Apis")
// JWT Swagger Auth
@SecurityRequirement(name = "Authorization")

public interface AdminApi {
    @Operation(summary = "Add Admin User", description = "Add Admin User")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "successful operation")})

    public ResponseEntity<User> createAdministrator(@RequestBody RegisterUserDto registerUserDto);
}
