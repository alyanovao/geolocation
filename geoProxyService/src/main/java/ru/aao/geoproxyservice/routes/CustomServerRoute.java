package ru.aao.geoproxyservice.routes;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.http.conn.HttpHostConnectException;
import org.springframework.stereotype.Component;
import ru.aao.geoproxyservice.dto.Location;
import ru.aao.geoproxyservice.processors.CustomRequestProcessor;

@Slf4j
@Component
public class CustomServerRoute extends RouteBuilder {

    @Override
    public void configure() {
        from("direct:customServerRoute")
            .routeId("customServerRouteId")

                .process(new CustomRequestProcessor())
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .marshal().json(JsonLibrary.Jackson, Location.class)

                .doTry()
                    .toD("{{custom.server.schema}}:{{custom.server.host}}:{{custom.server.port}}{{custom.server.endpoint}}?" +
                            "&httpClientConfigurer=#selfSignedHttpClientConfigurer" +
                            "&bridgeEndpoint=true" +
                            "&throwExceptionOnFailure=false" +
                            "&connectionsPerRoute={{poolsize}}" +
                            "&httpClient.connectTimeout={{connectiontimeout}}" +
                            "&httpClient.socketTimeout={{sockettimeout}}")

                    .log("httpStatus: ${in.headers." + Exchange.HTTP_RESPONSE_CODE + "}")

                .doCatch(HttpHostConnectException.class)
                    .process(exchange -> {
                        val exception = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
                        log.error(exception.toString());
                    })
                .end()
        .end();
    }
}
