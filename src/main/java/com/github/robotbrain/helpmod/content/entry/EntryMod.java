package com.github.robotbrain.helpmod.content.entry;

import com.github.robotbrain.helpmod.api.HelpEntry;
import com.github.robotbrain.helpmod.api.HelpPage;
import com.github.robotbrain.helpmod.api.entry.EntryTOC;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import cpw.mods.fml.common.Loader;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EntryMod extends EntryTOC {
    public final Map<Item, HelpEntry> items = Maps.newHashMap();
    public final Map<Block, HelpEntry> blocks = Maps.newHashMap();
    public final Map<Class<? super Entity>, EntryEntity> entities = Maps.newHashMap();
    private final String modid;
    private final List<HelpEntry> entries = Lists.newArrayList();

    public EntryMod(String modid) {
        this.modid = modid;
    }

    public HelpEntry addItem(Item item, HelpPage[] pages) {
        synchronized (items) {
            if (items.containsKey(item)) {
                HelpEntry helpEntry = items.get(item);
                ((EntryItem) helpEntry).addPages(pages);
                return helpEntry;
            }
            EntryItem entryItem = new EntryItem(item, pages);
            items.put(item, entryItem);
            entryItem.parent = this;
            return entryItem;
        }
    }

    public HelpEntry addBlock(Block block, HelpPage[] pages) {
        synchronized (blocks) {
            if (blocks.containsKey(block)) {
                HelpEntry helpEntry = blocks.get(block);
                ((EntryBlock) helpEntry).addPages(pages);
                return helpEntry;
            }
            EntryBlock entryBlock = new EntryBlock(block, pages);
            blocks.put(block, entryBlock);
            entryBlock.parent = this;
            return entryBlock;
        }
    }

    public HelpEntry addEntity(Class<? super Entity> entity, HelpPage[] pages) {
        synchronized (entities) {
            if (entities.containsKey(entity)) {
                HelpEntry helpEntry = entities.get(entity);
                ((EntryEntity) helpEntry).addPages(pages);
                return helpEntry;
            }
            EntryEntity entryEntity = new EntryEntity(entity, pages);
            entities.put(entity, entryEntity);
            entryEntity.parent = this;
            return entryEntity;
        }
    }

    @Override
    public String getTitle() {
        return Loader.instance().getIndexedModList().get(modid).getName();
    }

    @Override
    public HelpEntry[] contents() {
        ArrayList<HelpEntry> entries = Lists.newArrayList(Iterables.concat(items.values(), blocks.values(), entities.values(), this.entries));
        return entries.toArray(new HelpEntry[entries.size()]);
    }

    public HelpEntry addCustomEntry(HelpEntry entry) {
        synchronized (entries) {
            entries.add(entry);
        }
        entry.parent = this;
        return entry;
    }
}
