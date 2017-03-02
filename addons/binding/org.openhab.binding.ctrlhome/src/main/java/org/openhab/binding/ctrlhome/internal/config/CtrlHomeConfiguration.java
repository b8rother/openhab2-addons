/**
 * Copyright (c) 2014-2016 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.ctrlhome.internal.config;

import java.util.Dictionary;

public class CtrlHomeConfiguration {
    private String baseTopic;
    private String brokerUrl;

    public CtrlHomeConfiguration(Dictionary<String, Object> properties) {
        brokerUrl = (String) properties.get("mqttBrokerUrl");
        baseTopic = (String) properties.get("mqttBaseTopic");
    }

    public String getBaseTopic() {
        return baseTopic;
    }

    public String getBrokerUrl() {
        return brokerUrl;
    }

    public void setBaseTopic(String baseTopic) {
        this.baseTopic = baseTopic;
    }

    public void setBrokerUrl(String brokerUrl) {
        this.brokerUrl = brokerUrl;
    }

}
