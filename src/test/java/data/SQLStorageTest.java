package data;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class SQLStorageTest {
    private static String originalDatabaseUrl;

    @BeforeAll
    static void beforeAll() {
        originalDatabaseUrl = String.format("%s", SQLStorage.getDatabaseUrl());
    }

    @Test
    void canMakeConnection() {
        assertTrue(SQLStorage.canMakeConnection());
    }

    @Test
    void canMakeConnectionInvalid() {
        SQLStorage.setDatabaseUrl("jdbc:mysql://192.168.178.20:5000/sushifileshare?useSSL=false&serverTimezone=UTC");
        assertFalse(SQLStorage::canMakeConnection);
    }

    @AfterEach
    void afterEach() {
        SQLStorage.setDatabaseUrl(originalDatabaseUrl);
    }
}
