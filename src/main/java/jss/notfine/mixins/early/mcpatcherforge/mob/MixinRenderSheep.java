package jss.notfine.mixins.early.mcpatcherforge.mob;

import net.minecraft.ModelBase;
import net.minecraft.RenderLiving;
import net.minecraft.RenderSheep;
import net.minecraft.EntitySheep;
import net.minecraft.ResourceLocation;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.prupe.mcpatcher.mob.MobRandomizer;

@Mixin(RenderSheep.class)
public abstract class MixinRenderSheep extends RenderLiving {

    public MixinRenderSheep(ModelBase modelBase, float shadowSize) {
        super(modelBase, shadowSize);
    }

    @Redirect(
        method = "shouldRenderPass(Lnet/minecraft/entity/passive/EntitySheep;IF)I",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/entity/RenderSheep;bindTexture(Lnet/minecraft/util/ResourceLocation;)V"))
    private void modifyShouldRenderPass(RenderSheep instance, ResourceLocation resourceLocation, EntitySheep entity) {
        this.bindTexture(MobRandomizer.randomTexture(entity, resourceLocation));
    }
}
