package com.example.demo;

public interface CustomizedSave<T> {
	<S extends T> S save(S entity);
}