package jss.notfine.mixins.early.mcpatcherforge.mob;

import net.minecraft.ModelBase;
import net.minecraft.RenderEnderman;
import net.minecraft.RenderLiving;
import net.minecraft.EntityEnderman;
import net.minecraft.ResourceLocation;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.prupe.mcpatcher.mob.MobRandomizer;

@Mixin(RenderEnderman.class)
public abstract class MixinRenderEnderman extends RenderLiving {

    public MixinRenderEnderman(ModelBase p_i1262_1_, float p_i1262_2_) {
        super(p_i1262_1_, p_i1262_2_);
    }

    @Redirect(
        method = "shouldRenderPass(Lnet/minecraft/entity/monster/EntityEnderman;IF)I",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/entity/RenderEnderman;bindTexture(Lnet/minecraft/util/ResourceLocation;)V"))
    private void modifyShouldRenderPass(RenderEnderman instance, ResourceLocation resourceLocation,
        EntityEnderman entity) {
        MobRandomizer.randomTexture(entity, resourceLocation);
    }
}
