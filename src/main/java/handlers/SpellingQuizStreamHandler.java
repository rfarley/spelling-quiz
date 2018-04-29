package handlers;

import com.amazon.ask.Skill;
import com.amazon.ask.SkillStreamHandler;
import com.amazon.ask.Skills;

public class SpellingQuizStreamHandler extends SkillStreamHandler {

    private static Skill getSkill() {
        return Skills.standard()
                .addRequestHandlers(new CancelandStopIntentHandler(),
                        new HelpIntentHandler(),
                        new LaunchRequestHandler(),
                        new SessionEndedRequestHandler(),
                        new AnswerIntentHandler(),
                        new RepeatIntentHandler())
                .build();
    }

    public SpellingQuizStreamHandler() {
        super(getSkill());
    }

}
