package ru.aao.geoproxyservice.routes;

import lombok.val;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.http.conn.HttpHostConnectException;
import org.springframework.stereotype.Component;

@Component
public class PrimaryServerRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("direct:primaryServerRoute")
            .routeId("primaryServerRouteId")
            .doTry()
                .toD("{{location.server.schema}}:{{location.server.host}}:{{location.server.port}}?" +
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
