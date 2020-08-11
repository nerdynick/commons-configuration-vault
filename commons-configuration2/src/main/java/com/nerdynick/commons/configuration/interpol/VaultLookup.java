package com.nerdynick.commons.configuration.interpol;

import java.util.Map;

import com.bettercloud.vault.Vault;
import com.bettercloud.vault.VaultConfig;
import com.bettercloud.vault.VaultException;

import org.apache.commons.configuration2.interpol.Lookup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VaultLookup implements Lookup {
    private static final Logger LOG = LoggerFactory.getLogger(VaultLookup.class);

    public final Vault client;

    public VaultLookup(final VaultConfig config) {
        this(new Vault(config));
    }

    public VaultLookup(final Vault vaultClient) {
        this.client = vaultClient;
    }

    @Override
    public Object lookup(final String variable) {
        final String[] parts = variable.split(":", 2);
        LOG.debug("Looking up: {} `{}`", variable, parts);
        if (parts.length == 2) {
            try {
                final Map<String, String> data = client.logical().read(parts[0]).getData();
                return data.get(parts[1]);
            } catch (final VaultException e) {
                LOG.error("Failed to lookup value in Vault: {}", variable, e);
            }
        } else {
            LOG.error("Invalid lookup value of `{}`", variable);
        }
        
        return null;
    }
    
}