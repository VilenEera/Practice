package com.vilen.SpringbootCodeTemplate.config;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import lombok.Data;

/**
 * Created by vilen on 17/10/20.
 */
@Data
@XStreamAlias("Connector")
public class ConnectorCfg {
    @XStreamAsAttribute
    private int port = 8080;

    @XStreamAsAttribute
    private String protocol = "HTTP/1.1";

    @XStreamAsAttribute
    String executor;
}
