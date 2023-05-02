package ru.aao.geoproxyservice.routes;

import lombok.val;
import org.apache.camel.Predicate;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.aao.geoproxyservice.commons.Constant;
import ru.aao.geoproxyservice.processors.MaskLocationProcessor;
import ru.aao.geoproxyservice.processors.fault.RestFaultResponseBuilder;
import ru.aao.geoproxyservice.processors.log.LogIdGenerator;
import ru.aao.geoproxyservice.types.log.Direction;

@Service
public class GeoProxyServiceFacade extends RouteBuilder {

    @Value("${latitude}")
    private String latitude;

    @Value("${longitude}")
    private String longitude;

    public final Predicate logging = simple("{{logging.enable}}").isEqualToIgnoreCase("true");
    public final Predicate presentationMode = simple("{{presentation}}").isEqualToIgnoreCase(constant("true"));

    @Override
    public void configure() {
        errorHandler(defaultErrorHandler());

        onException(Exception.class)
                .handled(true)
                .log("error handler")
                .process(new RestFaultResponseBuilder())
                .setProperty(Constant.LOG_LEVEL, constant("ERROR"))
                .setProperty(Constant.LOG_STEP, constant("Exception"))
                .wireTap("direct:logging").copy(true)
        .end();


        from("netty-http:http://{{geo.proxy.host}}:{{geo.proxy.port}}" +
                "?matchOnUriPrefix=true" +
                //https://issues.apache.org/jira/browse/CAMEL-8031 -- должно быть 2 x cpu_core, но не работает
                "&maximumPoolSize={{maximum-pool-size}}" +
                "&usingExecutorService=false")
            .routeId(Constant.FACADE_ROUTE_ID)

            .process(new LogIdGenerator())
            .filter(presentationMode)
                .process(new MaskLocationProcessor(latitude, longitude))
            .end()
            .filter(logging)
                .setProperty(Constant.LOG_LEVEL, constant("INFO"))
                .setProperty(Constant.LOG_COMPONENT, constant("Facade"))
                .setProperty(Constant.LOG_OPERATION, constant("Request"))
                .setProperty(Constant.LOG_DIRECTION, constant(Direction.Inbound))
                .setProperty(Constant.LOG_STEP, constant("Receive"))
                .wireTap("direct:logging").copy(true)
            .end()

            .to("direct:mainRoute")

            .filter(logging)
                .setProperty(Constant.LOG_LEVEL, constant("INFO"))
                .setProperty(Constant.LOG_COMPONENT, constant("Facade"))
                .setProperty(Constant.LOG_OPERATION, constant("Response"))
                .setProperty(Constant.LOG_DIRECTION, constant(Direction.Outbound))
                .setProperty(Constant.LOG_STEP, constant("Send"))
                .wireTap("direct:logging").copy(true)
            .end()
        .end();
    }
}
