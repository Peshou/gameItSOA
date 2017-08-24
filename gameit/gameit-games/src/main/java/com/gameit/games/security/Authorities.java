package com.gameit.games.security;

public enum Authorities {
    ANONYMOUS {
        public String toString() {
            return "ANONYMOUS";
        }
    },
    ADMIN {
        public String toString() {
            return "ADMIN";
        }
    },
    SELLER {
        public String toString() {
            return "SELLER";
        }
    },
    BUYER {
        public String toString() {
            return "BUYER";
        }
    },
    GOOGLE {
        public String toString() {
            return "GOOGLE";
        }
    }
}