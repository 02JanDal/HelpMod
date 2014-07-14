package com.github.robotbrain.helpmod.api;

import com.github.robotbrain.helpmod.gui.GuiManual;
import net.minecraft.client.gui.GuiButton;

public abstract class HelpPage {
    public void draw(int x, int y, GuiManual guiManual) {
    }

    public abstract void initGui(GuiManual guiManual);

    public abstract void action(GuiManual guiManual, GuiButton button);

    public void updateScreen() {
    }
}
