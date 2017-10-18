package com.vilen.SpringbootCodeTemplate.controllers;

import com.vilen.SpringbootCodeTemplate.beans.Config;
import com.vilen.SpringbootCodeTemplate.services.ConfigService;
import com.vilen.common.beans.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * Created by vilen on 2017/10/18.
 */
@RequestMapping("/config")
@RestController
@CrossOrigin
public class ConfigController {
    @Autowired
    ConfigService configService;

    @GetMapping("/all")
    public ResultBean<Collection<Config>> getAll() {
        return new ResultBean<Collection<Config>>(configService.getAll());
    }

    @PostMapping("/add")
    public ResultBean<Long> add(@RequestBody Config config) {
        return new ResultBean<Long>(configService.add(config));
    }

    @PostMapping("/delete")
    public ResultBean<Boolean> delete(long id) {
        return new ResultBean<Boolean>(configService.delete(id));
    }

}


