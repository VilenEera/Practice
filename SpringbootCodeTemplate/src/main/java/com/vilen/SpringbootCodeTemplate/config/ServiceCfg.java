package com.vilen.SpringbootCodeTemplate.config;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import lombok.Data;

import java.util.List;

/**
 * Created by vilen on 17/10/20.
 */
@Data
@XStreamAlias("Service")
public class ServiceCfg {

    @XStreamAsAttribute
    private String name;

    private List<ConnectorCfg> connectors;
}
