/**
 * Copyright (c) 2014-2016 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.ctrlhome.internal.discovery;

import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.smarthome.config.discovery.AbstractDiscoveryService;
import org.eclipse.smarthome.config.discovery.DiscoveryResult;
import org.eclipse.smarthome.config.discovery.DiscoveryResultBuilder;
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
    public void messageArrived(String topicString, MqttMessage mqttMessage) throws Exception {
        String message = mqttMessage.toString();

        logger.info(topicString);
        logger.info(message);
        Topic topic = topicParser.parse(topicString, message);
        if (topic.isBridge()) {
            ThingUID thingId = new ThingUID(CtrlHomeBindingConstants.THING_TYPE_CTRLHOME_BRIDGE_GATEWAY,
                    topic.getDeviceId());
            DiscoveryResult dr = DiscoveryResultBuilder.create(thingId).withLabel(message).build();
            thingDiscovered(dr);
        }
    }

    @Override
    protected void startScan() {
        logger.info("ctrlHome Bridge Discovery Service start scan");

        mqttconnection.listenForBridge(this);
    }

    @Override
    protected synchronized void stopScan() {
        logger.info("ctrlHome Bridge Discovery Service stop scan");

        super.stopScan();
        mqttconnection.unsubscribeListenForBridge();
    }

}
