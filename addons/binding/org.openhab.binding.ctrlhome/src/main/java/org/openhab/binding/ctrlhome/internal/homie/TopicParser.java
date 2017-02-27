package org.openhab.binding.ctrlhome.internal.homie;

import java.text.ParseException;

public class TopicParser {
    private final String HOMIE_PROPERTY_PREFIX = "$";
    private final String HOMIE_DEVICE_PROPERTY_HOMIE = "homie";
    private final String HOMIE_DEVICE_PROPERTY_ONLINE = "online";
    private final String HOMIE_DEVICE_PROPERTY_NAME = "name";
    private final String HOMIE_DEVICE_PROPERTY_LOCAL_IP = "localip";
    private final String HOMIE_DEVICE_PROPERTY_MAC = "mac";
    private final String HOMIE_DEVICE_PROPERTY_STATS = "stats";
    private final String HOMIE_DEVICE_PROPERTY_STATS_UPTIME = "uptime";
    private final String HOMIE_DEVICE_PROPERTY_STATS_SIGNAL = "signal";
    private final String HOMIE_DEVICE_PROPERTY_FW = "fw";
    private final String HOMIE_DEVICE_PROPERTY_FW_NAME = "name";
    private final String HOMIE_DEVICE_PROPERTY_FW_VERSION = "version";
    private final String HOMIE_DEVICE_PROPERTY_IMPLEMENTATION = "implementation";
    private String baseTopic;

    public TopicParser(String baseTopic) {
        this.baseTopic = baseTopic;
    }

    public String getBaseTopic() {
        return baseTopic;
    }

    public Topic parse(String topicString) throws ParseException {
        String[] elements = topicString.split("\\/");
        Topic topic = new Topic();
        if (elements.length > 1) {
            topic.setDeviceId(elements[1]);
            if (elements.length > 2) {
                if (elements[2].startsWith(HOMIE_PROPERTY_PREFIX)) {
                    if (elements[2].equals(HOMIE_PROPERTY_PREFIX + HOMIE_DEVICE_PROPERTY_HOMIE)) {
                        topic.setDeviceHomie(elements[3]);
                    }
                    if (elements[2].equals(HOMIE_PROPERTY_PREFIX + HOMIE_DEVICE_PROPERTY_ONLINE)) {
                        topic.setDeviceOnline(elements[3]);
                    }
                    if (elements[2].equals(HOMIE_PROPERTY_PREFIX + HOMIE_DEVICE_PROPERTY_NAME)) {
                        topic.setDeviceName(elements[3]);
                    }
                    if (elements[2].equals(HOMIE_PROPERTY_PREFIX + HOMIE_DEVICE_PROPERTY_LOCAL_IP)) {
                        topic.setDeviceLocalIp(elements[3]);
                    }
                    if (elements[2].equals(HOMIE_PROPERTY_PREFIX + HOMIE_DEVICE_PROPERTY_MAC)) {
                        topic.setDeviceMac(elements[3]);
                    }
                    if (elements[2].equals(HOMIE_PROPERTY_PREFIX + HOMIE_DEVICE_PROPERTY_STATS)) {
                        if (elements[3].equals(HOMIE_PROPERTY_PREFIX + HOMIE_DEVICE_PROPERTY_STATS_UPTIME)) {
                            topic.setDeviceStatsUptime(elements[4]);
                        }
                        if (elements[3].equals(HOMIE_PROPERTY_PREFIX + HOMIE_DEVICE_PROPERTY_STATS_SIGNAL)) {
                            topic.setDeviceStatsSignal(elements[4]);
                        }
                    }
                    if (elements[2].equals(HOMIE_PROPERTY_PREFIX + HOMIE_DEVICE_PROPERTY_FW)) {
                        if (elements[3].equals(HOMIE_PROPERTY_PREFIX + HOMIE_DEVICE_PROPERTY_FW_NAME)) {
                            topic.setDeviceFwName(elements[4]);
                        }
                        if (elements[3].equals(HOMIE_PROPERTY_PREFIX + HOMIE_DEVICE_PROPERTY_FW_VERSION)) {
                            topic.setDeviceFwVersion(elements[4]);
                        }
                    }
                    if (elements[2].equals(HOMIE_PROPERTY_PREFIX + HOMIE_DEVICE_PROPERTY_IMPLEMENTATION)) {
                        topic.setDeviceImplementation(elements[3]);
                    }
                } else {
                    topic.setNodeId(elements[2]);
                }
            }
        }

        return new Topic();
    }

}
