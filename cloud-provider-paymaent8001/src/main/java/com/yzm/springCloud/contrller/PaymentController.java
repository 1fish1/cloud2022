package com.yzm.springCloud.contrller;


import com.yzm.api.commons.entities.CommonResult;
import com.yzm.api.commons.entities.Payment;
import com.yzm.springCloud.service.PaymentService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
public class PaymentController {

    //    private final static Integer serverPort = 8001;
    @Value("${server.port}")
    private String serverPort;

    private DiscoveryClient discoveryClient;

    @Resource
    private PaymentService paymentService;

    @PostMapping(value = "/payment/create")
    public CommonResult create(@RequestBody Payment payment) {
        int result = paymentService.create(payment);
        log.info("*****插入结果：" + result + "asdasdadsasd");

        if (result > 0) {
            return new CommonResult(200, "插入数据库成功,serverPort: " + serverPort, result + "UUID= " + UUID.randomUUID().toString());
        } else {
            return new CommonResult(444, "插入数据库失败", null);
        }
    }

    @GetMapping(value = "/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id) {
        Payment payment = paymentService.getPaymentById(id);
        log.info("*****获取结果：" + id + "中文环境中文环境");
        if (payment != null) {
            return new CommonResult(200, "查询成功,serverPort:  " + serverPort, payment + "UUID= " + UUID.randomUUID().toString());
        } else {
            return new CommonResult(444, "没有对应记录,查询ID: " + id, null);
        }
    }
    @GetMapping(value = "/payment/discovery")
    public Object discovery()
    {
        List<String> services = discoveryClient.getServices();
        for (String element : services) {
            log.info("*****element: "+element);
        }

        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance instance : instances) {
            log.info(instance.getServiceId()+"\t"+instance.getHost()+"\t"+instance.getPort()+"\t"+instance.getUri());
        }

        return this.discoveryClient;
    }

}
