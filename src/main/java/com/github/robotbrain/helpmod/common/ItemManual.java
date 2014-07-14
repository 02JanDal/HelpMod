package com.github.robotbrain.helpmod.common;

import com.github.robotbrain.helpmod.HelpMod;
import com.github.robotbrain.helpmod.Reference;
import com.github.robotbrain.helpmod.api.HelpAPI;
import com.github.robotbrain.helpmod.api.HelpEntry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class ItemManual extends Item {

    public ItemManual() {
        setMaxStackSize(1);
        setUnlocalizedName(Reference.ITEMS.MANUAL);
        setCreativeTab(CreativeTabs.tabMisc);
        iconString = Reference.MODID + ":" + Reference.ITEMS.MANUAL;
    }

    @Override
    @SuppressWarnings("unchecked")
    @SideOnly(Side.CLIENT)
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
        HelpMod.PROXY.setEntryToOpen(null);
        if (player.isSneaking()) {
            HelpEntry entry = null;

            MovingObjectPosition objectMouseOver = Minecraft.getMinecraft().objectMouseOver;
            switch (objectMouseOver.typeOfHit) {

                case MISS:
                    break;
                case BLOCK:
                    entry = HelpAPI.findEntry(world.getBlock(objectMouseOver.blockX, objectMouseOver.blockY, objectMouseOver.blockZ));
                    break;
                case ENTITY:
                    Entity entity = objectMouseOver.entityHit;
                    if (entity instanceof EntityItem) {
                        entry = HelpAPI.findEntry(((EntityItem) entity).getEntityItem().getItem());
                    } else if (entity instanceof EntityItemFrame) {
                        EntityItemFrame itemFrame = (EntityItemFrame) entity;
                        entry = HelpAPI.findEntry(itemFrame.getDisplayedItem().getItem());
                    } else {
                        entry = HelpAPI.findEntry((Class<? super Entity>) entity.getClass());
                    }
                    break;
            }

            if (entry != null) {
                HelpMod.PROXY.setEntryToOpen(entry);
            }
        }

        player.openGui(HelpMod.INSTANCE, Reference.GUIS.MANUAL, world, 0, 0, 0);
        return itemStack;
    }

    @Override
    public boolean hasEffect(ItemStack par1ItemStack, int pass) {
        return pass == 0;
    }
}
