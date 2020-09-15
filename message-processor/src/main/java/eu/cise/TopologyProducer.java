package eu.cise;


import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Produced;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class TopologyProducer {

    private final Logger logger = LoggerFactory.getLogger("ms:topology-producer");

    @Produces
    public Topology buildTopology() {
        StreamsBuilder builder = new StreamsBuilder();

        builder.stream(
                "validated-messages",
                Consumed.with(Serdes.String(), Serdes.String()))
                .mapValues(new SentenceProcessor())
                .to("transferred-messages", Produced.with(Serdes.String(), Serdes.String()));

        Topology topology = builder.build();

        logger.info(topology.describe().toString());

        return topology;
    }
}