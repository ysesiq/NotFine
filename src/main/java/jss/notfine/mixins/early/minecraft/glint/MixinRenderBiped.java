package jss.notfine.mixins.early.minecraft.glint;

import jss.notfine.core.Settings;
import net.minecraft.RenderBiped;
import net.minecraft.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = RenderBiped.class)
public abstract class MixinRenderBiped {

//    @Redirect(
//        method = "shouldRenderPass(Lnet/minecraft/EntityLiving;IF)I",
//        at = @At(
//            value = "INVOKE",
//            target = "Lnet/minecraft/ItemStack;isItemEnchanted()Z"
//        )
//    )
//    private boolean notFine$toggleGlint(ItemStack stack) {
//        return (boolean)Settings.MODE_GLINT_WORLD.option.getStore() && stack.isItemEnchanted();
//    }

}
