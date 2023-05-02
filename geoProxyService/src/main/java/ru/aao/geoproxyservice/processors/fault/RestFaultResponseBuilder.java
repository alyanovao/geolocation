package ru.aao.geoproxyservice.processors.fault;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.http.entity.ContentType;
import ru.aao.geoproxyservice.commons.ExceptionUtils;
import ru.aao.geoproxyservice.exception.ApplicationException;
import ru.aao.geoproxyservice.exception.error.Error;
import ru.aao.geoproxyservice.exception.error.ErrorDetail;

public class RestFaultResponseBuilder implements Processor {

    @Override
    public void process(Exchange exchange) {
        int status = 401;

        Exception exception = ExceptionUtils.exceptionExtractor(exchange);
        Error error;
        if (exception != null) {
            error = new Error();
            error.setCode(status);
            error.setMessage(exception.getMessage());
        } else {
            error = new Error(ErrorDetail.Inner_error, "");
        }
        exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, status);
        exchange.getIn().setHeader(Exchange.CONTENT_TYPE, ContentType.APPLICATION_JSON);
        try {
            exchange.getIn().setBody(new ObjectMapper().writeValueAsString(error));
        } catch (JsonProcessingException e) {
            throw new ApplicationException(e);
        }
    }
}
