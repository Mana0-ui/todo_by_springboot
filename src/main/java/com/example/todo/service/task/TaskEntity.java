package com.example.todo.service.task;
// ドメイン層に所属するオブジェクト
public record TaskEntity(
        Long id,
        String summary,
        String description,
        TaskStatus status
) {

}
