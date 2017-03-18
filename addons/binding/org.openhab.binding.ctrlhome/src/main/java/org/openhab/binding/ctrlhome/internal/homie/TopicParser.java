package org.openhab.binding.ctrlhome.internal.homie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;
import org.openhab.binding.ctrlhome.CtrlHomeBindingConstants;

public class TopicParser {
    // @formatter:off
    private String baseTopic;
    private final String HOMIE_DEVICE_PROPERTY_CONFIG                       = "config";
    private final String HOMIE_DEVICE_PROPERTY_FW                           = "fw";
    private final String HOMIE_DEVICE_PROPERTY_FW_CHECKSUM                  = "checksum";
    private final String HOMIE_DEVICE_PROPERTY_FW_NAME                      = "name";
    private final String HOMIE_DEVICE_PROPERTY_FW_VERSION                   = "version";
    private final String HOMIE_DEVICE_PROPERTY_HOMIE                        = "homie";
    private final String HOMIE_DEVICE_PROPERTY_IMPLEMENTATION               = "implementation";
    private final String HOMIE_DEVICE_PROPERTY_IMPLEMENTATION_SETTINGS      = "settings";
    private final String HOMIE_DEVICE_PROPERTY_LOCAL_IP                     = "localip";
    private final String HOMIE_DEVICE_PROPERTY_MAC                          = "mac";
    private final String HOMIE_DEVICE_PROPERTY_NAME                         = "name";
    private final String HOMIE_DEVICE_PROPERTY_ONLINE                       = "online";
    private final String HOMIE_DEVICE_PROPERTY_STATS                        = "stats";
    private final String HOMIE_DEVICE_PROPERTY_STATS_SIGNAL                 = "signal";
    private final String HOMIE_DEVICE_PROPERTY_STATS_INTERVAL               = "interval";
    private final String HOMIE_DEVICE_PROPERTY_STATS_UPTIME                 = "uptime";

    private final String HOMIE_NODE_PROPERTY_PROPERTIES                     = "properties";
    private final String HOMIE_NODE_PROPERTY_TYPE                           = "type";
    private final String HOMIE_NODE_REMOTE_ID_DELIMITER                     = "_";
    private final String HOMIE_NODE_REMOTE_ID_PAIR_DELIMITER                = "-";

    private final String HOMIE_LIVOLO_SWITCH_REMOTE_ID                      = "lvSwId";
    private final String HOMIE_LIVOLO_DIMMER_REMOTE_ID                      = "lvDmId";
    private final String HOMIE_LIVOLO_ROLLERSHUTTER_REMOTE_ID               = "lvRsId";

    private final String HOMIE_PROPERTY_PREFIX                              = "$";

    // @formatter:on
    public TopicParser(String baseTopic) {
        this.baseTopic = baseTopic;
    }

    public String getBaseTopic() {
        return baseTopic;
    }

    public List<String> getRemoteIds(String type, String implementationConfig) {
        List<String> remoteIds = new ArrayList<String>();

        try {
            JSONObject json = new JSONObject(implementationConfig);
            JSONObject settings = json.getJSONObject(HOMIE_DEVICE_PROPERTY_IMPLEMENTATION_SETTINGS);
            if (type.equals(CtrlHomeBindingConstants.DEVICE_LIVOLO_SWITCH)) {
                String remoteIdsString = settings.getString(HOMIE_LIVOLO_SWITCH_REMOTE_ID);
                remoteIds.addAll(Arrays.asList(remoteIdsString.split(HOMIE_NODE_REMOTE_ID_DELIMITER)));
            } else if (type.equals(CtrlHomeBindingConstants.DEVICE_LIVOLO_DIMMER)) {
                String remoteIdsString = settings.getString(HOMIE_LIVOLO_DIMMER_REMOTE_ID);
                remoteIds.addAll(Arrays.asList(remoteIdsString.split(HOMIE_NODE_REMOTE_ID_DELIMITER)));
            } else if (type.equals(CtrlHomeBindingConstants.DEVICE_LIVOLO_ROLLERSHUTTER)) {
                String remoteIdsString = settings.getString(HOMIE_LIVOLO_ROLLERSHUTTER_REMOTE_ID);
                remoteIds.addAll(Arrays.asList(remoteIdsString.split(HOMIE_NODE_REMOTE_ID_DELIMITER)));
            }
        } catch (Exception e) {

        }

        return remoteIds;
    }

    public List<String> getRemotePairdIds(String remoteId) {
        List<String> remotePairIds = new ArrayList<String>(
                Arrays.asList(remoteId.split(HOMIE_NODE_REMOTE_ID_PAIR_DELIMITER)));
        return remotePairIds;
    }

    public Topic parse(String topicString, String mqttMessage) {
        String[] elements = topicString.split("\\/");

        Topic topic = new Topic();
        if (elements.length > 1) {
            topic.setDeviceId(elements[1]);
            if (elements.length > 2) {
                if (elements[2].startsWith(HOMIE_PROPERTY_PREFIX)) {
                    if (elements[2].equals(HOMIE_PROPERTY_PREFIX + HOMIE_DEVICE_PROPERTY_HOMIE)) {
                        topic.setHomie(mqttMessage);
                    }
                    if (elements[2].equals(HOMIE_PROPERTY_PREFIX + HOMIE_DEVICE_PROPERTY_ONLINE)) {
                        topic.setOnline(mqttMessage);
                    }
                    if (elements[2].equals(HOMIE_PROPERTY_PREFIX + HOMIE_DEVICE_PROPERTY_NAME)) {
                        topic.setName(mqttMessage);
                    }
                    if (elements[2].equals(HOMIE_PROPERTY_PREFIX + HOMIE_DEVICE_PROPERTY_LOCAL_IP)) {
                        topic.setLocalIp(mqttMessage);
                    }
                    if (elements[2].equals(HOMIE_PROPERTY_PREFIX + HOMIE_DEVICE_PROPERTY_MAC)) {
                        topic.setMac(mqttMessage);
                    }
                    if (elements[2].equals(HOMIE_PROPERTY_PREFIX + HOMIE_DEVICE_PROPERTY_STATS)) {
                        if (elements[3].equals(HOMIE_DEVICE_PROPERTY_STATS_UPTIME)) {
                            topic.setStatsUptime(mqttMessage);
                        }
                        if (elements[3].equals(HOMIE_DEVICE_PROPERTY_STATS_SIGNAL)) {
                            topic.setStatsSignal(mqttMessage);
                        }
                        if (elements[3].equals(HOMIE_DEVICE_PROPERTY_STATS_INTERVAL)) {
                            topic.setStatsInterval(mqttMessage);
                        }
                    }
                    if (elements[2].equals(HOMIE_PROPERTY_PREFIX + HOMIE_DEVICE_PROPERTY_FW)) {
                        if (elements[3].equals(HOMIE_DEVICE_PROPERTY_FW_NAME)) {
                            topic.setFwName(mqttMessage);
                        }
                        if (elements[3].equals(HOMIE_DEVICE_PROPERTY_FW_VERSION)) {
                            topic.setFwVersion(mqttMessage);
                        }
                        if (elements[3].equals(HOMIE_DEVICE_PROPERTY_FW_CHECKSUM)) {
                            topic.setFwChecksum(mqttMessage);
                        }
                    }
                    if (elements[2].equals(HOMIE_PROPERTY_PREFIX + HOMIE_DEVICE_PROPERTY_IMPLEMENTATION)) {
                        if (elements.length > 3) {
                            if (elements[3].equals(HOMIE_DEVICE_PROPERTY_CONFIG)) {
                                topic.setImplementationConfig(mqttMessage);
                            }
                        } else {
                            topic.setImplementation(mqttMessage);
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
