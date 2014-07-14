package com.github.robotbrain.helpmod.content.entry;

import com.github.robotbrain.helpmod.api.HelpEntry;
import com.github.robotbrain.helpmod.api.HelpPage;
import net.minecraft.item.Item;
import net.minecraft.util.StatCollector;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EntryItem extends HelpEntry {
    private final Item item;
    private final List<HelpPage> pages;

    public EntryItem(Item item, HelpPage[] pages) {

        this.item = item;
        this.pages = Arrays.asList(pages);
    }

    public void addPages(HelpPage[] pages) {
        Collections.addAll(this.pages, pages);
    }

    @Override
    public String getTitle() {
        return StatCollector.translateToLocal(item.getUnlocalizedName() + ".name");
    }

    @Override
    public HelpPage[] getPages() {
        return pages.toArray(new HelpPage[pages.size()]);
    }
}
