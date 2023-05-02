package ru.aao.geoproxyservice.processors;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import ru.aao.geoproxyservice.commons.HttpUtils;

@RequiredArgsConstructor
public class MaskLocationProcessor implements Processor {

    private final String latitude;
    private final String longitude;

    @Override
    public void process(Exchange exchange) throws Exception {
        val message = exchange.getIn();
        val properties = HttpUtils.getQueryParam(message.getHeader(Exchange.HTTP_QUERY, String.class));
        properties.remove("lon");
        properties.remove("lat");
        properties.put("lat", latitude);
        properties.put("lon", longitude);
        val param = properties.entrySet().stream()
                .map(e -> e.getKey() + "=" + e.getValue())
                .reduce((p1, p2) -> p1 + "&" + p2)
                .orElse("");
        message.setHeader(Exchange.HTTP_QUERY, param);
        message.setHeader(Exchange.HTTP_RAW_QUERY, param);
        message.removeHeader("lat");
        message.removeHeader("lon");
        message.setHeader("lat", latitude);
        message.setHeader("lon", longitude);
        exchange.setIn(message);
    }
}
