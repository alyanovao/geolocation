package ru.aao.geoproxyservice.aggregator;

import lombok.val;
import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;

import java.util.Objects;

public class MulticastAggregation implements AggregationStrategy {

    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        if (Objects.isNull(oldExchange)) {
            return newExchange;
        }
        val messageOld = oldExchange.getIn();
        val messageNew = newExchange.getIn();
        return oldExchange;
    }
}
