package jss.notfine.mixins.early.mcpatcherforge.mob;

import net.minecraft.ItemRenderer;
import net.minecraft.RenderSnowMan;
import net.minecraft.EntityLivingBase;
import net.minecraft.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.WrapWithCondition;
import com.prupe.mcpatcher.mob.MobOverlay;

@Mixin(RenderSnowMan.class)
public abstract class MixinRenderSnowMan {

    @WrapWithCondition(
        method = "renderEquippedItems(Lnet/minecraft/entity/monster/EntitySnowman;F)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/ItemRenderer;renderItem(Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/item/ItemStack;I)V"))
    private boolean modifyRenderEquippedItems(ItemRenderer renderer, EntityLivingBase entity, ItemStack itemStack,
        int i) {
        return !MobOverlay.renderSnowmanOverlay(entity);
    }
}
