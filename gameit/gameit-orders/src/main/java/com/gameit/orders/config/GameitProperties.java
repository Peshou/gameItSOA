package com.gameit.orders.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("gameit")
public class GameitProperties {
    public StripeProperties getStripe() {
        return this.stripe;
    }

    private final StripeProperties stripe = new StripeProperties();

    public static class StripeProperties {
        private String clientId;
        private String testpk;

        public String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        public String getTestpk() {
            return testpk;
        }

        public void setTestpk(String testpk) {
            this.testpk = testpk;
        }
    }
}