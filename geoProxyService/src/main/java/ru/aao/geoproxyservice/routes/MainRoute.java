package ru.aao.geoproxyservice.routes;

import org.apache.camel.LoggingLevel;
import org.apache.camel.Predicate;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import ru.aao.geoproxyservice.commons.Constant;
import ru.aao.geoproxyservice.processors.SetEndpointNameProcessor;

@Component
public class MainRoute extends RouteBuilder {

    public final Predicate saveLocation = simple("${exchangeProperty." + Constant.ENDPOINT_NAME + "}").isEqualToIgnoreCase(Constant.SAVE_LOCATION);
    public final Predicate getLocation = simple("${exchangeProperty." + Constant.ENDPOINT_NAME + "}").isEqualToIgnoreCase(Constant.GET_LOCATION);

    @Override
    public void configure() {
        errorHandler(noErrorHandler());

        from("direct:mainRoute")
            .routeId("mainRouteId")
                .process(new SetEndpointNameProcessor())
                .choice()
                    .when(saveLocation)
                        .to("direct:saveLocationRoute")
                    .when(getLocation)
                        .to("direct:getLocationRoute")
                .otherwise()
                    .log(LoggingLevel.ERROR, "error routing message")
                .endChoice()
        .end();
    }
}
