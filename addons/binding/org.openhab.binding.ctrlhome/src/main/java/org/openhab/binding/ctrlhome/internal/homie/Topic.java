package org.openhab.binding.ctrlhome.internal.homie;

public class Topic {
    private String deviceFwChecksum;
    private String deviceFwName;
    private String deviceFwVersion;
    private String deviceHomie;
    private String deviceId;
    private String deviceImplementation;
    private String deviceImplementationConfig;
    private String deviceLocalIp;
    private String deviceMac;
    private String deviceName;
    private String deviceOnline;
    private String deviceStatsInterval;
    private String deviceStatsSignal;
    private String deviceStatsUptime;
    private boolean discovered = false;
    private String nodeId;
    private String nodeProperties;
    private String nodeType;

    public String getDeviceFwChecksum() {
        return deviceFwChecksum;
    }

    public String getDeviceFwName() {
        return deviceFwName;
    }

    public String getDeviceFwVersion() {
        return deviceFwVersion;
    }

    public String getDeviceHomie() {
        return deviceHomie;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getDeviceImplementation() {
        return deviceImplementation;
    }

    public String getDeviceImplementationConfig() {
        return deviceImplementationConfig;
    }

    public String getDeviceLocalIp() {
        return deviceLocalIp;
    }

    public String getDeviceMac() {
        return deviceMac;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getDeviceOnline() {
        return deviceOnline;
    }

    public String getDeviceStatsInterval() {
        return deviceStatsInterval;
    }

    public String getDeviceStatsSignal() {
        return deviceStatsSignal;
    }

    public String getDeviceStatsUptime() {
        return deviceStatsUptime;
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

    public boolean isBridge() {
        if (deviceId != null && nodeId == null) {
            return true;
        } else {
            return false;
        }

    }

    public boolean isBridgeInit() {
        if (getDeviceId() != null && getDeviceName() != null && getDeviceFwName() != null
                && getDeviceFwVersion() != null && getDeviceHomie() != null && getDeviceImplementation() != null
                && getDeviceImplementationConfig() != null && getDeviceLocalIp() != null && getDeviceMac() != null
                && getDeviceStatsInterval() != null && getDeviceStatsUptime() != null) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isDiscovered() {
        return discovered;
    }

    public void setDeviceFwChecksum(String checksum) {
        this.deviceFwChecksum = checksum;
    }

    public void setDeviceFwName(String deviceFwName) {
        this.deviceFwName = deviceFwName;
    }

    public void setDeviceFwVersion(String deviceFwVersion) {
        this.deviceFwVersion = deviceFwVersion;
    }

    public void setDeviceHomie(String deviceHomie) {
        this.deviceHomie = deviceHomie;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;

    }

    public void setDeviceImplementation(String deviceImplementation) {
        this.deviceImplementation = deviceImplementation;

    }

    public void setDeviceImplementationConfig(String config) {
        this.deviceImplementationConfig = config;
    }

    public void setDeviceLocalIp(String deviceLocalIp) {
        this.deviceLocalIp = deviceLocalIp;
    }

    public void setDeviceMac(String deviceMac) {
        this.deviceMac = deviceMac;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public void setDeviceOnline(String deviceOnline) {
        this.deviceOnline = deviceOnline;
    }

    public void setDeviceStatsInterval(String interval) {
        this.deviceStatsInterval = interval;
    }

    public void setDeviceStatsSignal(String deviceStatsSignal) {
        this.deviceStatsSignal = deviceStatsSignal;
    }

    public void setDeviceStatsUptime(String deviceStatsUptime) {
        this.deviceStatsUptime = deviceStatsUptime;
    }

    public void setDiscovered(boolean discovered) {
        this.discovered = discovered;
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

    public void update(Topic topic) {
        if (topic.getDeviceFwChecksum() != null) {
            this.setDeviceFwChecksum(topic.getDeviceFwChecksum());
        }
        if (topic.getDeviceFwName() != null) {
            this.setDeviceFwName(topic.getDeviceFwName());
        }
        if (topic.getDeviceFwVersion() != null) {
            this.setDeviceFwVersion(topic.getDeviceFwVersion());
        }
        if (topic.getDeviceHomie() != null) {
            this.setDeviceHomie(topic.getDeviceHomie());
        }
        if (topic.getDeviceImplementation() != null) {
            this.setDeviceImplementation(topic.getDeviceImplementation());
        }
        if (topic.getDeviceImplementationConfig() != null) {
            this.setDeviceImplementationConfig(topic.getDeviceImplementationConfig());
        }
        if (topic.getDeviceLocalIp() != null) {
            this.setDeviceLocalIp(topic.getDeviceLocalIp());
        }
        if (topic.getDeviceMac() != null) {
            this.setDeviceMac(topic.getDeviceMac());
        }
        if (topic.getDeviceName() != null) {
            this.setDeviceName(topic.getDeviceName());
        }
        if (topic.getDeviceOnline() != null) {
            this.setDeviceOnline(topic.getDeviceOnline());
        }
        if (topic.getDeviceStatsInterval() != null) {
            this.setDeviceStatsInterval(topic.getDeviceStatsInterval());
        }
        if (topic.getDeviceStatsSignal() != null) {
            this.setDeviceStatsSignal(topic.getDeviceStatsSignal());
        }
        if (topic.getDeviceStatsUptime() != null) {
            this.setDeviceStatsUptime(topic.getDeviceStatsUptime());
        }
        if (topic.getNodeProperties() != null) {
            this.setNodeProperties(topic.getNodeProperties());
        }
        if (topic.getNodeType() != null) {
            this.setNodeType(topic.getNodeType());
        }
    }
}
