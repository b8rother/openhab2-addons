package org.openhab.binding.ctrlhome.internal.homie;

public class TopicParser {
    private String baseTopic;
    private final String HOMIE_DEVICE_PROPERTY_CONFIG = "config";
    private final String HOMIE_DEVICE_PROPERTY_FW = "fw";
    private final String HOMIE_DEVICE_PROPERTY_FW_CHECKSUM = "checksum";
    private final String HOMIE_DEVICE_PROPERTY_FW_NAME = "name";
    private final String HOMIE_DEVICE_PROPERTY_FW_VERSION = "version";
    private final String HOMIE_DEVICE_PROPERTY_HOMIE = "homie";
    private final String HOMIE_DEVICE_PROPERTY_IMPLEMENTATION = "implementation";
    private final String HOMIE_DEVICE_PROPERTY_LOCAL_IP = "localip";
    private final String HOMIE_DEVICE_PROPERTY_MAC = "mac";
    private final String HOMIE_DEVICE_PROPERTY_NAME = "name";
    private final String HOMIE_DEVICE_PROPERTY_ONLINE = "online";
    private final String HOMIE_DEVICE_PROPERTY_STATS = "stats";
    private final String HOMIE_DEVICE_PROPERTY_STATS_SIGNAL = "signal";
    private final String HOMIE_DEVICE_PROPERTY_STATS_INTERVAL = "interval";
    private final String HOMIE_DEVICE_PROPERTY_STATS_UPTIME = "uptime";
    private final String HOMIE_NODE_PROPERTY_PROPERTIES = "properties";
    private final String HOMIE_NODE_PROPERTY_TYPE = "type";
    private final String HOMIE_PROPERTY_PREFIX = "$";

    public TopicParser(String baseTopic) {
        this.baseTopic = baseTopic;
    }

    public String getBaseTopic() {
        return baseTopic;
    }

    public Topic parse(String topicString, String mqttMessage) {
        String[] elements = topicString.split("\\/");

        Topic topic = new Topic();
        if (elements.length > 1) {
            topic.setDeviceId(elements[1]);
            if (elements.length > 2) {
                if (elements[2].startsWith(HOMIE_PROPERTY_PREFIX)) {
                    if (elements[2].equals(HOMIE_PROPERTY_PREFIX + HOMIE_DEVICE_PROPERTY_HOMIE)) {
                        topic.setDeviceHomie(mqttMessage);
                    }
                    if (elements[2].equals(HOMIE_PROPERTY_PREFIX + HOMIE_DEVICE_PROPERTY_ONLINE)) {
                        topic.setDeviceOnline(mqttMessage);
                    }
                    if (elements[2].equals(HOMIE_PROPERTY_PREFIX + HOMIE_DEVICE_PROPERTY_NAME)) {
                        topic.setDeviceName(mqttMessage);
                    }
                    if (elements[2].equals(HOMIE_PROPERTY_PREFIX + HOMIE_DEVICE_PROPERTY_LOCAL_IP)) {
                        topic.setDeviceLocalIp(mqttMessage);
                    }
                    if (elements[2].equals(HOMIE_PROPERTY_PREFIX + HOMIE_DEVICE_PROPERTY_MAC)) {
                        topic.setDeviceMac(mqttMessage);
                    }
                    if (elements[2].equals(HOMIE_PROPERTY_PREFIX + HOMIE_DEVICE_PROPERTY_STATS)) {
                        if (elements[3].equals(HOMIE_DEVICE_PROPERTY_STATS_UPTIME)) {
                            topic.setDeviceStatsUptime(mqttMessage);
                        }
                        if (elements[3].equals(HOMIE_DEVICE_PROPERTY_STATS_SIGNAL)) {
                            topic.setDeviceStatsSignal(mqttMessage);
                        }
                        if (elements[3].equals(HOMIE_DEVICE_PROPERTY_STATS_INTERVAL)) {
                            topic.setDeviceStatsInterval(mqttMessage);
                        }
                    }
                    if (elements[2].equals(HOMIE_PROPERTY_PREFIX + HOMIE_DEVICE_PROPERTY_FW)) {
                        if (elements[3].equals(HOMIE_DEVICE_PROPERTY_FW_NAME)) {
                            topic.setDeviceFwName(mqttMessage);
                        }
                        if (elements[3].equals(HOMIE_DEVICE_PROPERTY_FW_VERSION)) {
                            topic.setDeviceFwVersion(mqttMessage);
                        }
                        if (elements[3].equals(HOMIE_DEVICE_PROPERTY_FW_CHECKSUM)) {
                            topic.setDeviceFwChecksum(mqttMessage);
                        }
                    }
                    if (elements[2].equals(HOMIE_PROPERTY_PREFIX + HOMIE_DEVICE_PROPERTY_IMPLEMENTATION)) {
                        if (elements.length > 3) {
                            if (elements[3].equals(HOMIE_DEVICE_PROPERTY_CONFIG)) {
                                topic.setDeviceImplementationConfig(mqttMessage);
                            }
                        } else {
                            topic.setDeviceImplementation(mqttMessage);
                        }
                    }
                } else {
                    topic.setNodeId(elements[2]);
                    if (elements[3].startsWith(HOMIE_PROPERTY_PREFIX)) {
                        if (elements[3].equals(HOMIE_PROPERTY_PREFIX + HOMIE_NODE_PROPERTY_TYPE)) {
                            topic.setNodeType(mqttMessage);
                        }
                        if (elements[3].equals(HOMIE_PROPERTY_PREFIX + HOMIE_NODE_PROPERTY_PROPERTIES)) {
                            topic.setNodeProperties(mqttMessage);
                        }
                    }
                }
            }
        }

        return topic;
    }

}
