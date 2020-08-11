package com.nerdynick.commons.configuration.interpol;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import com.bettercloud.vault.Vault;
import com.bettercloud.vault.VaultException;
import com.bettercloud.vault.api.Logical;
import com.bettercloud.vault.response.LogicalResponse;

import org.apache.commons.configuration2.MapConfiguration;
import org.junit.Test;
import org.mockito.Mockito;

public class VaultLookupTest {
    @Test
    public void BasicTest() throws VaultException {
        final Vault vault = Mockito.mock(Vault.class);
        final Logical logical = Mockito.mock(Logical.class);
        final LogicalResponse response1 = Mockito.mock(LogicalResponse.class);
        final LogicalResponse response2 = Mockito.mock(LogicalResponse.class);

        Mockito.when(vault.logical()).thenReturn(logical);
        Mockito.when(logical.read("test/1")).thenReturn(response1);
        Mockito.when(logical.read("test/2")).thenReturn(response2);

        final Map<String, String> map1 = new HashMap<>();
        map1.put("test", "value1");
        final Map<String, String> map2 = new HashMap<>();
        map2.put("test", "value2");
        Mockito.when(response1.getData()).thenReturn(map1);
        Mockito.when(response2.getData()).thenReturn(map2);

        final MapConfiguration configs = new MapConfiguration(new HashMap<>());
        configs.getInterpolator().registerLookup("vault", new VaultLookup(vault));
        configs.addProperty("value.1", "${vault:test/1:test}");
        configs.addProperty("value.2", "${vault:test/2:test}");

        assertEquals("value1", configs.getString("value.1"));
        assertEquals("value2", configs.getString("value.2"));
    }

    // @Test
    // public void IntegrationTest() throws VaultException {
    //     final Vault vault = new Vault(new VaultConfig()
    //         .address("http://localhost:8200")
    //         .token("s.nsR6pSTzShZ5iH0dRyiRFq29")
    //         .build());

    //     final MapConfiguration configs = new MapConfiguration(new HashMap<>());
    //     configs.getInterpolator().registerLookup("vault", new VaultLookup(vault));
    //     configs.addProperty("value.1", "${vault:secret/example:data.1}");

    //     assertEquals("value.1", configs.getString("value.1"));
    // }
}
    