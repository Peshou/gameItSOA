package com.gameit.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("gameit")
public class GameitProperties {
    private final Oauth oauth = new Oauth();

    public Oauth getOauth() {
        return this.oauth;
    }

    public StripeProperties getStripe() {
        return this.stripe;
    }

    private final StripeProperties stripe = new StripeProperties();

    public static class Oauth {

        private String clientId;

        private String clientSecret;

        private Integer tokenValidityInSeconds;

        public String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        public String getClientSecret() {
            return clientSecret;
        }

        public void setClientSecret(String clientSecret) {
            this.clientSecret = clientSecret;
        }

        public Integer getTokenValidityInSeconds() {
            return tokenValidityInSeconds;
        }

        public void setTokenValidityInSeconds(Integer tokenValidityInSeconds) {
            this.tokenValidityInSeconds = tokenValidityInSeconds;
        }
    }

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