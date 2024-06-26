package jss.notfine.mixins.early.minecraft.toggle;

import jss.notfine.core.SettingsManager;
import net.minecraft.EntityRenderer;
import net.minecraft.GameSettings;
//import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityRenderer.class)
public abstract class MixinEntityRenderer$RenderRainSnow {

//    @Redirect(
//        method = "renderRainSnow(F)V",
//        at = @At(
//            value = "FIELD",
//            target = "Lnet/minecraft/GameSettings;fancyGraphics:Z",
//            opcode = Opcodes.GETFIELD
//        )
//    )
//    private boolean notFine$bypassWeatherMode(GameSettings settings) {
//        return false;
//    }

    @ModifyVariable(
        method = "renderRainSnow(F)V",
        at = @At("STORE"),
        ordinal = 0
    )
    private byte notFine$weatherDistance(byte distance) {
        return SettingsManager.downfallDistance;
    }
}
