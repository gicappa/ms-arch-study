package eu.cise;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OddSentencePrinter {

    @Incoming("to-odd")
    public void print(String sentence) {
        System.out.println(">> ODD: " + sentence);
    }
}
