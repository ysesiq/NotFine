package jss.notfine.mixins.early.mcpatcherforge.base;

import net.minecraft.Block;
import net.minecraft.BlockGrass;
import net.minecraft.Icon;
import net.minecraft.IBlockAccess;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.prupe.mcpatcher.mal.block.RenderBlocksUtils;

@Mixin(BlockGrass.class)
public class MixinBlockGrass {

    @Shadow
    private Icon field_149991_b;

    @Inject(
        method = "getIcon(Lnet/minecraft/IBlockAccess;IIII)Lnet/minecraft/Icon;",
        at = @At("HEAD"),
        cancellable = true)
    private void modifyGetIcon(IBlockAccess worldIn, int x, int y, int z, int side, CallbackInfoReturnable<Icon> cir) {
        final Icon grassTexture = RenderBlocksUtils
            .getGrassTexture((Block) (Object) this, worldIn, x, y, z, side, this.field_149991_b);
        if (grassTexture != null) {
            cir.setReturnValue(grassTexture);
        }
    }
}
