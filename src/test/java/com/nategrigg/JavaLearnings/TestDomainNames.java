package com.nategrigg.JavaLearnings;

import org.apache.commons.validator.routines.DomainValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.assertj.core.api.Assertions.assertThat;

public class TestDomainNames {
    static final boolean DO_ALLOW_LOCAL = true;
    DomainValidator domainValidator;

    @BeforeEach
    void setUp() {
        domainValidator = DomainValidator.getInstance();
    }
    @Test
    void testLocalhost() throws UnknownHostException {
        InetAddress localhost = InetAddress.getByName(null);
        assertThat(localhost.getHostName()).isEqualTo("localhost");
        assertThat(localhost.getAddress()).isEqualTo(new byte[]{127, 0, 0, 1});
    }
    @Test
    void testParseRealDomain() {
        assertThat(domainValidator.isValid("nategrigg.com")).isTrue();
    }

    @Test
    void testImaginaryHostName() {
        assertThat(domainValidator.isValid("foolog.nategrigg.com")).isTrue();
    }

    @Test
    void testUrl() {
        assertThat(domainValidator.isValid("https://www.google.com")).isFalse();
    }
}
