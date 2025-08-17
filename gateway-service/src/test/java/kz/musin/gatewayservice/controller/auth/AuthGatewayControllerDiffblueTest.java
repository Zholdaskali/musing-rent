package kz.musin.gatewayservice.controller.auth;

import kz.musin.gatewayservice.dto.ApiResponse;
import kz.musin.gatewayservice.dto.auth.request.RegisterRequestDto;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class AuthGatewayControllerDiffblueTest {
    /**
     * Test {@link AuthGatewayController#register(RegisterRequestDto)}.
     *
     * <p>Method under test: {@link AuthGatewayController#register(RegisterRequestDto)}
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
        AuthGatewayController authGatewayController = null;
        RegisterRequestDto request = null;

        // Act
        ApiResponse<?> actualRegisterResult = authGatewayController.register(request);

        // Assert
        // TODO: Add assertions on result
    }
}
