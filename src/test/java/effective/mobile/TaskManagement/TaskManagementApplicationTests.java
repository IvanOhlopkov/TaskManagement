package effective.mobile.TaskManagement;

import com.fasterxml.jackson.databind.ObjectMapper;
import effective.mobile.TaskManagement.entity.RoleType;
import effective.mobile.TaskManagement.web.model.CreateUserRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Тесты TaskManagement")
@Tag("Tests")
class TaskManagementApplicationTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("Тест создания пользователя")
    void testRegisterUser() throws Exception {
        CreateUserRequest createUserRequest = CreateUserRequest.builder()
                .email("test@test.ru")
                .password("1234")
                .username("user")
                .roles(Collections.singleton(RoleType.ROLE_USER))
                .build();

        String body = this.objectMapper.writeValueAsString(createUserRequest);

        this.mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("User created"));
    }

}
