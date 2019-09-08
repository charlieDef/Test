package com.materials.client.utils;

public class KeyValue<T> {

	private String key;
	private T object;

	public KeyValue(String key, T object) {
		this.key = key;
		this.object = object;
	}

	public String getKey() {
		return key;
	}

	public T getObject() {
		return object;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setObject(T object) {
		this.object = object;
	}
}
