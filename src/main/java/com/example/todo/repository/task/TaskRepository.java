package com.example.todo.repository.task;

import com.example.todo.service.task.TaskEntity;
import com.example.todo.service.task.TaskSearchEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

// BeanとしてTaskRepositoryクラスが登録される
// @Mapperの場合はinterfaceにする必要がある
// DBへの操作を記述する
@Mapper
public interface TaskRepository {
    //検索ボックスの概要部分が空欄・nullでない場合は、summary LIKE以降の文が実行される
    @Select("""
            <script>
                SELECT id, summary, description, status
                FROM tasks
                <where>
                    <if test='condition.summary != null and !condition.summary.isBlank()'>
                       summary LIKE CONCAT('%' ,#{condition.summary}, '%')
                    </if>
                    <if test='condition.status != null and !condition.status.isEmpty()'>
                       AND status IN(
                          <foreach item='item' index='index' collection='condition.status' separator=','>
                            #{item}
                          </foreach>
                       )
                    </if>
                </where>
            </script>
            """)
    List<TaskEntity> select(@Param("condition") TaskSearchEntity condition);

    @Select("SELECT id, summary, description, status FROM tasks WHERE id = #{taskId}")
    Optional<TaskEntity> selectById(@Param("taskId") long taskId);

    @Insert("""
            INSERT INTO tasks (summary, description, status)
            VALUES (#{task.summary}, #{task.description}, #{task.status})
            """)
    void insert(@Param("task")TaskEntity newEntity);

    @Update("""
            UPDATE tasks
            SET
               summary = #{task.summary},
               description = #{task.description},
               status = #{task.status}
            WHERE
               id = #{task.id}
            """)
    void update(@Param("task")TaskEntity entity);

    @Delete("""
            DELETE from tasks
            WHERE id = #{taskId}
            """)
    void delete(long id);
}
