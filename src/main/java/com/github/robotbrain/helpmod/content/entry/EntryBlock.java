package com.github.robotbrain.helpmod.content.entry;

import com.github.robotbrain.helpmod.api.HelpEntry;
import com.github.robotbrain.helpmod.api.HelpPage;
import net.minecraft.block.Block;
import net.minecraft.util.StatCollector;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EntryBlock extends HelpEntry {
    private final Block block;
    private final List<HelpPage> pages;

    public EntryBlock(Block block, HelpPage[] pages) {

        this.block = block;
        this.pages = Arrays.asList(pages);
    }

    public void addPages(HelpPage[] pages) {
        Collections.addAll(this.pages, pages);
    }

    @Override
    public String getTitle() {
        return StatCollector.translateToLocal(block.getUnlocalizedName());
    }

    @Override
    public HelpPage[] getPages() {
        return pages.toArray(new HelpPage[pages.size()]);
    }
}
