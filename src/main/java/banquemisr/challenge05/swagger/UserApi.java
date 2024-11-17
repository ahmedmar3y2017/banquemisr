package banquemisr.challenge05.swagger;

import banquemisr.challenge05.models.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Users", description = "the User Apis")
// JWT Swagger Auth
@SecurityRequirement(name = "Authorization")

public interface UserApi {
    @Operation(summary = "Fetch authenticated User", description = "authenticatedUser")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "successful operation")})
    public ResponseEntity<User> authenticatedUser();

    @Operation(summary = "Fetch All Users (admin , super admin)", description = "Fetch All Users (admin , super admin)")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "successful operation")})
    public ResponseEntity<List<User>> allUsers();
}
