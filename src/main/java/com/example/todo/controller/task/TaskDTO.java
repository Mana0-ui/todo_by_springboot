package com.example.todo.controller.task;

import com.example.todo.service.task.TaskEntity;

// Data Transfer Object
// プレゼンテーション層に所属するオブジェクト
public record TaskDTO(
    long id,
    String summary,
    String description,
    String status
){
    // TaskEntityをTaskDTOに変換するメソッド
    public static TaskDTO toDTO(TaskEntity entity) {
        // 引数に渡されたEntityを使って、DTOを初期化する
        return new TaskDTO(
                entity.id(),
                entity.summary(),
                entity.description(),
                entity.status().name()
        );
    }
}

