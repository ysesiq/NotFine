package jss.notfine.mixins.early.minecraft.glint;

import jss.notfine.core.Settings;
import net.minecraft.RenderItem;
import net.minecraft.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = RenderItem.class)
public abstract class MixinRenderItem {

    @Redirect(
        method = "renderDroppedItem(Lnet/minecraft/EntityItem;Lnet/minecraft/IIcon;IFFFFI)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/ItemStack;hasEffect(I)Z"
        ),
        remap = false
    )
    private boolean notFine$toggleGlint(ItemStack stack, int pass) {
        return stack.hasEffect(pass) && (boolean)Settings.MODE_GLINT_WORLD.option.getStore();
    }

}
