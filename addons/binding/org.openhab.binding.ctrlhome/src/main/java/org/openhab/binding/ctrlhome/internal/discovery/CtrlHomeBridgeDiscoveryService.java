/**
 * Copyright (c) 2014-2016 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.ctrlhome.internal.discovery;

import static org.openhab.binding.homie.HomieBindingConstants.HOMIE_DEVICE_THING_TYPE;
import static org.openhab.binding.homie.internal.conventionv200.HomieConventions.NAME_TOPIC_SUFFIX;

import java.text.ParseException;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.smarthome.config.discovery.AbstractDiscoveryService;
import org.eclipse.smarthome.config.discovery.DiscoveryResult;
import org.eclipse.smarthome.config.discovery.DiscoveryResultBuilder;
import org.eclipse.smarthome.core.thing.ThingUID;
import org.openhab.binding.ctrlhome.CtrlHomeBindingConstants;
import org.openhab.binding.ctrlhome.internal.config.CtrlHomeConfiguration;
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
    private CtrlHomeConfiguration configuration;

    private final Logger logger = LoggerFactory.getLogger(CtrlHomeBridgeDiscoveryService.class);

    // private final TopicParser topicParser;
    private MqttConnection mqttconnection;

    public CtrlHomeBridgeDiscoveryService(CtrlHomeConfiguration configuration) {
        super(CtrlHomeBindingConstants.SUPPORTED_BRIDGE_THING_TYPES_UIDS,
                CtrlHomeBindingConstants.BRIDGE_DISCOVERY_TIMEOUT_SECONDS, true);

        logger.info("ctrlHome Bridge Discovery Service started");

        this.configuration = configuration;
        mqttconnection = new MqttConnection(configuration, this);
        // topicParser = new TopicParser(config.getBaseTopic());

    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
        String message = mqttMessage.toString();

        try {
            HomieTopic ht = topicParser.parse(topic);
            if (ht.isDeviceProperty() && StringUtils.equals(ht.getCombinedInternalPropertyName(), NAME_TOPIC_SUFFIX)) {
                ThingUID thingId = new ThingUID(HOMIE_DEVICE_THING_TYPE, ht.getDeviceId());
                DiscoveryResult dr = DiscoveryResultBuilder.create(thingId).withLabel(message).build();
                thingDiscovered(dr);
            }

        } catch (ParseException e) {
            logger.debug("Topic cannot be parsed", e);
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
