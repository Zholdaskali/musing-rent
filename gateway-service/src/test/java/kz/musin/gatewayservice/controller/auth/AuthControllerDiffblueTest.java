package kz.musin.gatewayservice.controller.auth;

import kz.musin.gatewayservice.dto.ApiResponse;
import kz.musin.gatewayservice.dto.auth.request.RegisterRequestDto;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class AuthControllerDiffblueTest {
    /**
     * Test {@link AuthController#register(RegisterRequestDto)}.
     *
     * <p>Method under test: {@link AuthController#register(RegisterRequestDto)}
     */
    @Test
    @DisplayName("Test register(RegisterRequestDto)")
    @Disabled("TODO: Complete this test")
    @Tag("MaintainedByDiffblue")
    void testRegister() {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Diffblue AI was unable to find a test

        // Arrange
        // TODO: Populate arranged inputs
        AuthController authController = null;
        RegisterRequestDto request = null;

        // Act
        ApiResponse<?> actualRegisterResult = authController.register(request);

        // Assert
        // TODO: Add assertions on result
    }
}
