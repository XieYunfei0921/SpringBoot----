package com.example.demo.dao;

import com.example.demo.CustomizedSave;

public class CustomizedSaveImpl<T> implements CustomizedSave<T> {
	@Override
	public <S extends T> S save(S entity) {
		// write implement
		return null;
	}
}
