package cpc.uv.copsboot.user_test;

import cpc.uv.copsboot.user.web.UserRestController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserRestController.class)
@AutoConfigureMockMvc
class UserRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetCurrentUserReturns200() throws Exception {
        mockMvc.perform(
                        get("/api/users/me")
                                .with(SecurityMockMvcRequestPostProcessors.jwt())  // <-- simula un JWT
                )
                .andExpect(status().isOk());
    }
}
