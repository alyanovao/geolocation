package ru.aao.geoproxyservice.processors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import ru.aao.geoproxyservice.commons.Constant;

public class SetEndpointNameProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        String url = exchange.getIn().getHeader(Exchange.HTTP_PATH, String.class);
        String endpointName = null;
        if (url.equals("/")) {
            endpointName = Constant.SAVE_LOCATION;
        } else if(url.startsWith("/getLocations")) {
            endpointName = Constant.GET_LOCATION;
        }
        exchange.setProperty(Constant.ENDPOINT_NAME, endpointName);
    }
}
