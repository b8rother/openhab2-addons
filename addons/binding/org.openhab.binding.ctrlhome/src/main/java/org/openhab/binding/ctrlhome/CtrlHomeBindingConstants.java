/**
 * Copyright (c) 2014-2016 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.ctrlhome;

import java.util.Collection;
import java.util.Set;

import org.eclipse.smarthome.core.thing.ThingTypeUID;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;

/**
 * The {@link CtrlHomeBinding} class defines common constants, which are
 * used across the whole binding.
 *
 * @author Luka Bartonicek - Initial contribution
 */
// @formatter:off
public class CtrlHomeBindingConstants {

    public static final String BINDING_ID                           = "ctrlhome";

    // List of all Channel ids
    public final static String CHANNEL_1                            = "channel1";

    // List of device types
    public static final String  BRIDGE_CTRLHOME_GATEWAY             = "bridge";
    public static final String  DEVICE_LIVOLO_DIMMER                = "livoloDimmer";
    public static final String  DEVICE_LIVOLO_ROLLERSHUTTER         = "livoloRollershutter";
    public static final String  DEVICE_LIVOLO_SWITCH                = "livoloSwitch";

    // Custom Properties
    public final static String PROPERTY_MQTT_BROKER_URL            = "mqttBrokerUrl";
    public final static String PROPERTY_MQTT_BASE_TOPIC            = "mqttBaseTopic";
//    public final static String PROPERTY_BRIDGE_DEVICE_ID            = "deviceId";

    // MQTT fixed config
    public static final String  MQTT_CLIENT_ID_DELIMITER            = "_";
    public static final String  MQTT_CLIENT_ID_PREFIX               = "ctrlHomeOH2";
    public static final int     MQTT_QOS_PUBLISH                    = 2;
    public static final int     MQTT_QOS_SUBSCRIBE                  = 2;

    // List of all Thing Type UIDs
    public final static ThingTypeUID THING_TYPE_CTRLHOME_BRIDGE_GATEWAY = new ThingTypeUID(BINDING_ID, BRIDGE_CTRLHOME_GATEWAY);
    public final static ThingTypeUID THING_TYPE_LIVOLO_DIMMER = new ThingTypeUID(BINDING_ID, DEVICE_LIVOLO_DIMMER);
    public final static ThingTypeUID THING_TYPE_LIVOLO_ROLLERSHUTTER = new ThingTypeUID(BINDING_ID, DEVICE_LIVOLO_ROLLERSHUTTER);
    public final static ThingTypeUID THING_TYPE_LIVOLO_SWITCH = new ThingTypeUID(BINDING_ID, DEVICE_LIVOLO_SWITCH);

    // Discovery timeout
    public final static int BRIDGE_DISCOVERY_TIMEOUT_SECONDS        = 30;

    // Collections of supported devices
    public final static Collection<ThingTypeUID> SUPPORTED_ALL_THING_TYPES_UIDS = Lists.newArrayList(
            THING_TYPE_CTRLHOME_BRIDGE_GATEWAY,
            THING_TYPE_LIVOLO_DIMMER, THING_TYPE_LIVOLO_ROLLERSHUTTER, THING_TYPE_LIVOLO_SWITCH
            );
    public final static Set<ThingTypeUID> SUPPORTED_BRIDGE_THING_TYPES_UIDS = ImmutableSet.of(
            THING_TYPE_CTRLHOME_BRIDGE_GATEWAY
            );
    public final static Set<ThingTypeUID> SUPPORTED_DEVICE_THING_TYPES_UIDS = ImmutableSet.of(
            THING_TYPE_LIVOLO_DIMMER, THING_TYPE_LIVOLO_ROLLERSHUTTER, THING_TYPE_LIVOLO_SWITCH
            );

}
