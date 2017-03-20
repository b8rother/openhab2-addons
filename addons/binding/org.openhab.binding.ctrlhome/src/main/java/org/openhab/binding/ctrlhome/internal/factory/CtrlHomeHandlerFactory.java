/**
 * Copyright (c) 2014-2016 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.ctrlhome.internal.factory;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import org.eclipse.smarthome.config.core.Configuration;
import org.eclipse.smarthome.config.discovery.DiscoveryService;
import org.eclipse.smarthome.core.thing.Bridge;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingTypeUID;
import org.eclipse.smarthome.core.thing.ThingUID;
import org.eclipse.smarthome.core.thing.binding.BaseThingHandlerFactory;
import org.eclipse.smarthome.core.thing.binding.ThingHandler;
import org.openhab.binding.ctrlhome.CtrlHomeBindingConstants;
import org.openhab.binding.ctrlhome.handler.CtrlHomeBridgeHandler;
import org.openhab.binding.ctrlhome.handler.CtrlHomeDeviceHandler;
import org.openhab.binding.ctrlhome.internal.config.CtrlHomeConfiguration;
import org.openhab.binding.ctrlhome.internal.discovery.CtrlHomeBridgeDiscoveryService;
import org.openhab.binding.ctrlhome.internal.discovery.CtrlHomeDeviceDiscoveryService;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link CtrlHomeHandlerFactory} is responsible for creating things and thing
 * handlers.
 *
 * @author Luka Bartonicek - Initial contribution
 */
public class CtrlHomeHandlerFactory extends BaseThingHandlerFactory {
    private static Logger logger = LoggerFactory.getLogger(CtrlHomeHandlerFactory.class);
    private CtrlHomeConfiguration configuration;

    private Map<ThingUID, ServiceRegistration<?>> discoveryServiceRegs = new HashMap<>();

    @Override
    protected void activate(ComponentContext componentContext) {
        super.activate(componentContext);
        configuration = new CtrlHomeConfiguration(componentContext.getProperties());

        // registration and activation of Bridge Discovery Service
        CtrlHomeBridgeDiscoveryService discoveryService = new CtrlHomeBridgeDiscoveryService(configuration);
        bundleContext.registerService(DiscoveryService.class.getName(), discoveryService,
                new Hashtable<String, Object>());
    }

    @Override
    protected ThingHandler createHandler(Thing thing) {
        if (thing.getThingTypeUID().equals(CtrlHomeBindingConstants.THING_TYPE_CTRLHOME_BRIDGE_GATEWAY)) {
            CtrlHomeBridgeHandler bridgeHandler = new CtrlHomeBridgeHandler((Bridge) thing);
            CtrlHomeDeviceDiscoveryService discoveryService = new CtrlHomeDeviceDiscoveryService(configuration,
                    bridgeHandler);
            discoveryService.activate();

            this.discoveryServiceRegs.put(bridgeHandler.getThing().getUID(), bundleContext.registerService(
                    DiscoveryService.class.getName(), discoveryService, new Hashtable<String, Object>()));
            return bridgeHandler;
        } else if (supportsThingType(thing.getThingTypeUID())) {
            return new CtrlHomeDeviceHandler(thing, configuration);
        } else {
            logger.debug("ThingHandler not found for {}", thing.getThingTypeUID());
            return null;
        }
    }

    @Override
    public Thing createThing(ThingTypeUID thingTypeUID, Configuration configuration, ThingUID thingUID,
            ThingUID bridgeUID) {
        if (CtrlHomeBindingConstants.THING_TYPE_CTRLHOME_BRIDGE_GATEWAY.equals(thingTypeUID)) {
            return super.createThing(thingTypeUID, configuration, thingUID, null);
        }
        if (supportsThingType(thingTypeUID)) {
            return super.createThing(thingTypeUID, configuration, thingUID, bridgeUID);
        }
        throw new IllegalArgumentException("The thing type " + thingTypeUID + " is not supported by the binding.");
    }

    @Override
    protected void removeHandler(ThingHandler thingHandler) {
        if (thingHandler instanceof CtrlHomeBridgeHandler) {
            ServiceRegistration<?> serviceReg = this.discoveryServiceRegs.get(thingHandler.getThing().getUID());
            if (serviceReg != null) {
                // remove discovery service, if bridge handler is removed
                CtrlHomeDeviceDiscoveryService service = (CtrlHomeDeviceDiscoveryService) bundleContext
                        .getService(serviceReg.getReference());
                service.deactivate();
                serviceReg.unregister();
                discoveryServiceRegs.remove(thingHandler.getThing().getUID());
            }
        }
        super.removeHandler(thingHandler);
    }

    @Override
    public boolean supportsThingType(ThingTypeUID thingTypeUID) {
        return CtrlHomeBindingConstants.SUPPORTED_ALL_THING_TYPES_UIDS.contains(thingTypeUID);
    }
}
