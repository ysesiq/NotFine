package jss.notfine;

import jss.notfine.proxy.CommonProxy;
import net.fabricmc.api.ModInitializer;
import net.xiaoyu233.fml.classloading.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod
public class NotFine implements ModInitializer {
    public static final String MODID = "notfine";
    public static final String NAME = "NotFine";
    public static final String VERSION = "GRADLETOKEN_VERSION";
    public static final Logger logger = LogManager.getLogger(NAME);

    public static CommonProxy proxy;

    @Override
    public void onInitialize() {
        proxy.preInit();
        proxy.init();
        proxy.postInit();
    }
}
