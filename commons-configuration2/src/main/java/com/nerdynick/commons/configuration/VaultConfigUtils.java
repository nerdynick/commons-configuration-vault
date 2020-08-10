package com.nerdynick.commons.configuration;

import java.nio.file.Paths;

import com.bettercloud.vault.SslConfig;
import com.bettercloud.vault.VaultConfig;
import com.bettercloud.vault.VaultException;

import org.apache.commons.configuration2.Configuration;

public class VaultConfigUtils {
    public static final String CONFIG_TOKEN = "token";
    public static final String CONFIG_ADDRESS = "address";
    public static final String CONFIG_NAMESPACE = "namespace";
    public static final String CONFIG_OPEN_TIMEOUT = "open.timeout.ms";
    public static final String CONFIG_READ_TIMEOUT = "read.timeout.ms";

    public static final String CONFIG_SSL_KEYSTORE_LOCATION = "ssl.keystore.location";
    public static final String CONFIG_SSL_KEYSTORE_PASSWORD = "ssl.keystore.password";
    //public static final String CONFIG_SSL_KEY_PASSWORD = "ssl.key.password";
    public static final String CONFIG_SSL_TRUSTSTORE_LOCATION = "ssl.truststore.location";
    //public static final String CONFIG_SSL_TRUSTSTORE_PASSWORD = "ssl.truststore.password";

    public static VaultConfig PopulateFromConfigs(final Configuration configs,  VaultConfig config) throws VaultException {
        config = config.address(configs.getString(VaultConfigUtils.CONFIG_ADDRESS));
        config = config.token(configs.getString(VaultConfigUtils.CONFIG_TOKEN));

        if (configs.containsKey(VaultConfigUtils.CONFIG_OPEN_TIMEOUT)) {
            config = config.openTimeout(configs.getInt(VaultConfigUtils.CONFIG_OPEN_TIMEOUT));
        }
        if (configs.containsKey(VaultConfigUtils.CONFIG_READ_TIMEOUT)) {
            config = config.readTimeout(configs.getInt(VaultConfigUtils.CONFIG_READ_TIMEOUT));
        }
        if (configs.containsKey(VaultConfigUtils.CONFIG_NAMESPACE)) {
            config = config.nameSpace(configs.getString(VaultConfigUtils.CONFIG_NAMESPACE));
        }

        return config.build();
    }

    public static VaultConfig PopulateFromConfigs(final Configuration configs, VaultConfig config, SslConfig sslConfig) throws VaultException {
        return PopulateFromConfigs(configs, config)
            .sslConfig(PopulateFromConfigs(configs, sslConfig))
            .build();
    }

    public static SslConfig PopulateFromConfigs(final Configuration configs, SslConfig sslConfig) throws VaultException {
        if (configs.containsKey(VaultConfigUtils.CONFIG_SSL_KEYSTORE_LOCATION) && configs.containsKey(VaultConfigUtils.CONFIG_SSL_KEYSTORE_PASSWORD)) {
            sslConfig = sslConfig.keyStoreFile(Paths.get(configs.getString(VaultConfigUtils.CONFIG_SSL_KEYSTORE_LOCATION)).toFile(), configs.getString(VaultConfigUtils.CONFIG_SSL_KEYSTORE_PASSWORD));
        }
        if (configs.containsKey(VaultConfigUtils.CONFIG_SSL_TRUSTSTORE_LOCATION)) {
            sslConfig = sslConfig.trustStoreFile(Paths.get(configs.getString(VaultConfigUtils.CONFIG_SSL_TRUSTSTORE_LOCATION)).toFile());
        }

        return sslConfig.build();
    }
    
}