/* 
    Rainpoint Irrigation Valve Controller 

    2024-12-17 tach47
        - initial code. Based off genericComponentWindowBlind

    Requires "Tuya IoT Platform (Cloud)" device
        import "https://raw.githubusercontent.com/bradsjm/hubitat-drivers/main/Tuya/TuyaOpenCloudAPI.groovy"

*/

metadata
{
    definition(name: "Rainpoint Irrigation Valve", namespace: "tach47", author: "tach47", importUrl: "https://raw.githubusercontent.com/tach47/hubitat-drivers/main/rainpointIrrigation.groovy")
    {
        capability "Valve"
    }
    preferences {
        input name: "txtEnable", type: "bool", title: "Enable descriptionText logging", defaultValue: true
    }
    attribute "switch", "enum", ["off", "on"]
}

void updated() {
    log.info "Updated..."
    log.warn "description logging is: ${txtEnable == true}"
}

void installed() {
    log.info "Installed..."
    device.updateSetting("txtEnable",[type:"bool",value:true])
    refresh()
}

void parse(String description) { log.warn "parse(String description) not implemented" }

void parse(List<Map> description) {   
    description.each {
        if (it.name in ["switch"]) {
            if (txtEnable) log.info it.descriptionText
            sendEvent(it)
        }
    }
}

void refresh() {
    parent?.componentRefresh(this.device)
}

void open() {
    parent?.componentOn(this.device)
}

void close() {
    parent?.componentOff(this.device)
}

void ping() {
    refresh()
}
