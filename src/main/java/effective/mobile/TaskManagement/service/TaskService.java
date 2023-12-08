package effective.mobile.TaskManagement.service;

import effective.mobile.TaskManagement.entity.Comment;
import effective.mobile.TaskManagement.entity.Task;
import effective.mobile.TaskManagement.entity.TaskStatus;
import effective.mobile.TaskManagement.entity.User;
import effective.mobile.TaskManagement.exception.EntityNotFoundException;
import effective.mobile.TaskManagement.repository.CommentRepository;
import effective.mobile.TaskManagement.repository.TaskRepository;
import effective.mobile.TaskManagement.repository.UserRepository;
import effective.mobile.TaskManagement.security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    @Autowired
    private final TaskRepository taskRepository;

    private final UserRepository userRepository;

    @Autowired
    private final CommentRepository commentRepository;

    private final JwtUtils jwtUtils;

    public ResponseEntity<String> createTask(String token, Task newTask) {

        var task = Task.builder()
                .title(newTask.getTitle())
                .description(newTask.getDescription())
                .taskPriority(newTask.getTaskPriority())
                .executor(newTask.getExecutor())
                .build();

        task.setAuthor(getUsername(token));
        task.setTaskStatus(TaskStatus.PENDING);
        taskRepository.save(task);

        return ResponseEntity.status(HttpStatus.OK).body("Task created");
    }

    public ResponseEntity<String> editTask(String token, Long id, Task changeTask) {

        Task task = getTask(id);
        String username = getUsername(token);

        if (username.equals(task.getAuthor())) {
            task.setTitle(changeTask.getTitle());
            task.setDescription(changeTask.getDescription());
            task.setTaskStatus(changeTask.getTaskStatus());
            task.setTaskPriority(changeTask.getTaskPriority());
            task.setExecutor(changeTask.getExecutor());
        }

        if (username.equals(task.getExecutor())) {
            task.setTaskStatus(changeTask.getTaskStatus());
            return ResponseEntity.status(HttpStatus.OK).body("Status change");
        }

        taskRepository.save(task);

        return ResponseEntity.status(HttpStatus.OK).body("Task edited");
    }

    public ResponseEntity<String> deleteTask(String token, Long id) {

        Task task = getTask(id);
        String username = getUsername(token);

        if (username.equals(task.getAuthor())) {
            taskRepository.delete(task);
            return ResponseEntity.status(HttpStatus.OK).body("Task deleted");
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not enough rights for delete");
    }

    public Task getTask(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));
    }

    public ResponseEntity<String> getAllTaskAndComments(User user) {

        List<Task> taskListAuthor = taskRepository.findTaskByAuthor(user.getUsername()) ;
        List<Task> taskListExecutor = taskRepository.findTaskByExecutor(user.getUsername());
        List<Comment> commentList = commentRepository.findCommentByUserId(user.getUsername());

        StringBuilder builder = new StringBuilder();
        builder.append("\nTask author: ").append(user.getUsername()).append("\n");
        taskListAuthor.forEach(builder::append);
        builder.append("\nTask executor: ").append(user.getUsername()).append("\n");
        taskListExecutor.forEach(builder::append);
        builder.append("\nComment: ").append(user.getUsername()).append("\n");
        commentList.forEach(builder::append);

        return ResponseEntity.status(HttpStatus.OK).body(builder.toString());
    }

    public ResponseEntity<String> addComment(String token, Long id, Comment commentRequest) {

        var comment = Comment.builder()
                .comment(commentRequest.getComment())
                .build();
        comment.setUsername(getUser(token).getUsername());
        comment.setTask(getTask(id));

        commentRepository.save(comment);

        return ResponseEntity.status(HttpStatus.OK).body("Comment added");
    }

    private String getEmail(String token) {
        return jwtUtils.getEmail(token.substring(7));
    }

    private String getUsername(String token) {
        Optional<User> userOptional = userRepository.findByEmail(getEmail(token));
        if (userOptional.isEmpty()) {
            throw new NoSuchElementException("Username not found");
        }
        User user = userOptional.get();
        return user.getUsername();
    }

    private User getUser(String token) {
        Optional<User> userOptional = userRepository.findByEmail(getEmail(token));
        if (userOptional.isEmpty()) {
            throw new NoSuchElementException(("User not found"));
        }
        return userOptional.get();
    }
}
