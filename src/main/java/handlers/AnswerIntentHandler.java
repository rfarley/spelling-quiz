package handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.*;
import enums.Dictionary;

import java.util.ArrayList;
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
        String currentWord = (String) input.getAttributesManager().getSessionAttributes().get("CURRENT_WORD");
        ArrayList<Dictionary> dictionary = (ArrayList<Dictionary>) input.getAttributesManager().getSessionAttributes().get("DICTIONARY");

        //Get the user's answer
        Slot answerSlot = slots.get("answers");
        String answer = answerSlot.getValue();

        String speechText = "You said: " + answer + ". ";
        String answerWord = answer.replaceAll("\\s", "").toUpperCase();

        if (answerWord.equals(currentWord)) {
            speechText += "That is correct!";
        } else {
            speechText += "That is wrong.  You should have spelled " + currentWord;
        }

        return input.getResponseBuilder()
                .withSpeech(speechText)
                .withReprompt(speechText)
                .build();
    }

}
