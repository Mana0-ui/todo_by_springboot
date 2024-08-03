package com.example.todo.service.task;

import com.example.todo.repository.task.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

// TaskEntityを返すことで、TaskDTOの変更の影響を受けなくなる
// @ServiceでDI対象に登録する
@Service
@RequiredArgsConstructor // taskRepositoryを初期化する
public class TaskService {

    private final TaskRepository taskRepository;

    public List<TaskEntity> find(TaskSearchEntity searchEntity){
        return taskRepository.select(searchEntity);
    }

    // 戻り値がnullの可能性がある場合、Optional型にすることで、TaskEntityが戻ってこなかった場合にnullを返却する
    // nullPointerExceptionの防止になる
    public Optional<TaskEntity> findById(long taskId) {
        return taskRepository.selectById(taskId);
    }

    //@Transactionalで 処理が失敗した時にinsertされないようにする（ロールバックできるようにする）
    @Transactional
    public void create(TaskEntity newEntity) {
        taskRepository.insert(newEntity);
    }

    @Transactional
    public void update(TaskEntity entity) {
    taskRepository.update(entity);
    }

    @Transactional
    public void delete(long id) {
        taskRepository.delete(id);
    }
}
