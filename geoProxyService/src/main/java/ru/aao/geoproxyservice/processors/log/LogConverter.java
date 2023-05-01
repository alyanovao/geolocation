package ru.aao.geoproxyservice.processors.log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.joda.time.DateTime;
import ru.aao.geoproxyservice.commons.Constant;
import ru.aao.geoproxyservice.commons.MessageUtils;
import ru.aao.geoproxyservice.types.CustomMessage;
import ru.aao.geoproxyservice.types.log.Direction;
import ru.aao.geoproxyservice.types.log.LogRecord;

import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;


public class LogConverter implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JodaModule());
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		LogRecord record = new LogRecord();
		record.setSystem(exchange.getProperty(Constant.NAMEPROJECT, String.class));
		record.setTraceId(exchange.getProperty(Constant.LOG_ID, String.class));
		record.setSpanId(exchange.getProperty(Constant.CORRELATION_ID, String.class));
		record.setTimestamp(DateTime.now());

		record.setComponent(exchange.getProperty(Constant.LOG_COMPONENT, String.class));
		record.setOperation(exchange.getProperty(Constant.LOG_OPERATION, String.class));
		record.setDirection(exchange.getProperty(Constant.LOG_DIRECTION, Direction.class));
		record.setStep(exchange.getProperty(Constant.LOG_STEP, String.class));

		try {
			record.setHost(InetAddress.getLocalHost().getHostName());
		} catch (UnknownHostException e1) {
			record.setHost("unknown");
		}

		Object redelivery = exchange.getIn().getHeader(Constant.LOG_REDELIVERY) == null ? null
				: exchange.getIn().getHeader(Constant.LOG_REDELIVERY, String.class);
		Object body = exchange.getMessage().getBody() == null ? exchange.getIn().getBody() : exchange.getMessage().getBody();

		Object obj = redelivery == null ? body : redelivery;
		if (obj instanceof InputStream) {
			record.setData(new CustomMessage("body: " + MessageUtils.getStringFromInputStream((InputStream) obj), exchange.getIn().getHeaders().toString()));
		} else if (obj instanceof String) {
			record.setData(new CustomMessage(obj, exchange.getIn().getHeaders().toString()));
		} else {
			record.setData(mapper.writeValueAsString(obj));
		}

		exchange.getMessage().setBody(mapper.writeValueAsString(record));
		exchange.setProperty("record", record);
	}

}
