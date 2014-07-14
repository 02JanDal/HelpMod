package com.github.robotbrain.helpmod.content.entry;

import com.github.robotbrain.helpmod.api.HelpEntry;
import com.github.robotbrain.helpmod.api.HelpPage;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.util.StatCollector;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EntryEntity extends HelpEntry {
    private final String entity;
    private final List<HelpPage> pages;

    public EntryEntity(Class<? super Entity> entity, HelpPage[] pages) {

        this.entity = (String) EntityList.classToStringMapping.get(entity);
        this.pages = Arrays.asList(pages);
    }

    public void addPages(HelpPage[] pages) {
        Collections.addAll(this.pages, pages);
    }

    @Override
    public String getTitle() {
        return StatCollector.translateToLocal("entity." + entity + ".name");
    }

    @Override
    public HelpPage[] getPages() {
        return new HelpPage[0];
    }
}
