package org.epoch.java.basic.spi;

import java.util.ServiceLoader;

/**
 * @author Marshal
 * @since 2022/11/3
 */
public class Client {
    public static void main(String[] args) {
        ServiceLoader<SpiService> spiServices = ServiceLoader.load(SpiService.class);
        spiServices.forEach(SpiService::process);
    }
}
