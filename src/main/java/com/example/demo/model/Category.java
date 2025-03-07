package com.example.demo.model;

public enum Category {
    GENERAL("general"),
    LIVE_CLASS("live-class"),
    TESTS("tests"),
    MENTORSHIP("mentorship"),
    MIXED("mixed"),
    TEST_SCHEDULE("test schedule"),
    TEST_SYLLABUS("test syllabus"),
    ANALYTICS("analytics"),
    TOPIC_STRENGTHS("topic strengths"),
    ACTIONS("actions"),
    INFORMATION("information"),
    BATCH("batch"),
    COURSE("course"),
    TEST("test"),
    DOUBT_SOLVING("doubt-solving"),
    APP_ISSUES("app-issues"),
    PAYMENT("payment");

    private final String value;

    Category(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}