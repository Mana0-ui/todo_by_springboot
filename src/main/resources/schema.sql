--アプリ起動時にこのファイルを実行してくれる
--テーブル・インデックスの定義を書く
CREATE TABLE tasks
(
id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
summary VARCHAR(256) NOT NULL,
description TEXT,
status VARCHAR(256) NOT NULL
);