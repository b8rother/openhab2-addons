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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link CtrlHomeDeviceDiscoveryService} class is used to discover ctrlHome devices
 * that are connected to the Wifi network via MQTT.
 *
 * @author Luka Bartonicek - Initial contribution
 */
public class CtrlHomeDeviceDiscoveryService extends AbstractDiscoveryService implements IMqttMessageListener {

    private final static int SEARCH_TIME = 60;

    private final Logger logger = LoggerFactory.getLogger(CtrlHomeDeviceDiscoveryService.class);

    public CtrlHomeDeviceDiscoveryService(int timeout) throws IllegalArgumentException {
        super(timeout);
        // TODO Auto-generated constructor stub
    }

    // private MaxCubeBridgeHandler maxCubeBridgeHandler;
    //
    // public CtrlHomeDeviceDiscoveryService(MaxCubeBridgeHandler maxCubeBridgeHandler) {
    // super(MaxBinding.SUPPORTED_DEVICE_THING_TYPES_UIDS, SEARCH_TIME, true);
    // this.maxCubeBridgeHandler = maxCubeBridgeHandler;
    // }
    //
    // public void activate() {
    // maxCubeBridgeHandler.registerDeviceStatusListener(this);
    // }
    //
    // @Override
    // public void deactivate() {
    // maxCubeBridgeHandler.unregisterDeviceStatusListener(this);
    // removeOlderResults(new Date().getTime());
    // }
    //
    // @Override
    // public Set<ThingTypeUID> getSupportedThingTypes() {
    // return MaxBinding.SUPPORTED_DEVICE_THING_TYPES_UIDS;
    // }
    //
    // @Override
    // public void onDeviceAdded(Bridge bridge, Device device) {
    // logger.trace("Adding new MAX! {} with id '{}' to smarthome inbox", device.getType(), device.getSerialNumber());
    // ThingUID thingUID = null;
    // switch (device.getType()) {
    // case WallMountedThermostat:
    // thingUID = new ThingUID(MaxBinding.WALLTHERMOSTAT_THING_TYPE, bridge.getUID(),
    // device.getSerialNumber());
    // break;
    // case HeatingThermostat:
    // thingUID = new ThingUID(MaxBinding.HEATINGTHERMOSTAT_THING_TYPE, bridge.getUID(),
    // device.getSerialNumber());
    // break;
    // case HeatingThermostatPlus:
    // thingUID = new ThingUID(MaxBinding.HEATINGTHERMOSTATPLUS_THING_TYPE, bridge.getUID(),
    // device.getSerialNumber());
    // break;
    // case ShutterContact:
    // thingUID = new ThingUID(MaxBinding.SHUTTERCONTACT_THING_TYPE, bridge.getUID(),
    // device.getSerialNumber());
    // break;
    // case EcoSwitch:
    // thingUID = new ThingUID(MaxBinding.ECOSWITCH_THING_TYPE, bridge.getUID(), device.getSerialNumber());
    // break;
    // default:
    // break;
    // }
    // if (thingUID != null) {
    // String name = device.getName();
    // if (name.isEmpty()) {
    // name = device.getSerialNumber();
    // }
    // DiscoveryResult discoveryResult = DiscoveryResultBuilder.create(thingUID)
    // .withProperty(MaxBinding.PROPERTY_SERIAL_NUMBER, device.getSerialNumber())
    // .withBridge(bridge.getUID()).withLabel(device.getType() + ": " + name)
    // .withRepresentationProperty(MaxBinding.PROPERTY_SERIAL_NUMBER).build();
    // thingDiscovered(discoveryResult);
    // } else {
    // logger.debug("Discovered MAX! device is unsupported: type '{}' with id '{}'", device.getType(),
    // device.getSerialNumber());
    // }
    // }
    //
    // @Override
    // protected void startScan() {
    // if (maxCubeBridgeHandler != null) {
    // maxCubeBridgeHandler.clearDeviceList();
    // maxCubeBridgeHandler.deviceInclusion();
    // }
    // }

    @Override
    public void messageArrived(String arg0, MqttMessage arg1) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    protected void startScan() {
        // TODO Auto-generated method stub

    }
}
