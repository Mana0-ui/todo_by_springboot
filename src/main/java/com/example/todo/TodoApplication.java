package com.example.todo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TodoApplication {

	public static void main(String[] args) {
		// Springアプリケーションの起動を行う
		// 引数にクラスをメインクラスとして認識する
		SpringApplication.run(TodoApplication.class, args);
	}

}
