package jss.notfine.mixins.early.minecraft.gui;

import jss.notfine.core.Settings;
import net.minecraft.Minecraft;
import net.minecraft.GuiSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiSlot.class)
public abstract class MixinGuiSlot {

//    @Inject(method = "drawContainerBackground", at = @At("HEAD"), cancellable = true, remap = false)
//    private void notFine$toggleContainerBackground(CallbackInfo ci) {
//        if(!(boolean)Settings.MODE_GUI_BACKGROUND.option.getStore() && Minecraft.getMinecraft().theWorld != null) {
//            ci.cancel();
//        }
//    }

}
