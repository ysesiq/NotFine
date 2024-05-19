package jss.notfine.mixins.early.minecraft.toggle;

import jss.notfine.core.SettingsManager;
import net.minecraft.GuiIngame;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = GuiIngame.class)
public abstract class MixinGuiIngame {

    @Redirect(
        method = "renderGameOverlay(FZII)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/Minecraft;isFancyGraphicsEnabled()Z"
        )
    )
    private boolean notFine$toggleVignette(float whyAndHowIsThisAFloat) {
        return SettingsManager.vignette;
    }

}
