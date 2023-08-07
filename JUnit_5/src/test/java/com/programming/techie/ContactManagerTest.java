package com.programming.techie;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ContactManagerTest {
    private ContactManager contactManager;

    @BeforeAll
    public void setupAll() {
        System.out.println("Should print before all tests");
    }
    @BeforeEach
    public void setup() {
        contactManager = new ContactManager();
    }
    @Test
    @Disabled
    public void shouldCreateContact() {
        contactManager.addContact("Mark", "Doe", "0123456789");
        Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
        Assertions.assertEquals(1, contactManager.getAllContacts().size());
        Assertions.assertTrue(contactManager.getAllContacts().stream().anyMatch(c ->
            c.getFirstName().equals("Mark") &&
            c.getLastName().equals("Doe") &&
            c.getPhoneNumber().equals("0123456789")
        ));
    }
    @Test
    @DisplayName("Should not create contact when first name is null")
    public void shouldThrowRunTimeExceptionWhenFirstNameIsNull() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            contactManager.addContact(null, "Doe", "0123456789");
        });
    }
    @Test
    @DisplayName("Should not create contact when last name is null")
    public void shouldThrowRunTimeExceptionWhenLastNameIsNull() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            contactManager.addContact("John", null, "0123456789");
        });
    }
    @Test
    @DisplayName("Should not create contact when phone number is null")
    public void shouldThrowRunTimeExceptionWhenPhoneNumberIsNull() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            contactManager.addContact("Mark", "Doe", null);
        });
    }
    @AfterEach
    public void tearDown() {
        System.out.println("Should be executed after each test");
    }
    @AfterAll
    public void tearDownAll() {
        System.out.println("Should be executed after all tests");
    }
    @Test
    @DisplayName("Should create contact only on Mac OS")
    @EnabledOnOs(value = OS.MAC, disabledReason = "Enabled only on Mac OS")
    public void shouldCreateContactOnMacOs() {
        contactManager.addContact("Mark", "Doe", "0123456789");
        Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
        Assertions.assertEquals(1, contactManager.getAllContacts().size());
        Assertions.assertTrue(contactManager.getAllContacts().stream().anyMatch(c ->
                c.getFirstName().equals("Mark") &&
                        c.getLastName().equals("Doe") &&
                        c.getPhoneNumber().equals("0123456789")
        ));
    }
    @Test
    @DisplayName("Should create contact only on Windows OS")
    @EnabledOnOs(value = OS.WINDOWS, disabledReason = "Enabled only on Windows OS")
    public void shouldCreateContactOnWinOs() {
        contactManager.addContact("Mark", "Doe", "0123456789");
        Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
        Assertions.assertEquals(1, contactManager.getAllContacts().size());
        Assertions.assertTrue(contactManager.getAllContacts().stream().anyMatch(c ->
                c.getFirstName().equals("Mark") &&
                        c.getLastName().equals("Doe") &&
                        c.getPhoneNumber().equals("0123456789")
        ));
    }
    @Test
    @DisplayName("Test contact creation on developer machine")
    public void shouldCreateContactOnDevMachine() {
        Assumptions.assumeTrue("DE".equals(System.getProperty("ENV")));
        contactManager.addContact("Mark", "Doe", "0123456789");
        Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
        Assertions.assertEquals(1, contactManager.getAllContacts().size());
    }

    @Nested
    class NestedTestClass {
        @RepeatedTest(value = 5)
        @DisplayName("Repeat contact creation test 5 times")
        public void shouldCreateContactRepeatedly() {
            contactManager.addContact("Mark", "Doe", "0123456789");
            Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
            Assertions.assertEquals(1, contactManager.getAllContacts().size());
        }
    }
    @Nested
    class ParametrizedTestClass {
        private List<String> phoneNumberList() {
            return List.of("0123456789", "0123456789", "0312345789", "0123456780");
        }
        @ParameterizedTest
        @ValueSource(strings = {"0123456789", "0123456789", "0312345789", "0123456780"})
        @DisplayName("Repeat contact creation test 5 times")
        public void shouldCreateParametrizedContact(String phoneNumber) {
            contactManager.addContact("Mark", "Doe", phoneNumber);
            Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
            Assertions.assertEquals(1, contactManager.getAllContacts().size());
        }
        @ParameterizedTest
        @Disabled
        @MethodSource("phoneNumberList")
        @DisplayName("Repeat contact creation test 5 times List")
        public void shouldCreateParametrizedContactMethodSource(String phoneNumber) {
            contactManager.addContact("Mark", "Doe", phoneNumber);
            Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
            Assertions.assertEquals(1, contactManager.getAllContacts().size());
        }

        @ParameterizedTest
        @CsvSource({"0123456789", "0123456789", "0312345789", "0123456780"})
        @DisplayName("Repeat contact creation test 5 times")
        public void shouldCreateParametrizedContactCsv(String phoneNumber) {
            contactManager.addContact("Mark", "Doe", phoneNumber);
            Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
            Assertions.assertEquals(1, contactManager.getAllContacts().size());
        }
        @ParameterizedTest
        @CsvFileSource(resources = "/data.csv")
        @DisplayName("Repeat contact creation test 5 times CSV file")
        public void shouldCreateParametrizedContactCsvFile(String phoneNumber) {
            contactManager.addContact("Mark", "Doe", phoneNumber);
            Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
            Assertions.assertEquals(1, contactManager.getAllContacts().size());
        }
    }
}