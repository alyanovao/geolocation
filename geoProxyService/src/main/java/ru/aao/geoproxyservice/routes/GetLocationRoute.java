package ru.aao.geoproxyservice.routes;

import lombok.val;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.http.conn.HttpHostConnectException;
import org.springframework.stereotype.Component;
import ru.aao.geoproxyservice.commons.Constant;

@Component
public class GetLocationRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("direct:getLocationRoute")
            .routeId("getLocationRouteId")
            .doTry()
                .toD("{{consumer.server.schema}}:{{consumer.server.host}}:" +
                    "{{consumer.server.port}}" +
                    "${in.headers." + Exchange.HTTP_PATH + "}?" +
                    "&httpClientConfigurer=#selfSignedHttpClientConfigurer" +
                    "&bridgeEndpoint=true" +
                    "&throwExceptionOnFailure=false" +
                    "&connectionsPerRoute={{poolsize}}" +
                    "&httpClient.connectTimeout={{connectiontimeout}}" +
                    "&httpClient.socketTimeout={{sockettimeout}}")
                .log("httpStatus :: ${in.headers." + Exchange.HTTP_RESPONSE_CODE + "}")
            .doCatch(HttpHostConnectException.class)
                .process(exchange -> {
                    val exception = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
                    log.error(exception.toString());
                })
            .end()
        .end();
    }
}
