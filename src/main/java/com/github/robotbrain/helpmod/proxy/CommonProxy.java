package com.github.robotbrain.helpmod.proxy;

import com.github.robotbrain.helpmod.Reference;
import com.github.robotbrain.helpmod.api.HelpAPI;
import com.github.robotbrain.helpmod.api.HelpEntry;
import com.github.robotbrain.helpmod.api.entry.BlankPage;
import com.github.robotbrain.helpmod.api.entry.EntryTOC;
import com.github.robotbrain.helpmod.common.ItemManual;
import com.github.robotbrain.helpmod.gui.GuiManual;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class CommonProxy implements IGuiHandler {
    private HelpEntry entryToOpen;
    private HelpEntry entryRoot;

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case Reference.GUIS.MANUAL:
                ItemStack stack = player.getCurrentEquippedItem();
                if (stack == null || !(stack.getItem() instanceof ItemManual)) {
                    return null;
                }
                return new GuiManual();
        }

        return null;
    }

    public HelpEntry getEntryToOpen() {
        return entryToOpen;
    }

    public void setEntryToOpen(HelpEntry entryToOpen) {
        if (entryToOpen == null) {
            entryToOpen = entryRoot;
        }
        this.entryToOpen = entryToOpen;
    }

    public void postInit() {
        entryRoot = new EntryTOC() {
            @Override
            public HelpEntry[] contents() {
                return HelpAPI.getModEntries().toArray(new HelpEntry[HelpAPI.getModEntries().size()]);
            }

            @Override
            public String getTitle() {
                return StatCollector.translateToLocal("gnuman.toc");
            }

            @Override
            public boolean isRoot() {
                return true;
            }
        };
    }

    public void registerHelpModHelp() {
        HelpAPI.registerHelpPageFor(Reference.ITEMS.MANUAL_ITEM, Reference.MODID, new BlankPage());
    }

    public HelpEntry getRoot() {
        return entryRoot;
    }
}
