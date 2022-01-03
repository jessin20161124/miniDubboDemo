package com.example.demo.service;

import com.jessin.practice.dubbo.model.DomainInfo;
import com.jessin.practice.dubbo.model.UserParam;
import com.jessin.practice.dubbo.spring.processor.Service;
import com.jessin.practice.dubbo.service.DomainService;

/**
 * @Author: jessin
 * @Date: 2022/1/1 1:26 下午
 */
@Service(group="myGroup", version="1.0.0")
public class DomainServiceImpl implements DomainService {

    @Override
    public DomainInfo queryAssociatedDomain(UserParam userParam) {
        DomainInfo domainInfo = new DomainInfo();
        domainInfo.setName(userParam.getName());
        domainInfo.setDesc("域名信息");
        domainInfo.setSourceType(userParam.getId());
        return domainInfo;
    }
}
