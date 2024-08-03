package com.example.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// http:localhost:8080/ → "Hello World!"を表示する
@Controller("/")
public class IndexController {

    // ハンドラーメソッド：どのようなURLを↓のメソッドに処理させるかを「Get」で指示する
    @GetMapping
    public String index(){
        return "index";
    }
}
