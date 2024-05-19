package jss.notfine.mixins.early.mcpatcherforge.mob;

import net.minecraft.ModelBase;
import net.minecraft.RenderLiving;
import net.minecraft.RenderSpider;
import net.minecraft.EntitySpider;
import net.minecraft.ResourceLocation;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.prupe.mcpatcher.mob.MobRandomizer;

@Mixin(RenderSpider.class)
public abstract class MixinRenderSpider extends RenderLiving {

    public MixinRenderSpider(ModelBase modelBase, float shadowSize) {
        super(modelBase, shadowSize);
    }

    @Redirect(
        method = "shouldRenderPass(Lnet/minecraft/entity/monster/EntitySpider;IF)I",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/entity/RenderSpider;bindTexture(Lnet/minecraft/util/ResourceLocation;)V"))
    private void modifyShouldRenderPass(RenderSpider instance, ResourceLocation resourceLocation, EntitySpider entity) {
        this.bindTexture(MobRandomizer.randomTexture(entity, resourceLocation));
    }
}
