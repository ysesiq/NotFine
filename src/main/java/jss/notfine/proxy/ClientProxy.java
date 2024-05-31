package jss.notfine.proxy;

import jss.notfine.config.NotFineConfig;
import jss.notfine.core.LoadMenuButtons;
import jss.notfine.core.Settings;
import jss.notfine.core.SettingsManager;
import net.minecraft.Minecraft;
import net.minecraft.GameSettings;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit() {
        if(!NotFineConfig.allowAdvancedOpenGL) {
            Minecraft.getMinecraft().gameSettings.advancedOpengl = false;
        }
        if(!NotFineConfig.allowToggle3DAnaglyph) {
            Minecraft.getMinecraft().gameSettings.anaglyph = false;
        }
//        if(!NotFineConfig.allowToggleFBO) {
//            Minecraft.getMinecraft().gameSettings.fboEnable = true;
//        }

        for(Settings setting : Settings.values()) {
            setting.ready();
        }

//        MinecraftForge.EVENT_BUS.register(this);
//        MinecraftForge.EVENT_BUS.register(LoadMenuButtons.INSTANCE);
    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void postInit() {
        SettingsManager.settingsFile.loadSettings();
    }

//    @SubscribeEvent
//    public void onFOVModifierUpdate(FOVUpdateEvent event) {
//        if (!(boolean)Settings.DYNAMIC_FOV.option.getStore()){
//            event.newfov = 1.0F;
//        }
//    }

}
