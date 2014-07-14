/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * File Created @ [Jan 14, 2014, 9:54:21 PM (GMT)]
 */
package com.github.robotbrain.helpmod.gui.button;

import com.github.robotbrain.helpmod.api.RenderUtils;
import com.github.robotbrain.helpmod.gui.GuiManual;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

import java.util.Arrays;
import java.util.List;

public class GuiButtonBack extends GuiButton {

    public GuiButtonBack(int par1, int par2, int par3) {
        super(par1, par2, par3, 18, 9, "");
    }

    @Override
    public void drawButton(Minecraft par1Minecraft, int par2, int par3) {
        if (!enabled) {
            return;
        }

        field_146123_n = par2 >= xPosition && par3 >= yPosition && par2 < xPosition + width && par3 < yPosition + height;
        int k = getHoverState(field_146123_n);

        par1Minecraft.renderEngine.bindTexture(GuiManual.texture);
        GL11.glColor4f(1F, 1F, 1F, 1F);
        drawTexturedModalRect(xPosition, yPosition, 36, k == 2 ? 180 : 189, 18, 9);

        List<String> tooltip = getTooltip();
        int tooltipY = (tooltip.size() - 1) * 10;
        if (k == 2)
            RenderUtils.renderTooltip(par2, par3 + tooltipY, tooltip);
    }

    public List<String> getTooltip() {
        return Arrays.asList(StatCollector.translateToLocal("gnuman.back"));
    }

}

