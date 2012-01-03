package org.tsinghua.omedia.utils;

import junit.framework.Assert;

import org.testng.annotations.Test;

public class ConfigManagerTest {
    
    @Test
    public void testGetConfig() {
        String url = ConfigManager.getConfig().getCcnUrl();
        Assert.assertNotNull(url);
    }
}
