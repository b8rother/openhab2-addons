package org.openhab.binding.ctrlhome.internal.homie;

public class Topic {
    private String fwChecksum;
    private String fwName;
    private String fwVersion;
    private String homie;
    private String deviceId;
    private String implementation;
    private String implementationConfig;
    private String localIp;
    private String mac;
    private String name;
    private String online;
    private String statsInterval;
    private String statsSignal;
    private String statsUptime;
    private boolean bridgeDiscovered = false;
    private boolean nodeDiscovered = false;
    private String nodeId;
    private String nodeProperties;
    private String nodeType;

    public String getDeviceId() {
        return deviceId;
    }

    public String getFwChecksum() {
        return fwChecksum;
    }

    public String getFwName() {
        return fwName;
    }

    public String getFwVersion() {
        return fwVersion;
    }

    public String getHomie() {
        return homie;
    }

    public String getImplementation() {
        return implementation;
    }

    public String getImplementationConfig() {
        return implementationConfig;
    }

    public String getLocalIp() {
        return localIp;
    }

    public String getMac() {
        return mac;
    }

    public String getName() {
        return name;
    }

    public String getNodeId() {
        return nodeId;
    }

    public String getNodeProperties() {
        return nodeProperties;
    }

    public String getNodeType() {
        return nodeType;
    }

    public String getOnline() {
        return online;
    }

    public String getStatsInterval() {
        return statsInterval;
    }

    public String getStatsSignal() {
        return statsSignal;
    }

    public String getStatsUptime() {
        return statsUptime;
    }

    public boolean isBridge() {
        if (deviceId != null && nodeId == null) {
            return true;
        } else {
            return false;
        }

    }

    public boolean isBridgeDiscovered() {
        return bridgeDiscovered;
    }

    public boolean isBridgeInit() {
        if (getDeviceId() != null && getName() != null && getFwChecksum() != null && getFwName() != null
                && getFwVersion() != null && getHomie() != null && getImplementation() != null
                && getImplementationConfig() != null && getLocalIp() != null && getMac() != null
                && getStatsInterval() != null && getStatsUptime() != null) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isNode() {
        if (deviceId != null && nodeId != null) {
            return true;
        } else {
            return false;
        }

    }

    public boolean isNodeDiscovered() {
        return nodeDiscovered;
    }

    public boolean isNodeInit() {
        if (getDeviceId() != null && getNodeId() != null && getNodeProperties() != null && getNodeType() != null) {
            return true;
        } else {
            return false;
        }
    }

    public void setBridgeDiscovered(boolean discovered) {
        this.bridgeDiscovered = discovered;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;

    }

    public void setFwChecksum(String checksum) {
        this.fwChecksum = checksum;
    }

    public void setFwName(String fwName) {
        this.fwName = fwName;
    }

    public void setFwVersion(String fwVersion) {
        this.fwVersion = fwVersion;
    }

    public void setHomie(String homie) {
        this.homie = homie;
    }

    public void setImplementation(String implementation) {
        this.implementation = implementation;

    }

    public void setImplementationConfig(String config) {
        this.implementationConfig = config;
    }

    public void setLocalIp(String localIp) {
        this.localIp = localIp;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNodeDiscovered(boolean discovered) {
        this.nodeDiscovered = discovered;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public void setNodeProperties(String nodeProperties) {
        this.nodeProperties = nodeProperties;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public void setOnline(String online) {
        this.online = online;
    }

    public void setStatsInterval(String interval) {
        this.statsInterval = interval;
    }

    public void setStatsSignal(String statsSignal) {
        this.statsSignal = statsSignal;
    }

    public void setStatsUptime(String statsUptime) {
        this.statsUptime = statsUptime;
    }

    public void update(Topic topic) {
        if (topic.getFwChecksum() != null) {
            this.setFwChecksum(topic.getFwChecksum());
        }
        if (topic.getFwName() != null) {
            this.setFwName(topic.getFwName());
        }
        if (topic.getFwVersion() != null) {
            this.setFwVersion(topic.getFwVersion());
        }
        if (topic.getHomie() != null) {
            this.setHomie(topic.getHomie());
        }
        if (topic.getImplementation() != null) {
            this.setImplementation(topic.getImplementation());
        }
        if (topic.getImplementationConfig() != null) {
            this.setImplementationConfig(topic.getImplementationConfig());
        }
        if (topic.getLocalIp() != null) {
            this.setLocalIp(topic.getLocalIp());
        }
        if (topic.getMac() != null) {
            this.setMac(topic.getMac());
        }
        if (topic.getName() != null) {
            this.setName(topic.getName());
        }
        if (topic.getOnline() != null) {
            this.setOnline(topic.getOnline());
        }
        if (topic.getStatsInterval() != null) {
            this.setStatsInterval(topic.getStatsInterval());
        }
        if (topic.getStatsSignal() != null) {
            this.setStatsSignal(topic.getStatsSignal());
        }
        if (topic.getStatsUptime() != null) {
            this.setStatsUptime(topic.getStatsUptime());
        }
        if (topic.getNodeProperties() != null) {
            this.setNodeProperties(topic.getNodeProperties());
        }
        if (topic.getNodeType() != null) {
            this.setNodeType(topic.getNodeType());
        }
    }

}
