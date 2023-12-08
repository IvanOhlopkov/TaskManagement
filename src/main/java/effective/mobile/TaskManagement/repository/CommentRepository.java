package effective.mobile.TaskManagement.repository;

import effective.mobile.TaskManagement.entity.Comment;
import effective.mobile.TaskManagement.entity.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c FROM Comment c WHERE c.username = :username")
    List<Comment> findCommentByUserId(@Param("username") String username);
}
