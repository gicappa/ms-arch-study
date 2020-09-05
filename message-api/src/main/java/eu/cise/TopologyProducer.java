package eu.cise;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class TopologyProducer {

    @Produces
    public Topology buildTopology() {
        StreamsBuilder builder = new StreamsBuilder();

        KStream<String, String>[] branches = builder.stream(
                "submitted-messages",
                Consumed.with(Serdes.String(), Serdes.String()))
                .mapValues(new SentenceProcessor())
                .branch(
                        (key, value) -> value.length() > 10,
                        (key, value) -> value.length() <= 10
                );

        branches[0].to("to-even", Produced.with(Serdes.String(), Serdes.String()));
        branches[1].to("to-odd", Produced.with(Serdes.String(), Serdes.String()));

        Topology topology = builder.build();
        System.out.println(topology.describe());
        return topology;
    }
}