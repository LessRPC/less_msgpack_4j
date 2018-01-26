package test.java.org.lessrpc.serialize.msgpack;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import org.lessrpc.common.info.EnvironmentInfo;
import org.lessrpc.common.info.SerializationFormat;
import org.lessrpc.common.info.ServiceDescription;
import org.lessrpc.common.info.ServiceInfo;
import org.lessrpc.common.info.ServiceLocator;
import org.lessrpc.common.info.ServiceProviderInfo;
import org.lessrpc.common.info.ServiceRequest;
import org.lessrpc.common.info.ServiceSupportInfo;
import org.lessrpc.common.info.responses.ExecuteRequestResponse;
import org.lessrpc.common.info.responses.IntegerResponse;
import org.lessrpc.common.info.responses.ProviderInfoResponse;
import org.lessrpc.common.info.responses.ServiceResponse;
import org.lessrpc.common.info.responses.ServiceSupportResponse;
import org.lessrpc.common.info.responses.TextResponse;
import org.lessrpc.common.serializer.Serializer;
import org.lessrpc.serialize.msgpack.MsgPackSerializer;

public class MsgPackSerializerTest {

	private static ServiceProviderInfo spInfo;

	private static ServiceInfo<Integer> service;

	private static int serverPort = 4040;

	@BeforeClass
	public static void init() throws Exception {
		// setting service provider info
		spInfo = new ServiceProviderInfo("127.0.0.1", serverPort, EnvironmentInfo.currentEnvInfo());

		// setting service
		service = new ServiceInfo<Integer>("add", 1);
	}

	@Test
	public void testSeviceProviderInfoResponse() {

		ProviderInfoResponse response = new ProviderInfoResponse(200,
				new ServiceProviderInfo("127.0.0.1", 4040, EnvironmentInfo.currentEnvInfo()));

		Serializer serializer = new MsgPackSerializer();
		ProviderInfoResponse response2 = null;
		try {
			response2 = serializer.deserialize(serializer.serialize(response, ProviderInfoResponse.class),
					ProviderInfoResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		assertEquals(response.getStatus(), response2.getStatus());

		assertEquals(response.getContent(), response2.getContent());
	}

	@Test
	public void testSupportResponse() {

		ServiceSupportResponse response = new ServiceSupportResponse(200, new ServiceSupportInfo(service, spInfo,
				new SerializationFormat[] { SerializationFormat.defaultFotmat() }));

		Serializer serializer = new MsgPackSerializer();
		ServiceSupportResponse response2 = null;
		try {
			response2 = serializer.deserialize(serializer.serialize(response, ServiceSupportResponse.class),
					ServiceSupportResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		assertEquals(response.getStatus(), response2.getStatus());

		assertEquals(response.getContent(), response2.getContent());
	}

	@Test
	public void testIntegerResponse() {

		IntegerResponse response = new IntegerResponse(200, 1);

		Serializer serializer = new MsgPackSerializer();
		IntegerResponse response2 = null;
		try {
			response2 = serializer.deserialize(serializer.serialize(response, IntegerResponse.class),
					IntegerResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		assertEquals(response.getStatus(), response2.getStatus());

		assertEquals(response.getContent(), response2.getContent());
	}

	@Test
	public void testTextResponse() {

		TextResponse response = new TextResponse(200, "test");

		Serializer serializer = new MsgPackSerializer();
		TextResponse response2 = null;
		try {
			response2 = serializer.deserialize(serializer.serialize(response, TextResponse.class), TextResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		assertEquals(response.getStatus(), response2.getStatus());

		assertEquals(response.getContent(), response2.getContent());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testExecuteResponse() {

		ExecuteRequestResponse<ServiceInfo<Integer>> response = new ExecuteRequestResponse<ServiceInfo<Integer>>(200,
				new ServiceResponse<>(service, service, 10));
		ServiceDescription<Integer> desc = new ServiceDescription<>(service,
				new Class[] { Integer.class, String.class, service.getClass() }, ServiceInfo.class);

		Serializer serializer = new MsgPackSerializer();
		ExecuteRequestResponse<Integer> response2 = null;
		try {
			response2 = serializer.deserialize(serializer.serialize(response, ExecuteRequestResponse.class),
					ExecuteRequestResponse.class,ServiceLocator.create(desc));
		} catch (Exception e) {
			e.printStackTrace();
		}

		assertEquals(response.getStatus(), response2.getStatus());

		assertEquals(response.getContent(), response2.getContent());
	}

	@Test
	public void testServiceRequest() {

		ServiceRequest req = new ServiceRequest(service, EnvironmentInfo.currentEnvInfo(), 1,
				new Object[] { 1, "asd",service });
		ServiceDescription<Integer> desc = new ServiceDescription<>(service,
				new Class[] { Integer.class, String.class, service.getClass() }, Integer.class);

		Serializer serializer = new MsgPackSerializer();
		ServiceRequest req2 = null;
		try {
			req2 = serializer.deserialize(serializer.serialize(req, ServiceRequest.class),
					ServiceRequest.class, ServiceLocator.create(desc));
		} catch (Exception e) {
			e.printStackTrace();
		}

		assertEquals(req.getService(), req2.getService());

		assertEquals(req.getArgs().length, req2.getArgs().length);
		
		
		for (int i = 0; i < req.getArgs().length; i++) {
			assertEquals(req.getArgs()[i], req2.getArgs()[i]);
		}
	}
	
	

}
