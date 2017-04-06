package com.gameit.security;

/**
 * Created by Stefan on 06.4.2017.
 */
public enum Authorities {
    ROLE_ANONYMOUS {
        public String toString() {
            return "ROLE_ANONYMOUS";
        }
    },
    ROLE_ADMIN {
        public String toString() {
            return "ROLE_ADMIN";
        }
    },
    ROLE_SELLER {
        public String toString() {
            return "ROLE_SELLER";
        }
    },
    ROLE_BUYER {
        public String toString() {
            return "ROLE_BUYER";
        }
    }
}