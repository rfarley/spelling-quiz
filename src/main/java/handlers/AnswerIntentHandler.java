package handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.*;
import enums.Dictionary;
import utils.DictionaryUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class AnswerIntentHandler implements RequestHandler {

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("AnswerIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        Request request = input.getRequestEnvelope().getRequest();
        IntentRequest intentRequest = (IntentRequest) request;
        Intent intent = intentRequest.getIntent();
        Map<String, Slot> slots = intent.getSlots();

        //Get the current dictionary and word
        Map<String, Object> attributesMap = input.getAttributesManager().getSessionAttributes();
        String currentWord = (String) attributesMap.get("CURRENT_WORD");
        ArrayList<String> dictionary = (ArrayList<String>) attributesMap.get("DICTIONARY");

        //Get the user's answer
        Slot answerSlot = slots.get("answers");
        String answer = answerSlot.getValue();
        if (answer != null) {
            answer = answer.replaceAll("[^A-Za-z]", "");
        } else {
            return input.getResponseBuilder()
                    .withSpeech("That is not a valid response.  Try again. The word is. " + currentWord)
                    .withReprompt("The word is. "+ currentWord)
                    .build();
        }
        String speechText = "You said: " + answer.replaceAll("", ".") + ". ";
        String answerWord = answer.replaceAll(" ", "").toUpperCase();

        if (answerWord.equals(currentWord)) {
            speechText += "That is correct! ";
        } else {
            speechText += "That is wrong.  You should have spelled " + currentWord.replaceAll("", ". ");
        }

        //Get the next word or end the session
        if (dictionary == null || dictionary.size() == 0) {
            speechText += "You have finished all words.  Goodbye.";
            return input.getResponseBuilder()
                    .withSpeech(speechText)
                    .withShouldEndSession(true)
                    .build();
        } else {
            currentWord = DictionaryUtils.getWord(dictionary);
            attributesMap.put("CURRENT_WORD", currentWord);
            attributesMap.put("DICTIONARY", dictionary);
            input.getAttributesManager().setSessionAttributes(attributesMap);
            speechText += "The next word is. " + currentWord;
        }

        return input.getResponseBuilder()
                .withSpeech(speechText)
                .withReprompt(speechText)
                .build();
    }

}
