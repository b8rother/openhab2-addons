/**
 * Copyright (c) 2014-2016 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.ctrlhome.internal.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.eclipse.smarthome.core.thing.Channel;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.types.Command;
import org.openhab.binding.ctrlhome.CtrlHomeBindingConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MqttConnection {
    private static Logger logger = LoggerFactory.getLogger(MqttConnection.class);

    public static MqttConnection fromConfiguration(HomieConfiguration config, Object consumer) {
        return new MqttConnection(config.getBrokerUrl(), config.getBaseTopic(), MQTT_CLIENTID + "-" + consumer);
    }

    private final String basetopic;
    private final String brokerUrl;
    private MqttClient client;
    private final String clientId;
    private final String listenDeviceTopic;

    public MqttConnection(String brokerUrl, String basetopic, String clientId) {
        this.brokerUrl = brokerUrl;
        this.basetopic = basetopic;
        this.listenDeviceTopic = String.format("%s/#", basetopic);
        this.clientId = clientId;

        connect();
    }

    private void connect() {
        try {
            logger.debug("MQTT Connection start");

            MqttConnectOptions opts = new MqttConnectOptions();
            opts.setAutomaticReconnect(true);
            opts.setCleanSession(true);
            client = new MqttClient(brokerUrl, CtrlHomeBindingConstants.MQTT_CLIENT_ID_PREFIX
                    + CtrlHomeBindingConstants.MQTT_CLIENT_ID_DELIMITER + clientId, new MemoryPersistence());
            client.connect(opts);

            logger.debug("MQTT Connection success");
        } catch (MqttException e) {
            logger.error("MQTT Connect failed", e);
        }
    }

    public void disconnect() {
        try {
            client.disconnectForcibly();
        } catch (MqttException e) {
            logger.error("MQTT Disconnect failed", e);
        }
    }

    public String getBasetopic() {
        return basetopic;
    }

    public void listenForDeviceIds(IMqttMessageListener messageListener) {
        logger.debug("Listening for devices on topic " + listenDeviceTopic);
        try {
            resubscribe(listenDeviceTopic, messageListener);
        } catch (MqttException e) {
            logger.error("Failed to subscribe to topic " + listenDeviceTopic, e);
        }
    }

    public void listenForNode(String deviceId, String nodeId, IMqttMessageListener listener) throws MqttException {
        String topic = String.format("%s/%s/%s/", basetopic, deviceId, nodeId);
        // Subscribe to type and proplist separately, as the notifications have to arrive first at the listener
        resubscribe(topic + HomieConventions.HOMIE_NODE_TYPE_ANNOUNCEMENT_TOPIC_SUFFIX, listener);
        resubscribe(topic + HomieConventions.HOMIE_NODE_PROPERTYLIST_ANNOUNCEMENT_TOPIC_SUFFIX, listener);
        resubscribe(topic + "#", listener);
    }

    private void resubscribe(String topic, IMqttMessageListener listener) throws MqttException {
        client.unsubscribe(topic);
        client.subscribe(topic, SUBSCRIBE_QOS, listener);
    }

    public void send(String deviceId, String nodeId, String property, Command command)
            throws MqttPersistenceException, MqttException {
        String topic = String.format("%s/%s/%s/%s/set", basetopic, deviceId, nodeId, property);
        client.publish(topic, command.toString().getBytes(), PUBLISH_QOS, true);

    }

    public void subscribe(Thing thing, IMqttMessageListener messageListener) throws MqttException {
        String topic = String.format("%s/%s/#", basetopic, thing.getUID().getId());
        resubscribe(topic, messageListener);

    }

    /**
     * Subscribe to a channel of a device
     *
     * @param channelUID
     * @param handler
     */
    public void subscribeChannel(Channel channel, HomieDeviceHandler handler) {

        String topic = String.format("%s/%s/%s", basetopic, handler.getThing().getUID().getId(),
                channel.getProperties().get(CHANNELPROPERTY_TOPICSUFFIX));
        logger.debug(
                "(Re-)Subscribing to topic '" + topic + "' to listen for events of channel '" + channel.getUID() + "'");
        try {
            resubscribe(topic, handler);
            logger.debug("Subscribed to topic " + topic);
        } catch (MqttException e) {
            logger.error("Error (re)subscribing to channel. topic is " + topic, e);
        }

    }

    public void unsubscribeListenForDeviceIds() {
        try {
            client.unsubscribe(listenDeviceTopic);
        } catch (MqttException e) {
            logger.error("Failed to unsubscribe from topic " + listenDeviceTopic, e);
        }

    }

}
