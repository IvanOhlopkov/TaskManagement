package effective.mobile.TaskManagement.web.model;

import effective.mobile.TaskManagement.entity.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserRequest {

    private String username;

    private String email;

    private Set<RoleType> roles;

    private String password;
}
