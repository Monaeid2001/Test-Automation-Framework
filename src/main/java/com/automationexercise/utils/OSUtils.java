package com.automationexercise.utils;

import com.automationexercise.utils.dataReader.PropertyReader;

public class OSUtils {
    public enum OS {
        WINDOWS, MAC, LINUX, OTHER
    }
    public static OS getCurrentOS() {
        String osName = PropertyReader.getProperty("os.name").toLowerCase();
        if (osName.contains("win"))
            return OS.WINDOWS;
        if (osName.contains("mac"))
            return OS.MAC;
        if (osName.contains("nix") || osName.contains("nux") || osName.contains("aix"))
            return OS.LINUX;
        return OS.OTHER;

    }

}
