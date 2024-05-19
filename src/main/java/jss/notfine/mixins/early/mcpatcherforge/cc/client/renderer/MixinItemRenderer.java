package jss.notfine.mixins.early.mcpatcherforge.cc.client.renderer;

import net.minecraft.Block;
import net.minecraft.ItemRenderer;
import net.minecraft.EntityLivingBase;
import net.minecraft.ItemStack;
import net.minecraftforge.client.IItemRenderer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.prupe.mcpatcher.cc.ColorizeBlock;

@Mixin(ItemRenderer.class)
public abstract class MixinItemRenderer {

    @Inject(
        method = "renderItem(Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/item/ItemStack;ILnet/minecraftforge/client/IItemRenderer$ItemRenderType;)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/ItemRenderer;renderItemIn2D(Lnet/minecraft/client/renderer/Tessellator;FFFFIIF)V",
            ordinal = 0))
    private void modifyRenderItem2(EntityLivingBase entity, ItemStack itemStack, int renderPass,
        IItemRenderer.ItemRenderType type, CallbackInfo ci) {
        ColorizeBlock.colorizeWaterBlockGL(Block.getBlockFromItem(itemStack.getItem()));
    }
}
