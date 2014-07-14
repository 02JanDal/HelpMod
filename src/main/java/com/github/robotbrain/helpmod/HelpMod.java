package com.github.robotbrain.helpmod;

import com.github.robotbrain.helpmod.api.IConfigureHelp;
import com.github.robotbrain.helpmod.common.ItemManual;
import com.github.robotbrain.helpmod.proxy.CommonProxy;
import com.google.common.collect.Lists;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

import java.util.Arrays;
import java.util.List;

import static cpw.mods.fml.common.Mod.EventHandler;
import static cpw.mods.fml.common.Mod.Instance;
import static cpw.mods.fml.common.event.FMLInterModComms.IMCEvent;
import static cpw.mods.fml.common.event.FMLInterModComms.IMCMessage;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class HelpMod {
    private final static List<IConfigureHelp> s_configurers = Lists.newArrayList();
    @Instance(Reference.MODID)
    public static HelpMod INSTANCE;
    @SidedProxy(clientSide = "com.github.robotbrain.helpmod.proxy.ClientProxy", serverSide = "com.github.robotbrain.helpmod.proxy.CommonProxy")
    public static CommonProxy PROXY;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        LogHelper.init(event.getModLog());
        Reference.ITEMS.MANUAL_ITEM = new ItemManual();
        GameRegistry.registerItem(Reference.ITEMS.MANUAL_ITEM, Reference.ITEMS.MANUAL);
    }

    @EventHandler
    public void load(FMLInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(INSTANCE, PROXY);
    }

    @EventHandler
    public void commsReceived(IMCEvent event) {
        ClassLoader loader = Loader.instance().getModClassLoader();
        for (IMCMessage message : event.getMessages()) {
            if (message.key.equals("register") && message.isStringMessage()) {
                Class<?> clazz;
                try {
                    clazz = loader.loadClass(message.getStringValue());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    continue;
                }
                if (!Arrays.asList(clazz.getInterfaces()).contains(IConfigureHelp.class)) {
                    LogHelper.e("Invalid Help Configuration Class: %s, ignoring...", clazz.getName());
                    continue;
                }
                try {
                    s_configurers.add((IConfigureHelp) clazz.newInstance());
                } catch (Throwable t) {
                    LogHelper.e("Error initializing Help Configuration Class: %s", t, clazz.getName());
                }
            }
        }
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        for (IConfigureHelp configurer : s_configurers) {
            configurer.configure();
        }
        PROXY.postInit();
        PROXY.registerHelpModHelp();
    }
}
