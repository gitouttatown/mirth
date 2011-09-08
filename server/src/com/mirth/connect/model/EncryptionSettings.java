package com.mirth.connect.model;

import java.util.Properties;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("encryptionSettings")
public class EncryptionSettings extends AbstractSettings implements Auditable {
    public static final String DEFAULT_ENCRYPTION_ALGORITHM = "AES";
    public static final String DEFAULT_DIGEST_ALGORITHM = "SHA256";
    public static final String DEFAULT_SECURTITY_PROVIDER = BouncyCastleProvider.class.getName();
    public static final String DEFAULT_ENCRYPTION_KEY_LENGTH = "128";

    private static final String ENCRYPTION_EXPORT = "encryption.export";
    private static final String ENCRYPTION_PROPERTIES = "encryption.properties";
    private static final String ENCRYPTION_ALGORITHM = "encryption.algorithm";
    private static final String ENCRYPTION_KEY_LENGTH = "encryption.keylength";
    private static final String DIGEST_ALGORITHM = "digest.algorithm";
    private static final String SECURITY_PROVIDER = "security.provider";

    private Boolean encryptExport = false;
    private Boolean encryptProperties = false;
    private String encryptionAlgorithm = null;
    private int encryptionKeyLength = -1;
    private String digestAlgorithm = null;
    private String securityProvider = null;
    private byte[] secretKey = null;

    public EncryptionSettings() {

    }

    public EncryptionSettings(Properties properties) {
        setProperties(properties);
    }

    public Boolean getEncryptExport() {
        return encryptExport;
    }

    public void setEncryptExport(Boolean encryptExport) {
        this.encryptExport = encryptExport;
    }

    public Boolean getEncryptProperties() {
        return encryptProperties;
    }

    public void setEncryptProperties(Boolean encryptProperties) {
        this.encryptProperties = encryptProperties;
    }

    public String getEncryptionAlgorithm() {
        return encryptionAlgorithm;
    }

    public void setEncryptionAlgorithm(String encryptionAlgorithm) {
        this.encryptionAlgorithm = encryptionAlgorithm;
    }

    public int getEncryptionKeyLength() {
        return encryptionKeyLength;
    }

    public void setEncryptionKeyLength(int encryptionKeyLength) {
        this.encryptionKeyLength = encryptionKeyLength;
    }

    public String getDigestAlgorithm() {
        return digestAlgorithm;
    }

    public void setDigestAlgorithm(String digestAlgorithm) {
        this.digestAlgorithm = digestAlgorithm;
    }

    public String getSecurityProvider() {
        return securityProvider;
    }

    public void setSecurityProvider(String securityProvider) {
        this.securityProvider = securityProvider;
    }

    public byte[] getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(byte[] secretKey) {
        this.secretKey = secretKey;
    }

    @Override
    public void setProperties(Properties properties) {
        setEncryptExport(intToBooleanObject(properties.getProperty(ENCRYPTION_EXPORT, "0")));
        setEncryptProperties(intToBooleanObject(properties.getProperty(ENCRYPTION_PROPERTIES, "0")));
        setEncryptionAlgorithm((String) properties.getProperty(ENCRYPTION_ALGORITHM, DEFAULT_ENCRYPTION_ALGORITHM));
        setEncryptionKeyLength(Integer.parseInt((String) properties.getProperty(ENCRYPTION_KEY_LENGTH, DEFAULT_ENCRYPTION_KEY_LENGTH)));
        setDigestAlgorithm((String) properties.getProperty(DIGEST_ALGORITHM, DEFAULT_DIGEST_ALGORITHM));
        setSecurityProvider((String) properties.getProperty(SECURITY_PROVIDER, DEFAULT_SECURTITY_PROVIDER));
    }

    @Override
    public Properties getProperties() {
        Properties properties = new Properties();

        if (getEncryptExport() != null) {
            properties.put(ENCRYPTION_EXPORT, getEncryptExport());
        }

        if (getEncryptProperties() != null) {
            properties.put(ENCRYPTION_PROPERTIES, getEncryptProperties());
        }

        if (getEncryptionAlgorithm() != null) {
            properties.put(ENCRYPTION_ALGORITHM, getEncryptionAlgorithm());
        }

        if (getEncryptionKeyLength() != -1) {
            properties.put(ENCRYPTION_KEY_LENGTH, getEncryptionKeyLength());
        }

        if (getDigestAlgorithm() != null) {
            properties.put(DIGEST_ALGORITHM, getDigestAlgorithm());
        }

        if (getSecurityProvider() != null) {
            properties.put(SECURITY_PROVIDER, getSecurityProvider());
        }

        return properties;
    }

    @Override
    public String toAuditString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE).toString();
    }

}