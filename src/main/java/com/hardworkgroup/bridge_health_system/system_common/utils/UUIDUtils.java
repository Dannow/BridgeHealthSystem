package com.hardworkgroup.bridge_health_system.system_common.utils;

import java.util.UUID;

public class UUIDUtils {

    public static String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}