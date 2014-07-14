package com.github.robotbrain.helpmod.api;

public abstract class HelpEntry {
    /**
     * This entry's parent, null if unparented
     */
    public HelpEntry parent;

    public abstract String getTitle();

    public abstract HelpPage[] getPages();

    public boolean isRoot() {
        return false;
    }
}
