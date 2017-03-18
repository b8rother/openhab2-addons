/**
 * Copyright (c) 2014-2016 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.ctrlhome.handler;

import org.eclipse.smarthome.core.thing.Bridge;
import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.thing.ThingStatus;
import org.eclipse.smarthome.core.thing.binding.BaseBridgeHandler;
import org.eclipse.smarthome.core.types.Command;
import org.eclipse.smarthome.core.types.RefreshType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link CtrlHomeBridgeHandler} is responsible for handling commands, which are
 * sent to one of the channels.
 *
 * @author Luka Bartonicek - Initial contribution
 */
public class CtrlHomeBridgeHandler extends BaseBridgeHandler {
    private Logger logger = LoggerFactory.getLogger(CtrlHomeBridgeHandler.class);

    public CtrlHomeBridgeHandler(Bridge bridge) {
        super(bridge);
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
        if (command instanceof RefreshType) {
            logger.debug("Refresh command received.");
        } else {
            logger.warn("No bridge commands defined. Cannot process '{}'.", command.toString());
        }
    }

    @Override
    public void initialize() {
        logger.debug("Initializing ctrHome Gateway bridge handler.");
        updateStatus(ThingStatus.ONLINE);

        // Note: When initialization can NOT be done set the status with more details for further
        // analysis. See also class ThingStatusDetail for all available status details.
        // Add a description to give user information to understand why thing does not work
        // as expected. E.g.
        // updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR,
        // "Can not access device as username and/or password are invalid");

        // MaxCubeBridgeConfiguration configuration = getConfigAs(MaxCubeBridgeConfiguration.class);
        // port = configuration.port;
    }
}
