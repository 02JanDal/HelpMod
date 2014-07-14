package com.github.robotbrain.helpmod.gui;

import com.github.robotbrain.helpmod.HelpMod;
import com.github.robotbrain.helpmod.Reference;
import com.github.robotbrain.helpmod.api.HelpEntry;
import com.github.robotbrain.helpmod.api.HelpPage;
import com.github.robotbrain.helpmod.gui.button.GuiButtonBackWithShift;
import com.github.robotbrain.helpmod.gui.button.GuiButtonPage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

public class GuiManual extends GuiScreen {

    public static ResourceLocation texture = new ResourceLocation(Reference.TEXTURES.GUI_MANUAL);
    public int left;
    public int top;
    int guiWidth = 146;
    int guiHeight = 180;
    int pageIndex;
    HelpPage page;
    private String title;
    private GuiButton leftButton, backButton, rightButton;

    @Override
    @SuppressWarnings("unchecked")
    public void initGui() {
        super.initGui();

        title = Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem().getDisplayName();

        left = width / 2 - guiWidth / 2;
        top = height / 2 - guiHeight / 2;

        buttonList.clear();

        page = entry().getPages()[pageIndex];

        page.initGui(this);

        buttonList.add(backButton = new GuiButtonBackWithShift(0, left + guiWidth / 2 - 8, top + guiHeight + 2));
        buttonList.add(leftButton = new GuiButtonPage(1, left, top + guiHeight - 10, false));
        buttonList.add(rightButton = new GuiButtonPage(2, left + guiWidth - 18, top + guiHeight - 10, true));

        leftButton.enabled = pageIndex != 0;
        rightButton.enabled = pageIndex + 1 < entry().getPages().length;
        backButton.enabled = !entry().isRoot();
    }

    String getTitle() {
        if (entry().isRoot()) {
            return title;
        }
        return String.format("%s " + EnumChatFormatting.ITALIC + "(%s/%s)", title, pageIndex + 1, entry().getPages().length);
    }

    private HelpEntry entry() {
        return HelpMod.PROXY.getEntryToOpen();
    }

    String getSubtitle() {
        return entry().getTitle();
    }

    @Override
    public void drawScreen(int par1, int par2, float par3) {
        GL11.glColor4f(1F, 1F, 1F, 1F);
        mc.renderEngine.bindTexture(texture);
        drawTexturedModalRect(left, top, 0, 0, guiWidth, guiHeight);
        drawTitle(left + guiWidth / 2, top - getTitleHeight(), true);
        String subtitle = getSubtitle();
        if (subtitle != null) {
            GL11.glScalef(0.5F, 0.5F, 1F);
            drawCenteredString(fontRendererObj, subtitle, left * 2 + guiWidth, (top - getTitleHeight() + 11) * 2, 0x00FF00);
            GL11.glScalef(2F, 2F, 1F);
        }

        drawHeader();

        page.draw(par1, par2, this);

        super.drawScreen(par1, par2, par3);
    }

    private void drawTitle(int x, int y, boolean drawLeft) {
        boolean unicode = fontRendererObj.getUnicodeFlag();
        fontRendererObj.setUnicodeFlag(true);
        int l = fontRendererObj.getStringWidth(getTitle().trim());
        int fontOff = 0;

        if (!drawLeft) {
            x += l / 2;
            fontOff = 2;
        }

        Minecraft.getMinecraft().renderEngine.bindTexture(texture);

        GL11.glColor4f(1F, 1F, 1F, 1F);
        drawTexturedModalRect(x + l / 2 + 3, y - 1, 54, 180, 6, 11);
        if (drawLeft)
            drawTexturedModalRect(x - l / 2 - 9, y - 1, 61, 180, 6, 11);
        for (int i = 0; i < l + 6; i++)
            drawTexturedModalRect(x - l / 2 - 3 + i, y - 1, 60, 180, 1, 11);

        fontRendererObj.drawString(getTitle(), x - l / 2 + fontOff, y, 0xffffff, false);
        fontRendererObj.setUnicodeFlag(unicode);
    }

    int getTitleHeight() {
        return getSubtitle() == null ? 12 : 16;
    }

    void drawHeader() {
        boolean unicode = fontRendererObj.getUnicodeFlag();
        fontRendererObj.setUnicodeFlag(true);
        fontRendererObj.drawSplitString(StatCollector.translateToLocal("helpmod.gui.gnuman.header"), left + 18, top + 14, 110, 0);
        fontRendererObj.setUnicodeFlag(unicode);
    }

    @SuppressWarnings("unchecked")
    public void addButton(GuiButton button) {
        buttonList.add(button);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button == backButton) {
            if (GuiScreen.isShiftKeyDown()) {
                openEntry(null);
            } else {
                openEntry(entry().parent);
            }
        } else if (button == leftButton && leftButton.enabled) {
            pageIndex--;
            mc.displayGuiScreen(new GuiManual());
        } else if (button == rightButton && rightButton.enabled) {
            pageIndex++;
            mc.displayGuiScreen(new GuiManual());
        } else {
            page.action(this, button);
        }
    }

    public void openEntry(HelpEntry page) {
        HelpMod.PROXY.setEntryToOpen(page);
        mc.displayGuiScreen(new GuiManual());
    }

    @Override
    protected void keyTyped(char par1, int par2) {
        if (mc.gameSettings.keyBindInventory.getKeyCode() == par2) {
            mc.displayGuiScreen(null);
            mc.setIngameFocus();
        }

        if (par2 == 203 || par2 == 200 || par2 == 201) // Left, Up, Page Up
            prevPage();
        else if (par2 == 205 || par2 == 208 || par2 == 209) // Right, Down Page Down
            nextPage();
        else if (par2 == 14) // Backspace
            back();
        else if (par2 == 199) { // Home
            HelpMod.PROXY.setEntryToOpen(null);
            mc.displayGuiScreen(new GuiManual());
        }

        super.keyTyped(par1, par2);
    }

    @Override
    public void updateScreen() {
        page.updateScreen();
    }

    void back() {
        if (backButton.enabled)
            actionPerformed(backButton);
    }

    void nextPage() {
        if (rightButton.enabled)
            actionPerformed(rightButton);
    }

    void prevPage() {
        if (leftButton.enabled)
            actionPerformed(leftButton);
    }
}
