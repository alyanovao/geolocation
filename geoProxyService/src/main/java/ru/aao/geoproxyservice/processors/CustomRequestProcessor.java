package ru.aao.geoproxyservice.processors;

import lombok.val;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.util.NumberUtils;
import ru.aao.geoproxyservice.commons.HttpUtils;
import ru.aao.geoproxyservice.dto.Location;

public class CustomRequestProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        val message = exchange.getIn();
        String query = message.getHeader(Exchange.HTTP_QUERY, String.class);
        val params = HttpUtils.getQueryParam(query);

        val clientId = NumberUtils.parseNumber(params.get("id"), Long.class);
        val timestamp = NumberUtils.parseNumber(params.get( "timestamp"), Long.class);
        val latitude = NumberUtils.parseNumber(params.get("lat"), Double.class);
        val longitude = NumberUtils.parseNumber(params.get("lon"), Double.class);
        val speed = NumberUtils.parseNumber(params.get("speed"), Double.class);
        val bearing = NumberUtils.parseNumber(params.get("bearing"), Double.class);
        val altitude = NumberUtils.parseNumber(params.get("altitude"), Double.class);
        val accuracy = NumberUtils.parseNumber(params.get("accuracy"), Double.class);
        val batteryLevel = NumberUtils.parseNumber(params.get("batt"), Double.class);
        val userAgent = message.getHeader("User-Agent", String.class);

        Location location = Location.builder().clientId(clientId)
                .timestamp(timestamp)
                .latitude(latitude)
                .longitude(longitude)
                .speed(speed)
                .bearing(bearing)
                .altitude(altitude)
                .accuracy(accuracy)
                .batteryLevel(batteryLevel)
                .userAgent(userAgent)
                .build();

        message.setBody(location);
        exchange.setIn(message);
    }
}
