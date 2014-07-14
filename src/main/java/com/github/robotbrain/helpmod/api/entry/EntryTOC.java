package com.github.robotbrain.helpmod.api.entry;

import com.github.robotbrain.helpmod.api.HelpEntry;
import com.github.robotbrain.helpmod.api.HelpPage;
import com.google.common.base.Function;
import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;

public abstract class EntryTOC extends HelpEntry {

    public abstract HelpEntry[] contents();

    @Override
    public HelpPage[] getPages() {
        HelpEntry[] contents = contents();
        List<List<HelpEntry>> partition = Lists.partition(Arrays.asList(contents), 12);
        List<HelpPage> transform = Lists.transform(partition, new Function<List<HelpEntry>, HelpPage>() {
            @Override
            public HelpPage apply(List<HelpEntry> input) {
                return new PageTOC(input.toArray(new HelpEntry[input.size()]));
            }
        });
        return transform.toArray(new HelpPage[transform.size()]);
    }
}
