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
import org.eclipse.smarthome.core.thing.Bridge;
import org.eclipse.smarthome.core.thing.ThingTypeUID;
import org.eclipse.smarthome.core.thing.ThingUID;
import org.openhab.binding.ctrlhome.CtrlHomeBindingConstants;
import org.openhab.binding.ctrlhome.handler.CtrlHomeBridgeHandler;
import org.openhab.binding.ctrlhome.internal.config.CtrlHomeConfiguration;
import org.openhab.binding.ctrlhome.internal.homie.Topic;
import org.openhab.binding.ctrlhome.internal.homie.TopicParser;
import org.openhab.binding.ctrlhome.internal.mqtt.MqttConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link CtrlHomeDeviceDiscoveryService} class is used to discover ctrlHome devices
 * that are connected to the Wifi network via MQTT.
 *
 * @author Luka Bartonicek - Initial contribution
 */
public class CtrlHomeDeviceDiscoveryService extends AbstractDiscoveryService implements IMqttMessageListener {
    private final Logger logger = LoggerFactory.getLogger(CtrlHomeDeviceDiscoveryService.class);

    private CtrlHomeBridgeHandler bridgeHandler;
    private List<Topic> deviceTopics = new ArrayList<Topic>();
    private MqttConnection mqttConnection;
    private TopicParser topicParser;

    public CtrlHomeDeviceDiscoveryService(CtrlHomeConfiguration configuration, CtrlHomeBridgeHandler bridgeHandler) {
        super(CtrlHomeBindingConstants.SUPPORTED_DEVICE_THING_TYPES_UIDS,
                CtrlHomeBindingConstants.DEVICE_DISCOVERY_TIMEOUT_SECONDS, true);

        logger.info("ctrlHome Device Discovery Service started");

        this.bridgeHandler = bridgeHandler;
        mqttConnection = new MqttConnection(configuration, this);
        topicParser = new TopicParser(configuration.getBaseTopic());
    }

    public void activate() {
        startScan();
    }

    @Override
    public void deactivate() {
        stopScan();
    }

    @Override
    public Set<ThingTypeUID> getSupportedThingTypes() {
        return CtrlHomeBindingConstants.SUPPORTED_DEVICE_THING_TYPES_UIDS;
    }

    @Override
    public void messageArrived(String topicString, MqttMessage mqttMessage) throws Exception {
        String message = mqttMessage.toString();
        Topic topic = topicParser.parse(topicString, message);
        boolean newTopic = true;

        if (topic.isNode()) {
            Topic deviceTopic = topic;
            for (Topic tempTopic : deviceTopics) {
                if (topic.getNodeId().equals(tempTopic.getNodeId())) {
                    deviceTopic = tempTopic;
                    deviceTopic.update(topic);
                    newTopic = false;
                    break;
                }
            }
            if (newTopic) {
                deviceTopics.add(deviceTopic);
            }

            if (deviceTopic.isNodeInit() && !deviceTopic.isNodeDiscovered()) {
                Bridge bridge = bridgeHandler.getThing();
                String implementationConfig = bridge.getConfiguration().getProperties()
                        .get(CtrlHomeBindingConstants.PROPERTY_BRIDGE_IMPLEMENTATION_CONFIG).toString();
                ThingTypeUID thingType = null;
                Map<String, Object> properties = new HashMap<>(2);
                String label = "";

                for (String remoteId : topicParser.getRemoteIds(deviceTopic.getNodeType(), implementationConfig)) {
                    logger.info("Adding new ctrlHome device {} with id '{}' and remote id '{}' to inbox.",
                            deviceTopic.getName(), deviceTopic.getNodeId(), remoteId);

                    if (deviceTopic.getNodeType().equals(CtrlHomeBindingConstants.DEVICE_LIVOLO_SWITCH)) {
                        properties.put(CtrlHomeBindingConstants.PROPERTY_DEVICE_REMOTE_ID, remoteId);
                        thingType = CtrlHomeBindingConstants.THING_TYPE_LIVOLO_SWITCH;
                        label = CtrlHomeBindingConstants.LABEL_DEVICE_LIVOLO_SWITCH;

                    } else if (deviceTopic.getNodeType().equals(CtrlHomeBindingConstants.DEVICE_LIVOLO_DIMMER)) {
                        properties.put(CtrlHomeBindingConstants.PROPERTY_DEVICE_REMOTE_ID, remoteId);
                        thingType = CtrlHomeBindingConstants.THING_TYPE_LIVOLO_DIMMER;
                        label = CtrlHomeBindingConstants.LABEL_DEVICE_LIVOLO_DIMMER;
                    } else if (deviceTopic.getNodeType().equals(CtrlHomeBindingConstants.DEVICE_LIVOLO_ROLLERSHUTTER)) {
                        List<String> remotePairIds = topicParser.getRemotePairdIds(remoteId);
                        if (remotePairIds.size() > 1) {
                            properties.put(CtrlHomeBindingConstants.PROPERTY_DEVICE_REMOTE_ID_UP, remotePairIds.get(0));
                            properties.put(CtrlHomeBindingConstants.PROPERTY_DEVICE_REMOTE_ID_DOWN,
                                    remotePairIds.get(1));
                        }
                        properties.put(CtrlHomeBindingConstants.PROPERTY_DEVICE_REMOTE_ID, remoteId);
                        thingType = CtrlHomeBindingConstants.THING_TYPE_LIVOLO_ROLLERSHUTTER;
                        label = CtrlHomeBindingConstants.LABEL_DEVICE_LIVOLO_ROLLERSHUTTER;
                    }
                    label += " - remote Id:" + remoteId;
                    String deviceId = deviceTopic.getDeviceId() + deviceTopic.getNodeId() + remoteId;
                    ThingUID uid = new ThingUID(thingType, String.valueOf(deviceId.hashCode()));
                    if (uid != null) {
                        DiscoveryResult dr = DiscoveryResultBuilder.create(uid).withProperties(properties)
                                .withLabel(label).withBridge(bridge.getUID()).withThingType(thingType).build();
                        thingDiscovered(dr);
                    }
                }

                if (deviceTopic.getNodeType().equals(CtrlHomeBindingConstants.DEVICE_LIVOLO_CONFIGURATOR)) {
                    thingType = CtrlHomeBindingConstants.THING_TYPE_LIVOLO_CONFIGURATOR;
                    label = CtrlHomeBindingConstants.LABEL_DEVICE_LIVOLO_CONFIGURATOR;

                    String deviceId = deviceTopic.getDeviceId() + deviceTopic.getNodeId();
                    ThingUID uid = new ThingUID(thingType, String.valueOf(deviceId.hashCode()));
                    if (uid != null) {
                        DiscoveryResult dr = DiscoveryResultBuilder.create(uid).withLabel(label)
                                .withBridge(bridge.getUID()).withThingType(thingType).build();
                        thingDiscovered(dr);
                    }
                }

                deviceTopic.setNodeDiscovered(true);
            }
        }
    }

    @Override
    protected void startScan() {
        logger.info("ctrlHome Device Discovery Service start scan");

        mqttConnection.listenForDeviceDiscovery(bridgeHandler.getThing().getConfiguration().getProperties()
                .get(CtrlHomeBindingConstants.PROPERTY_BRIDGE_DEVICE_ID).toString(), this);
        deviceTopics.clear();
    }

    @Override
    protected synchronized void stopScan() {
        logger.info("ctrlHome Device Discovery Service stop scan");

        super.stopScan();
        mqttConnection.unsubscribeListenForBridgeDiscovery();
        deviceTopics.clear();
    }

}
