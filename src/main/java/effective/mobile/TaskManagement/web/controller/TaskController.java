package effective.mobile.TaskManagement.web.controller;

import effective.mobile.TaskManagement.entity.Comment;
import effective.mobile.TaskManagement.entity.Task;
import effective.mobile.TaskManagement.entity.User;
import effective.mobile.TaskManagement.repository.TaskRepository;
import effective.mobile.TaskManagement.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Task", description = "Task service API")
@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class TaskController {

    private final TaskRepository taskRepository;

    private final TaskService taskService;

    @Operation(summary = "Create task", tags = "Task")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Task created",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Task.class)
                            )
                    }
            )
    })
    @PostMapping()
    public ResponseEntity<String> createTask(@RequestHeader("Authorization") String token, @RequestBody Task taskRequest) {
        return taskService.createTask(token, taskRequest);
    }

    @Operation(summary = "Edit task", tags = "Task")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Task edited / Status change",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Task.class)
                            )
                    }
            )
    })
    @PatchMapping("/{id}")
    public ResponseEntity<String> editTask(@RequestHeader("Authorization") String token, @PathVariable Long id, @RequestBody Task taskRequest) {
        return taskService.editTask(token, id, taskRequest);
    }

    @Operation(summary = "Delete task", tags = "Task")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Task deleted",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Task.class)
                            )
                    }
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        return taskService.deleteTask(token, id);
    }

    @Operation(summary = "Get task", tags = "Task")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Task.class)
                            )
                    }
            )
    })
    @GetMapping("/{id}")
    public Task getTask(@PathVariable Long id) {
        return taskService.getTask(id);
    }

    @Operation(summary = "Create comment", tags = "comment")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Comment added",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Comment.class)
                            )
                    }
            )
    })
    @PatchMapping("/{id}/comment")
    public ResponseEntity<String> addComment(@RequestHeader("Authorization") String token, @PathVariable Long id, @RequestBody Comment commentRequest) {
        return taskService.addComment(token, id, commentRequest);
    }

    @Operation(summary = "Get all task", tags = "Task")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Task created",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Task.class))
                            )
                    }
            )
    })
    @GetMapping()
    public ResponseEntity<String> getAllTaskAndComments(@RequestBody User user) {
        return taskService.getAllTaskAndComments(user);
    }

}
