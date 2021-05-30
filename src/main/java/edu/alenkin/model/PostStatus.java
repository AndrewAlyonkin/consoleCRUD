package edu.alenkin.model;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */

/**
 * Enum class that represent publication status of {@link edu.alenkin.model.Post post}
 */
public enum PostStatus {
    ACTIVE("Опубликован"),
    UNDER_REVIEW("Проверяется"),
    DELETED("Удален");

    private String status;

    PostStatus(String status) {
        this.status = status;
    }
}
