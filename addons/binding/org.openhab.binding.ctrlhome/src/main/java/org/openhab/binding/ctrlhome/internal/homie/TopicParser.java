package org.openhab.binding.ctrlhome.internal.homie;

import java.text.ParseException;

public class TopicParser {
    private String baseTopic;

    public TopicParser(String baseTopic) {
        this.baseTopic = baseTopic;
    }

    public String getBaseTopic() {
        return baseTopic;
    }

    public Topic parse(String topicString) throws ParseException {
        String[] elements = topicString.split("\\/");

        return new Topic();
    }

    public void setBaseTopic(String baseTopic) {
        this.baseTopic = baseTopic;
    }
}
