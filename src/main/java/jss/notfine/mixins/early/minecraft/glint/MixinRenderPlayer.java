package jss.notfine.mixins.early.minecraft.glint;

import jss.notfine.core.Settings;
import net.minecraft.RenderPlayer;
import net.minecraft.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = RenderPlayer.class)
public abstract class MixinRenderPlayer {

//    @Redirect(
//        method = "shouldRenderPass(Lnet/minecraft/entity/AbstractClientPlayer;IF)I",
//        at = @At(
//            value = "INVOKE",
//            target = "Lnet/minecraft/ItemStack;isItemEnchanted()Z"
//        )
//    )
//    private boolean notFine$toggleGlint(ItemStack stack) {
//        return stack.isItemEnchanted() && (boolean)Settings.MODE_GLINT_WORLD.option.getStore();
//    }

}
