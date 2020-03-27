package com.example.demo.component;

import com.example.demo.entity.Person;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

@JsonComponent
public class CoustumizedSerilizer {

	@Autowired
	private ObjectInputStream ois;

	@Autowired
	private ObjectOutputStream oos;

	@Autowired
	private Logger logger= LoggerFactory.getLogger(CoustumizedSerilizer.class);

	public class Serializer extends JsonSerializer<Person>{

		@Override
		public void serialize(Person person, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
			oos.defaultWriteObject();
		}
	}

	public class Deserializer extends JsonSerializer<Person>{

		@Override
		public void serialize(Person person, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
			try {
				ois.defaultReadObject();
			} catch (ClassNotFoundException e) {
				logger.error("an error has occured due to IOException");
				e.printStackTrace();
			}
		}
	}
}
