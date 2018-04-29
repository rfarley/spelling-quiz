package handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.LaunchRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.request.Predicates;
import utils.DictionaryUtils;

import java.util.*;

public class LaunchRequestHandler implements RequestHandler {

    private Map<String, Object> attributesMap;

    public LaunchRequestHandler() {
        attributesMap = new HashMap<>();
    }

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(Predicates.requestType(LaunchRequest.class));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        ArrayList<String> dictionary = DictionaryUtils.buildDictionary();
        String speechText = "Welcome to Spelling Trainer.  Let's get started. ";
        String firstWord = DictionaryUtils.getWord(dictionary);
        String firstPrompt = "The first word is. " + firstWord;
        attributesMap.put("DICTIONARY", dictionary);
        attributesMap.put("CURRENT_WORD", firstWord);
        input.getAttributesManager().setSessionAttributes(attributesMap);
        return input.getResponseBuilder()
                .withSpeech(speechText + firstPrompt)
                .withReprompt(firstPrompt)
                .build();
    }

}
