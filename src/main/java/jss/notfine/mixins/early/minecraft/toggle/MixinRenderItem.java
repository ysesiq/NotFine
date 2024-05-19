package jss.notfine.mixins.early.minecraft.toggle;

import jss.notfine.core.SettingsManager;
import net.minecraft.RenderItem;
import net.minecraft.GameSettings;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = RenderItem.class, priority = 1100)
abstract public class MixinRenderItem {

    @Redirect(
        method = "renderDroppedItem(Lnet/minecraft/EntityItem;Lnet/minecraft/IIcon;IFFFFI)V",
        at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/GameSettings;fancyGraphics:Z",
            opcode = Opcodes.GETFIELD
        ),
        allow = 1
    )
    private boolean notFine$toggleDroppedItemDetail(GameSettings settings) {
        return SettingsManager.droppedItemDetail;
    }

}
