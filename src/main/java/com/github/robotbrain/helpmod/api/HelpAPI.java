package com.github.robotbrain.helpmod.api;

import com.github.robotbrain.helpmod.HelpMod;
import com.github.robotbrain.helpmod.content.entry.EntryMod;
import com.github.robotbrain.helpmod.content.entry.EntryModVanilla;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class HelpAPI {
    static final Map<String, HelpEntry> modEntries = Maps.newHashMap();
    private static EntryMod entryVanilla = new EntryModVanilla();

    @SuppressWarnings("unchecked")
    public static HelpEntry registerHelpPageFor(Object thing, String modid, HelpPage... pages) {
        EntryMod entryMod = getModEntryInternal(modid);
        if (thing instanceof Item) {
            return entryMod.addItem((Item) thing, pages);
        }
        if (thing instanceof Block) {
            return entryMod.addBlock((Block) thing, pages);
        }
        if (thing instanceof Entity) {
            Entity e = (Entity) thing;
            return entryMod.addEntity((Class<? super Entity>) e.getClass(), pages);
        }
        if (thing instanceof Class<?>) {
            if (Entity.class.isAssignableFrom((Class<?>) thing)) {
                return entryMod.addEntity((Class<? super Entity>) thing, pages);
            }
        }
        throw Throwables.propagate(new Exception("Invalid thing to be registered!"));
    }

    private static EntryMod getModEntryInternal(String modid) {
        if (modid.equals("minecraft")) {
            return entryVanilla;
        }
        synchronized (modEntries) {
            if (modEntries.containsKey(modid)) {
                return (EntryMod) modEntries.get(modid);
            }
            EntryMod modEntry = new EntryMod(modid);
            modEntry.parent = HelpMod.PROXY.getRoot();
            modEntries.put(modid, modEntry);
            return modEntry;
        }
    }

    public static HelpEntry addCustomEntry(String modid, final String title, final HelpPage... pages) {
        HelpEntry entry = new HelpEntry() {
            @Override
            public String getTitle() {
                return title;
            }

            @Override
            public HelpPage[] getPages() {
                return pages;
            }
        };
        return getModEntryInternal(modid).addCustomEntry(entry);
    }

    public static List<HelpEntry> getModEntries() {
        Collection<HelpEntry> values1 = modEntries.values();
        List<HelpEntry> values = Lists.newArrayList(values1);
        return Collections.unmodifiableList(values);
    }

    public static HelpEntry findEntry(Block block) {
        for (HelpEntry mod : getModEntries()) {
            if (mod instanceof EntryMod) {
                EntryMod entryMod = (EntryMod) mod;
                if (entryMod.blocks.containsKey(block)) {
                    return entryMod.blocks.get(block);
                }
            }
        }
        return null;
    }

    public static HelpEntry findEntry(Item item) {
        for (HelpEntry mod : getModEntries()) {
            if (mod instanceof EntryMod) {
                EntryMod entryMod = (EntryMod) mod;
                if (entryMod.items.containsKey(item)) {
                    return entryMod.items.get(item);
                }
            }
        }
        return null;
    }

    public static HelpEntry findEntry(Class<? super Entity> entity) {
        for (HelpEntry mod : getModEntries()) {
            if (mod instanceof EntryMod) {
                EntryMod entryMod = (EntryMod) mod;
                if (entryMod.entities.containsKey(entity)) {
                    return entryMod.entities.get(entity);
                }
            }
        }
        return null;
    }
}
