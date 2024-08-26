--liquibase formatted sql

--changeset u1406:1 labels:v0.0.1
CREATE TYPE "exercise_visibility_type" AS ENUM ('public', 'owner', 'group');

CREATE TYPE "muscle_group_type" AS ENUM ('chest', 'back', 'legs', 'arms', 'shoulders', 'abs', 'none');

CREATE TYPE "exercise_importance_type" AS ENUM ('low', 'medium', 'high');

CREATE TABLE "exercises" (
                             "id" text primary key constraint exercises_id_length_ctr check (length("id") < 64),
                             "name" text constraint exercises_name_length_ctr check (length(name) < 128),
                             "description" text constraint exercises_description_length_ctr check (length(description) < 4096),
                             "visibility" exercise_visibility_type not null,
                             "owner_id" text not null constraint exercises_owner_id_length_ctr check (length(owner_id) < 64),
                             "lock" text not null constraint exercises_lock_length_ctr check (length(lock) < 64),
                             "importance" exercise_importance_type not null,
                             "muscle_group" muscle_group_type not null,
                             "last_performed" text,
                             "next_reminder" text,
                             "added_on" text,
                             "append" text
);

CREATE INDEX exercises_owner_id_idx on "exercises" using hash ("owner_id");
CREATE INDEX exercises_visibility_idx on "exercises" using hash ("visibility");