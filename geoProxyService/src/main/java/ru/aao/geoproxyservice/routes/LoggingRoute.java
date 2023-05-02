package ru.aao.geoproxyservice.routes;

import org.apache.camel.ExchangePattern;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Predicate;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import ru.aao.geoproxyservice.commons.Constant;
import ru.aao.geoproxyservice.processors.log.LogConverter;

@Component
public class LoggingRoute extends RouteBuilder {

    public final Predicate trace = exchangeProperty(Constant.LOG_LEVEL).isEqualToIgnoreCase("TRACE");
    public final Predicate debug = exchangeProperty(Constant.LOG_LEVEL).isEqualToIgnoreCase("DEBUG");
    public final Predicate info = exchangeProperty(Constant.LOG_LEVEL).isEqualToIgnoreCase("INFO");
    public final Predicate warn = exchangeProperty(Constant.LOG_LEVEL).isEqualToIgnoreCase("WARN");
    public final Predicate error = exchangeProperty(Constant.LOG_LEVEL).isEqualToIgnoreCase("ERROR");
    public final Predicate off = exchangeProperty(Constant.LOG_LEVEL).isEqualToIgnoreCase("OFF");

    @Override
    public void configure() {
        errorHandler(noErrorHandler());

        from("direct:logging")
                .routeId("loggingRouteId")
                .setExchangePattern(ExchangePattern.InOnly)
                .process(new LogConverter())
                .choice()
                    .when(trace)
                        .log(LoggingLevel.TRACE, Constant.NAMEPROJECT, "${body}")
                    .when(debug)
                        .log(LoggingLevel.DEBUG, Constant.NAMEPROJECT, "${body}")
                    .when(info)
                        .log(LoggingLevel.INFO, Constant.NAMEPROJECT, "${body}")
                    .when(warn)
                        .log(LoggingLevel.WARN, Constant.NAMEPROJECT, "${body}")
                    .when(error)
                        .log(LoggingLevel.ERROR, Constant.NAMEPROJECT, "${body}")
                    .when(off)
                        .log(LoggingLevel.OFF, Constant.NAMEPROJECT, "${body}")
                    .otherwise()
                        .log("Unknown level :: ${exchangeProperty(" + Constant.LOG_LEVEL + ")}")
                        .log(LoggingLevel.OFF, Constant.NAMEPROJECT, "$body}")
        .end();
    }
}
