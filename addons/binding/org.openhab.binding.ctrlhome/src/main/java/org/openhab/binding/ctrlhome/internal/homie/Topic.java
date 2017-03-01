package org.openhab.binding.ctrlhome.internal.homie;

public class Topic {
    private String baseTopic;
    private String deviceFwName;
    private String deviceFwVersion;
    private String deviceHomie;
    private String deviceId;
    private String deviceImplementation;
    private String deviceLocalIp;
    private String deviceMac;
    private String deviceName;
    private String deviceOnline;
    private String deviceStatsSignal;
    private String deviceStatsUptime;
    private String nodeId;
    private String nodeProperties;
    private String nodeType;

    public String getDeviceId() {
        return deviceId;
    }

    public boolean isBridge() {
        if (deviceId != null && nodeId == null) {
            return true;
        } else {
            return false;
        }

    }

    public void setBaseTopic(String baseTopic) {
        this.baseTopic = baseTopic;
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

    public void setDeviceStatsSignal(String deviceStatsSignal) {
        this.deviceStatsSignal = deviceStatsSignal;
    }

    public void setDeviceStatsUptime(String deviceStatsUptime) {
        this.deviceStatsUptime = deviceStatsUptime;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public void setProperties(String nodeProperties) {
        this.nodeProperties = nodeProperties;
    }
}
