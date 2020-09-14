package eu.cise;

import org.apache.kafka.streams.kstream.ValueMapper;

public class SentenceProcessor implements ValueMapper<String, String> {

    @Override
    public String apply(String value) {
        System.out.println(">> PROC: " + value);
        return value.toUpperCase();
    }
}
