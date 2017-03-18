/**
 * Copyright (c) 2014-2016 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.ctrlhome.internal.discovery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.smarthome.config.discovery.AbstractDiscoveryService;
import org.eclipse.smarthome.config.discovery.DiscoveryResult;
import org.eclipse.smarthome.config.discovery.DiscoveryResultBuilder;
import org.eclipse.smarthome.core.thing.ThingTypeUID;
import org.eclipse.smarthome.core.thing.ThingUID;
import org.openhab.binding.ctrlhome.CtrlHomeBindingConstants;
import org.openhab.binding.ctrlhome.internal.config.CtrlHomeConfiguration;
import org.openhab.binding.ctrlhome.internal.homie.Topic;
import org.openhab.binding.ctrlhome.internal.homie.TopicParser;
import org.openhab.binding.ctrlhome.internal.mqtt.MqttConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link CtrlHomeBridgeDiscoveryService} is responsible for discovering new
 * ctrlHome gateway devices on the network
 *
 * @author Luka Bartonicek - Initial contribution
 *
 */
public class CtrlHomeBridgeDiscoveryService extends AbstractDiscoveryService implements IMqttMessageListener {
    private final Logger logger = LoggerFactory.getLogger(CtrlHomeBridgeDiscoveryService.class);

    private List<Topic> bridgeTopics = new ArrayList<Topic>();
    private MqttConnection mqttconnection;
    private TopicParser topicParser;

    public CtrlHomeBridgeDiscoveryService(CtrlHomeConfiguration configuration) {
        super(CtrlHomeBindingConstants.SUPPORTED_BRIDGE_THING_TYPES_UIDS,
                CtrlHomeBindingConstants.BRIDGE_DISCOVERY_TIMEOUT_SECONDS, true);

        logger.info("ctrlHome Bridge Discovery Service started");

        mqttconnection = new MqttConnection(configuration, this);
        topicParser = new TopicParser(configuration.getBaseTopic());

    }

    @Override
    public Set<ThingTypeUID> getSupportedThingTypes() {
        return CtrlHomeBindingConstants.SUPPORTED_BRIDGE_THING_TYPES_UIDS;
    }

    @Override
    public void messageArrived(String topicString, MqttMessage mqttMessage) throws Exception {
        String message = mqttMessage.toString();
        Topic topic = topicParser.parse(topicString, message);
        boolean newTopic = true;

        if (topic.isBridge()) {
            Topic bridgeTopic = topic;
            for (Topic tempTopic : bridgeTopics) {
                if (topic.getDeviceId().equals(tempTopic.getDeviceId())) {
                    bridgeTopic = tempTopic;
                    bridgeTopic.update(topic);
                    newTopic = false;
                    break;
                }
            }
            if (newTopic) {
                bridgeTopics.add(bridgeTopic);
            }

            if (bridgeTopic.isBridgeInit() && !bridgeTopic.isBridgeDiscovered()) {
                logger.info("Adding new ctrlHome bridge {} with id '{}' to inbox.", bridgeTopic.getName(),
                        bridgeTopic.getDeviceId());

                Map<String, Object> properties = new HashMap<>(2);
                properties.put(CtrlHomeBindingConstants.PROPERTY_BRIDGE_DEVICE_ID, bridgeTopic.getDeviceId());
                properties.put(CtrlHomeBindingConstants.PROPERTY_BRIDGE_NAME, bridgeTopic.getName());
                properties.put(CtrlHomeBindingConstants.PROPERTY_BRIDGE_MAC, bridgeTopic.getMac());
                properties.put(CtrlHomeBindingConstants.PROPERTY_BRIDGE_LOCAL_IP, bridgeTopic.getLocalIp());
                properties.put(CtrlHomeBindingConstants.PROPERTY_BRIDGE_HOMIE_VERSION, bridgeTopic.getHomie());
                properties.put(CtrlHomeBindingConstants.PROPERTY_BRIDGE_IMPLEMENTATION,
                        bridgeTopic.getImplementation());
                properties.put(CtrlHomeBindingConstants.PROPERTY_BRIDGE_IMPLEMENTATION_CONFIG,
                        bridgeTopic.getImplementationConfig());
                properties.put(CtrlHomeBindingConstants.PROPERTY_BRIDGE_STATS_UPTIME, bridgeTopic.getStatsUptime());
                properties.put(CtrlHomeBindingConstants.PROPERTY_BRIDGE_STATS_INTERVAL, bridgeTopic.getStatsInterval());
                if (bridgeTopic.getStatsSignal() != null) {
                    properties.put(CtrlHomeBindingConstants.PROPERTY_BRIDGE_STATS_SIGNAL, bridgeTopic.getStatsSignal());
                }
                properties.put(CtrlHomeBindingConstants.PROPERTY_BRIDGE_FW_NAME, bridgeTopic.getFwName());
                properties.put(CtrlHomeBindingConstants.PROPERTY_BRIDGE_FW_VERSION, bridgeTopic.getFwVersion());
                if (bridgeTopic.getFwChecksum() != null) {
                    properties.put(CtrlHomeBindingConstants.PROPERTY_BRIDGE_FW_CHECKSUM, bridgeTopic.getFwChecksum());
                }

                ThingUID uid = new ThingUID(CtrlHomeBindingConstants.THING_TYPE_CTRLHOME_BRIDGE_GATEWAY,
                        bridgeTopic.getDeviceId());
                String label = CtrlHomeBindingConstants.LABEL_BRIDGE_CTRLHOME_GATEWAY;
                if (uid != null) {
                    DiscoveryResult dr = DiscoveryResultBuilder.create(uid).withProperties(properties)
                            .withThingType(CtrlHomeBindingConstants.THING_TYPE_CTRLHOME_BRIDGE_GATEWAY).withLabel(label)
                            .build();
                    thingDiscovered(dr);

                    bridgeTopic.setBridgeDiscovered(true);
                }
            }
        }
    }

    @Override
    protected void startScan() {
        logger.info("ctrlHome Bridge Discovery Service start scan");

        mqttconnection.listenForBridge(this);
        bridgeTopics.clear();
    }

    @Override
    protected synchronized void stopScan() {
        logger.info("ctrlHome Bridge Discovery Service stop scan");

        super.stopScan();
        mqttconnection.unsubscribeListenForBridge();
        bridgeTopics.clear();
    }

}
