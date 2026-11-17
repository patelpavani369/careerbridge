package com.careerbridge.db;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class DatabaseUrlTest {
    @Test void renderStyleUrlWithoutPortGetsDefaultPostgresPort() {
        String[] c = Database.parseDatabaseUrl("postgresql://cbuser:secret@dpg-abc123-a/careerbridge_db");
        assertEquals("jdbc:postgresql://dpg-abc123-a:5432/careerbridge_db", c[0]);
        assertEquals("cbuser", c[1]);
        assertEquals("secret", c[2]);
    }

    @Test void explicitPortAndParametersAreKept() {
        String[] c = Database.parseDatabaseUrl("postgres://u:p@host.example.com:6543/db?sslmode=require");
        assertEquals("jdbc:postgresql://host.example.com:6543/db?sslmode=require", c[0]);
    }

    @Test void urlWithoutPasswordGivesEmptyPassword() {
        String[] c = Database.parseDatabaseUrl("postgres://onlyuser@host/db");
        assertEquals("onlyuser", c[1]);
        assertEquals("", c[2]);
    }

    @Test void localH2IsTheDefaultWhenNothingIsConfigured() {
        assertTrue(Database.defaultUrl().startsWith("jdbc:"));
    }
}
