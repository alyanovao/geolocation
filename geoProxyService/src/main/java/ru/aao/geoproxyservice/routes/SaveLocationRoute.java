package ru.aao.geoproxyservice.routes;

import org.apache.camel.LoggingLevel;
import org.apache.camel.Predicate;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import ru.aao.geoproxyservice.aggregator.MulticastAggregation;

@Component
public class SaveLocationRoute extends RouteBuilder {

    public final Predicate originalOnly = simple("{{geo.proxy.route}}").isEqualToIgnoreCase("original");
    public final Predicate customOnly = simple("{{geo.proxy.route}}").isEqualToIgnoreCase("custom");
    public final Predicate all = simple("{{geo.proxy.route}}").isEqualToIgnoreCase("all");

    @Override
    public void configure() {
        errorHandler(noErrorHandler());

        from("direct:saveLocationRoute")
            .routeId("saveLocationRouteId")
            .choice()
                .when(originalOnly)
                    .to("direct:primaryServerRoute")
                .when(customOnly)
                    .to("direct:customServerRoute")
                .when(all)
                    .multicast(new MulticastAggregation()).parallelProcessing()
                        .to("direct:primaryServerRoute")
                        .to("direct:customServerRoute")
                    .end()
            .endChoice()
                .otherwise()
                    .log(LoggingLevel.ERROR, "error routing message")
            .end()
        .end();
    }
}
