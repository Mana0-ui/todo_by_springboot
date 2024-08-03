package com.example.todo.controller.task;

import com.example.todo.service.task.TaskSearchEntity;
import com.example.todo.service.task.TaskStatus;

import java.util.List;
import java.util.Optional;

public record TaskSearchForm (
        String summary,
        List<String> status

){
    public TaskSearchEntity toEntity(){

        //status() == nullの場合、空のlistを返却する
        var statusEntityList = Optional.ofNullable(status())
                //searchFormがnullでない場合、ストリーム化しmapにする処理を行う
                .map(statusList -> statusList.stream().map(TaskStatus::valueOf).toList())
                //searchFormがnullの場合の処理
                .orElse(List.of());

        /*TaskFormで受け取った内容をTaskSearchEntityに詰め替える*/
        return new TaskSearchEntity(summary(), statusEntityList);

    }

    public TaskSearchDTO toDTO() {
        return new TaskSearchDTO(summary(), status());
    }
}
