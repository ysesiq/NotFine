package jss.notfine.mixins.early.minecraft.optimization;

import net.minecraft.Render;
import net.minecraft.RenderItemFrame;
import net.minecraft.EntityItem;
import net.minecraft.ItemStack;
import net.minecraft.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = RenderItemFrame.class)
public abstract class MixinRenderItemFrame extends Render {

    private EntityItem cachedEntityItem;

    @Redirect(
        method = "func_82402_b(Lnet/minecraft/EntityItemFrame;)V",
        at = @At(
            value = "NEW",
            target = "(Lnet/minecraft/World;DDDLnet/minecraft/ItemStack;)Lnet/minecraft/EntityItem;",
            ordinal = 0
        )
    )
    public EntityItem cacheEntityItem(World world, double x, double y, double z, ItemStack itemstack) {
        if(cachedEntityItem == null) {
            cachedEntityItem = new EntityItem(world, 0.0D, 0.0D, 0.0D, itemstack);
        } else {
            cachedEntityItem.setEntityItemStack(itemstack);
        }
        return cachedEntityItem;
    }

}
