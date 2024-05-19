package jss.notfine.mixins.early.minecraft.toggle;

import jss.notfine.core.SettingsManager;
import net.minecraft.Render;
import net.minecraft.GameSettings;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = Render.class)
public abstract class MixinRender {

    @Redirect(
        method = "doRenderShadowAndFire",
        at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/GameSettings;fancyGraphics:Z",
            opcode = Opcodes.GETFIELD,
            ordinal = 0
        )
    )
    private boolean notFine$toggleEntityShadows(GameSettings settings) {
        return SettingsManager.shadows;
    }

}
