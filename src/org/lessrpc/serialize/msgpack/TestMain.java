package org.lessrpc.serialize.msgpack;

import java.io.IOException;

import org.lessrpc.common.info.ServiceInfo;
import org.msgpack.jackson.dataformat.MessagePackFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestMain {
	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper(new MessagePackFactory());
		ServiceInfo<Integer> service = new ServiceInfo<>("test", 1);
		byte[] bytes = objectMapper.writeValueAsBytes(service);
		JsonParser parser = objectMapper.getFactory().createParser(bytes);
		parser.setCodec(objectMapper);
		System.out.println(parser.readValueAs(ServiceInfo.class)); // =>
																	// komamitsu

	}
}
