package banquemisr.challenge05.swagger;

import banquemisr.challenge05.dtos.LoginUserDto;
import banquemisr.challenge05.dtos.RegisterUserDto;
import banquemisr.challenge05.models.LoginResponse;
import banquemisr.challenge05.models.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "AuthenticationApi", description = "the AuthenticationApi")
public interface AuthenticationApi {
    @Operation(summary = "Sign Up new User", description = "Add new user")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "successful operation"), @ApiResponse(responseCode = "409", description = "throws TaskAlreadyExistsException -> When Task already exists ")

    })
    public ResponseEntity<User> register(@RequestBody RegisterUserDto registerUserDto);
    @Operation(summary = "authenticate user (email & password)", description = "authenticate user and return Token")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "successful operation"), @ApiResponse(responseCode = "409", description = "throws TaskAlreadyExistsException -> When Task already exists ")

    })
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto);
}
