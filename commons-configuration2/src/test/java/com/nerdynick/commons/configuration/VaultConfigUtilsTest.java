package com.nerdynick.commons.configuration;

import java.io.File;
import java.util.HashMap;

import com.bettercloud.vault.SslConfig;
import com.bettercloud.vault.VaultConfig;
import com.bettercloud.vault.VaultException;

import org.apache.commons.configuration2.MapConfiguration;
import org.junit.Test;
import org.mockito.Mockito;

public class VaultConfigUtilsTest {

    @Test
    public void PopulateFromConfigs() throws VaultException {
        final MapConfiguration config = new MapConfiguration(new HashMap<>());
        config.addProperty(VaultConfigUtils.CONFIG_ADDRESS, "http://vault.com");
        config.addProperty(VaultConfigUtils.CONFIG_TOKEN, "my_token");
        
        final VaultConfig vConfig = Mockito.mock(VaultConfig.class, Mockito.CALLS_REAL_METHODS);
        final SslConfig sslConfig = Mockito.mock(SslConfig.class);
        Mockito.when(sslConfig.keyStoreFile(Mockito.any(File.class), Mockito.anyString())).thenReturn(sslConfig);
        Mockito.when(sslConfig.trustStoreFile(Mockito.any(File.class))).thenReturn(sslConfig);

        VaultConfigUtils.PopulateFromConfigs(config, vConfig, sslConfig);

        Mockito.verify(sslConfig, Mockito.times(0)).keyStoreFile(Mockito.any(File.class), Mockito.anyString());
        Mockito.verify(sslConfig, Mockito.times(0)).trustStoreFile(Mockito.any(File.class));
        Mockito.verify(sslConfig, Mockito.times(1)).build();

        Mockito.verify(vConfig, Mockito.times(1)).address("http://vault.com");
        Mockito.verify(vConfig, Mockito.times(1)).token("my_token");

    }
}