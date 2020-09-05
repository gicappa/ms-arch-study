package eu.cise;

import org.eclipse.microprofile.reactive.messaging.Incoming;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EvenSentencePrinter {

    @Incoming("to-even")
    public void print(String sentence) {
        System.out.println(">> EVEN: " + sentence);
    }
}
