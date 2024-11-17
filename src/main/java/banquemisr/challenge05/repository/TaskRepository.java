package banquemisr.challenge05.repository;

import banquemisr.challenge05.models.Task;
import banquemisr.challenge05.models.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {


    String FILTER_CUSTOMERS_ON_FIRST_NAME_AND_LAST_NAME_QUERY = "select t from Task t where UPPER(t.title) like CONCAT('%',UPPER(?1),'%') and UPPER(t.description) like CONCAT('%',UPPER(?2),'%') " +
            " and t.status like CONCAT('%',?3,'%') " +
            " and t.dueDate = ?4";

    @Query(FILTER_CUSTOMERS_ON_FIRST_NAME_AND_LAST_NAME_QUERY)
    Page<Task> findByTitleLikeAndDescriptionLikeAndStatusLikeAndDueDateEquals(String taskTitle, String taskDescription, String Status, Date dueDate, Pageable pageable);

    List<Task> findByStatusEqualsAndDueDateBetweenOrderByPriorityAsc(TaskStatus Status, Date startDate, Date endDate);

}
