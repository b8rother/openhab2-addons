/**
 * Copyright (c) 2014-2016 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.ctrlhome;

import org.eclipse.smarthome.core.thing.ThingTypeUID;

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
    public static final String  DEVICE_CTRLHOME_BRIDGE              = "bridge";
    public static final String  DEVICE_LIVOLO_DIMMER                = "livoloDimmer";
    public static final String  DEVICE_LIVOLO_ROLLERSHUTTER         = "livoloRollershutter";
    public static final String  DEVICE_LIVOLO_SWITCH                = "livoloSwitch";

    // MQTT fixed config
    public static final String  MQTT_CLIENT_ID_DELIMITER            = "_";
    public static final String  MQTT_CLIENT_ID_PREFIX               = "ctrlHomeOH2";
    public static final int     MQTT_QOS_PUBLISH                    = 2;
    public static final int     MQTT_QOS_SUBSCRIBE                  = 2;

    // List of all Thing Type UIDs
    public final static ThingTypeUID THING_TYPE_LIVOLO_DIMMER = new ThingTypeUID(BINDING_ID, DEVICE_LIVOLO_DIMMER);
    public final static ThingTypeUID THING_TYPE_LIVOLO_ROLLERSHUTTER = new ThingTypeUID(BINDING_ID,
            DEVICE_LIVOLO_ROLLERSHUTTER);
    public final static ThingTypeUID THING_TYPE_LIVOLO_SWITCH = new ThingTypeUID(BINDING_ID, DEVICE_LIVOLO_SWITCH);

}
