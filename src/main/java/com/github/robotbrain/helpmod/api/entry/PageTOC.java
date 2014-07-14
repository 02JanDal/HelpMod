package com.github.robotbrain.helpmod.api.entry;

import com.github.robotbrain.helpmod.api.HelpEntry;
import com.github.robotbrain.helpmod.api.HelpPage;
import com.github.robotbrain.helpmod.gui.GuiManual;
import com.github.robotbrain.helpmod.gui.button.GuiButtonInvisible;
import net.minecraft.client.gui.GuiButton;

public class PageTOC extends HelpPage {
    private final HelpEntry[] pages;

    public PageTOC(HelpEntry[] pages) {

        this.pages = pages;
    }

    @Override
    public void draw(int x, int y, GuiManual guiManual) {

    }

    @Override
    public void initGui(GuiManual guiManual) {
        int x = 18;
        for (int i = 0; i < 12; i++) {
            int y = 16 + i * 12;
            guiManual.addButton(new GuiButtonInvisible(i, guiManual.left + x, guiManual.top + y + 16, 110, 10, i < pages.length ? pages[i].getTitle() : ""));
        }
    }

    @Override
    public void action(GuiManual guiManual, GuiButton button) {
        int id = button.id;
        if (id < pages.length) {
            guiManual.openEntry(pages[id]);
        }
    }
}
