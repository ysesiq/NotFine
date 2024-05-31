package jss.notfine.mixins.early.minecraft.clouds;

import jss.notfine.core.Settings;
import net.minecraft.WorldType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(value = WorldType.class)
public abstract class MixinWorldType {

    /**
     * @author jss2a98aj
     * @reason Control cloud height.
     */
//    @Overwrite(remap = false)
//    public float getCloudHeight() {
//        return (int)Settings.CLOUD_HEIGHT.option.getStore();
//    }

}
