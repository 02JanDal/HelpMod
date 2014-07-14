package com.github.robotbrain.helpmod;

import com.github.robotbrain.helpmod.common.ItemManual;

public class Reference {
    public static final String MODID = "helpmod";
    public static final String NAME = "Help Mod";
    public static final String VERSION = "@VERSION@";

    public static class GUIS {
        public static final int MANUAL = 0;
    }

    public static class TEXTURES {

        public static final String GUI_MANUAL = PREFIX_GUI + "gnuman.png";
        public static final String PREFIX_GUI = MODID + ":textures/gui/";

    }

    public static class ITEMS {
        public static final String MANUAL = "gnuman";
        public static ItemManual MANUAL_ITEM;
    }
}
