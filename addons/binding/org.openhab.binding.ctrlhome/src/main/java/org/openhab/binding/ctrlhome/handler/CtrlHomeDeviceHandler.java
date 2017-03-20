/**
 * Copyright (c) 2014-2016 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.ctrlhome.handler;

import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingStatus;
import org.eclipse.smarthome.core.thing.binding.BaseThingHandler;
import org.eclipse.smarthome.core.types.Command;
import org.openhab.binding.ctrlhome.CtrlHomeBindingConstants;
import org.openhab.binding.ctrlhome.internal.config.CtrlHomeConfiguration;
import org.openhab.binding.ctrlhome.internal.homie.TopicParser;
import org.openhab.binding.ctrlhome.internal.mqtt.MqttConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link CtrlHomeDeviceHandler} is responsible for handling commands, which are
 * sent to one of the channels.
 *
 * @author Luka Bartonicek - Initial contribution
 */
public class CtrlHomeDeviceHandler extends BaseThingHandler implements IMqttMessageListener {

    private Logger logger = LoggerFactory.getLogger(CtrlHomeDeviceHandler.class);
    private MqttConnection mqttConnection;
    private TopicParser topicParser;

    public CtrlHomeDeviceHandler(Thing thing, CtrlHomeConfiguration configuration) {
        super(thing);

        mqttConnection = new MqttConnection(configuration, this);
        topicParser = new TopicParser(configuration.getBaseTopic());
    }

    @Override
    public void dispose() {
        mqttConnection.disconnect();
        super.dispose();
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
        if (channelUID.getId().equals(CtrlHomeBindingConstants.CHANNEL_CONFIG_COMMAND)) {
            // TODO: handle command

            // Note: if communication with thing fails for some reason,
            // indicate that by setting the status with detail information
            // updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR,
            // "Could not control device at IP address x.x.x.x");
        } else if (channelUID.getId().equals(CtrlHomeBindingConstants.CHANNEL_CONFIG_REMOTE_ID)) {

        } else if (channelUID.getId().equals(CtrlHomeBindingConstants.CHANNEL_SWITCH_STATE)) {

        }
    }

    @Override
    public void initialize() {
        String bridgeId = getBridge().getConfiguration().getProperties()
                .get(CtrlHomeBindingConstants.PROPERTY_BRIDGE_DEVICE_ID).toString();
        String deviceId = thing.getConfiguration().getProperties().get(CtrlHomeBindingConstants.PROPERTY_DEVICE_ID)
                .toString();
        mqttConnection.listenForDevice(bridgeId, deviceId, this);
        updateStatus(ThingStatus.ONLINE);
    }

    @Override
    public void messageArrived(String topicString, MqttMessage mqttMessage) throws Exception {
        // TODO Auto-generated method stub

    }
}
