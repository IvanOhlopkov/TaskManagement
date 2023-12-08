package effective.mobile.TaskManagement.repository;

import effective.mobile.TaskManagement.entity.Task;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("SELECT t FROM Task t WHERE t.author = :authorUsername")
    List<Task> findTaskByAuthor(@Param("authorUsername") String authorUsername);

    @Query("SELECT t FROM Task t WHERE t.executor = :executorUsername")
    List<Task> findTaskByExecutor(@Param("executorUsername") String executorUsername);
}
