package jss.notfine.mixins.early.mcpatcherforge.cit.item;

import net.minecraft.Item;
import net.minecraft.ItemStack;
import net.minecraft.Icon;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.prupe.mcpatcher.cit.CITUtils;

@Mixin(Item.class)
public abstract class MixinItem {

    @Shadow
    public abstract Icon getIconFromDamage(int meta);

    @Redirect(
        method = "getIconIndex(Lnet/minecraft/ItemStack;)Lnet/minecraft/Icon;",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/Item;getIconFromDamage(I)Lnet/minecraft/Icon;"))
    private Icon modifyGetIconIndex(Item item, int meta, ItemStack itemStack) {
        return CITUtils.getIcon(this.getIconFromDamage(meta), itemStack, 0);
    }
}
