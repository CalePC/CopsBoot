package cpc.uv.copsboot.user_test;

import cpc.uv.copsboot.CopsbootControllerTest;
import cpc.uv.copsboot.infrastructure.security.WebSecurityConfiguration;
import cpc.uv.copsboot.user.*;
import cpc.uv.copsboot.user.web.UserRestController;
import com.c4_soft.springaddons.security.oauth2.test.annotations.WithJwt;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@CopsbootControllerTest(UserRestController.class)
@ExtendWith(RestDocumentationExtension.class)
@Import(CopsbootControllerDocumentationTestConfiguration.class)
@AutoConfigureRestDocs(outputDir = "target/generated-snippets") // << Agregado para generación correcta
public class UserRestControllerDocumentation {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService service;

    @Test
    void ownUserDetailsWhenNotLoggedInExample() throws Exception {
        mockMvc.perform(get("/api/users/me"))
                .andExpect(status().isUnauthorized())
                .andDo(document("own-details-unauthorized"));
    }

    @Test
    void authenticatedOfficerDetailsExample() throws Exception {
        mockMvc.perform(get("/api/users/me")
                        .with(jwt()
                                .jwt(builder -> builder.subject(UUID.randomUUID().toString()))
                                .authorities(new SimpleGrantedAuthority("ROLE_OFFICER"))))
                .andExpect(status().isOk())
                .andDo(document("own-details",
                        responseFields(
                                fieldWithPath("subject").description("The subject from the JWT token"),
                                subsectionWithPath("claims").description("The claims from the JWT token")
                        )));
    }

    @Test
    void createOfficerExample() throws Exception {
        UserId userId = new UserId(UUID.randomUUID());
        when(service.createUser(any(CreateUserParameters.class)))
                .thenReturn(new User(
                        userId,
                        "wim@example.com",
                        new AuthServerId(UUID.randomUUID()),
                        "c41536a5a8b93df14a7e5472a5322b51f76a6e7a9255c2c2e7e0d3a2c5b9d0"
                ));

        mockMvc.perform(post("/api/users")
                        .with(jwt()
                                .jwt(builder -> builder.subject(UUID.randomUUID().toString()))
                                .authorities(new SimpleGrantedAuthority("ROLE_OFFICER")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "mobileToken": "c41536a5a8b93df14a7e5472a5322b51f76a6e7a9255c2c2e7e0d3a2c5b9d0"
                                }
                                """))
                .andExpect(status().isCreated())
                .andDo(document("create-user",
                        requestFields(
                                fieldWithPath("mobileToken").description("The unique mobile token of the device (for push notifications).")
                        ),
                        responseFields(
                                fieldWithPath("userId").description("The unique id of the user."),
                                fieldWithPath("email").description("The email address of the user."),
                                fieldWithPath("authServerId").description("The id of the user on the authorization server."),
                                fieldWithPath("mobileToken").description("The unique mobile token of the device (for push notifications).")
                        )));
    }
}
