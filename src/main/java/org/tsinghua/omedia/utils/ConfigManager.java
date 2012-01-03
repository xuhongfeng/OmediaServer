package org.tsinghua.omedia.utils;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

import org.apache.log4j.Logger;
import org.tsinghua.omedia.model.Config;

/**
 * 
 * @author xuhongfeng
 *
 */
public class ConfigManager {
    private static final Logger logger = Logger.getLogger(ConfigManager.class);
    private static final long INTERVAL = 60*1000L;
    
    private static volatile long lastCheckTime = System.currentTimeMillis();
    
    private static volatile long configVersion = System.currentTimeMillis();
    
    private static final ReentrantReadWriteLock versionlock = new ReentrantReadWriteLock();
    
    private static final ReadLock versionReadLock = versionlock.readLock();
    
    private static final WriteLock versionWriteLock = versionlock.writeLock();
    
    private static volatile Config config = getNewConfig();
    
    private static final ReentrantReadWriteLock configlock = new ReentrantReadWriteLock();
    
    private static final ReadLock configReadLock = configlock.readLock();
    
    private static final WriteLock configWriteLock = configlock.writeLock();
    
    public static long getConfigVersion() {
        long oldVersion;
        versionReadLock.lock();
        try {
            if(System.currentTimeMillis()-lastCheckTime<INTERVAL) {
                return configVersion;
            }
            oldVersion = configVersion;
        } finally {
            versionReadLock.unlock();
        }
        Config newConfig = getNewConfig();
        if(oldVersion != configVersion || config.equals(newConfig)) {
            return configVersion;
        }
        versionWriteLock.lock();
        try {
            if(oldVersion != configVersion || config.equals(newConfig)) {
                return configVersion;
            }
            configWriteLock.lock();
            try {
                if(oldVersion != configVersion || config.equals(newConfig)) {
                    return configVersion;
                }
                config = newConfig;
                configVersion = System.currentTimeMillis();
                return configVersion;
            } finally {
                configWriteLock.unlock();
            }
        } finally {
            versionWriteLock.unlock();
        }
    }
    
    public static Config getConfig() {
        configReadLock.lock();
        try {
            return config;
        } finally {
            configReadLock.unlock();
        }
        
    }
    
    private static Config getNewConfig() {
        Properties p = new Properties();
        try {
            p.load(ConfigManager.class.getClassLoader().getResourceAsStream("config.properties"));
        } catch (IOException e) {
            logger.error("", e);
            return null;
        }
        Config config = new Config();
        config.setCcnUrl(p.getProperty("ccn.url"));
        config.setCcnHost(p.getProperty("ccn.host"));
        return config;
    }
}
