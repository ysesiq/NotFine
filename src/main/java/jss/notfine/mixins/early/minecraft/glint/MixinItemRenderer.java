package jss.notfine.mixins.early.minecraft.glint;

import jss.notfine.core.Settings;
import net.minecraft.ItemRenderer;
import net.minecraft.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = ItemRenderer.class)
public abstract class MixinItemRenderer {

//    @Redirect(
//        method = "renderItem(Lnet/minecraft/EntityLivingBase;Lnet/minecraft/ItemStack;ILnet/minecraftforge/client/IItemRenderer$ItemRenderType;)V",
//        at = @At(
//            value = "INVOKE",
//            target = "Lnet/minecraft/ItemStack;hasEffect(I)Z"
//        ),
//        remap = false
//    )
//    private boolean notFine$toggleGlint(ItemStack stack, int pass) {
//        return (boolean)Settings.MODE_GLINT_WORLD.option.getStore() && stack.hasEffect(pass);
//    }

}
