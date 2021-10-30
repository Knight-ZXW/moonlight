-- 创建数据库
create database moonlight;
-- 进入 moonlight 数据库
-- \c moonlight
CREATE USER moonlight WITH PASSWORD 'moonlight'
create schema moonlight;

alter schema moonlight owner to moonlight;
