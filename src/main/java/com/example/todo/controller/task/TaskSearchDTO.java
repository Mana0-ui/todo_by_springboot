package com.example.todo.controller.task;

import java.util.List;
import java.util.Optional;

public record TaskSearchDTO(
        String summary,
        List<String> statusList
) {


    public boolean isChecked(String status){
        //検索時に、ステータスが選択されていたらtrueを返す。statusにはTODO,DOING,DONEのいずれかが入る
        return Optional.ofNullable(statusList)
                .map(l -> l.contains(status))
                //statusListがnullの場合はfalseを返す。
                .orElse(false);
    }

}
