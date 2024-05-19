package jss.notfine.mixins.early.mcpatcherforge.mob;

import net.minecraft.ModelBase;
import net.minecraft.RenderLiving;
import net.minecraft.RenderWolf;
import net.minecraft.EntityWolf;
import net.minecraft.ResourceLocation;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.prupe.mcpatcher.mob.MobRandomizer;

@Mixin(RenderWolf.class)
public abstract class MixinRenderWolf extends RenderLiving {

    public MixinRenderWolf(ModelBase modelBase, float shadowSize) {
        super(modelBase, shadowSize);
    }

    @Redirect(
        method = "shouldRenderPass(Lnet/minecraft/entity/passive/EntityWolf;IF)I",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/entity/RenderWolf;bindTexture(Lnet/minecraft/util/ResourceLocation;)V",
            ordinal = 1))
    private void modifyShouldRenderPass1(RenderWolf instance, ResourceLocation resourceLocation, EntityWolf entity) {
        this.bindTexture(MobRandomizer.randomTexture(entity, resourceLocation));
    }
}
