package INTERFACES;

import java.util.UUID;

public interface Identifiable {
    default String generateId() {
        return generateUUID();
    }

    private String generateUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
