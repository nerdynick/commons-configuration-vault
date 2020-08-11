# commons-configuration-vault
Hashicorp Vault integration for Apache Commons Configuration.

[![Maintainability](https://api.codeclimate.com/v1/badges/13691a804e8bc4a7cf47/maintainability)](https://codeclimate.com/github/nerdynick/commons-configuration-vault/maintainability)
[![Test Coverage](https://api.codeclimate.com/v1/badges/13691a804e8bc4a7cf47/test_coverage)](https://codeclimate.com/github/nerdynick/commons-configuration-vault/test_coverage)
[![Travis CI: Build Status](https://travis-ci.com/nerdynick/commons-configuration-vault.svg?branch=master)](https://travis-ci.com/nerdynick/commons-configuration-vault)

# Getting Started

## Maven Dependency

```xml
<dependency>
    <groupId>com.nerdynick</groupId>
    <artifactId>commons-configuration2-vault</artifactId>
    <version>${version}</version>
</dependency>
```

## Basic Usage

**Example Config File:**
```yaml
credentials:
  username: "${vault:my/path:my.username}"
  password: "${vault:my/path:my.password}"
```

**Example Java Usage:**
```java
final Vault myVault = new Vault(new VaultConfig()
    .address("http://vault.example.com")
    .token("my-token")
    .build());

final Map<String, Lookup> lookups = new HashMap<>();
lookups.put("vault", new VaultLookup(vault));

final MapConfiguration configs = new MapConfiguration(new HashMap<>());
configs.setPrefixLookups(lookups);
```

See [Vault Java Driver](https://github.com/BetterCloud/vault-java-driver) docs for more examples and details on how to create the Vault Client.

Packaged with this library is also a utility, [VaultConfigUtils.java](commons-configuration2/src/main/java/com/nerdynick/commons/configuration/VaultConfigUtils.java), to assist with creating VaultConfig and SslConfig instances using [commons-configuration](https://commons.apache.org/proper/commons-configuration).