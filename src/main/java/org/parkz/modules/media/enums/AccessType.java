package org.parkz.modules.media.enums;

public enum AccessType {
    PUBLIC, PRIVATE;

    public String getNameLowerCase() {
        return this.name().toLowerCase();
    }
}
