package com.vilen.SpringbootCodeTemplate.config;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import lombok.Data;

import java.util.List;

/**
 * Created by vilen on 17/10/20.
 */

@Data
@XStreamAlias("Server")
public class ServerCfg {

    @XStreamAsAttribute
    private int port = 8005;

    @XStreamAsAttribute
    private String shutDown = "SHUTDOWN";

    private List<ServiceCfg> services;

}