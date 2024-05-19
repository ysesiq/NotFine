package jss.notfine.mixins.early.mcpatcherforge.cc.client.renderer.entity;

import net.minecraft.RenderWolf;
import net.minecraft.EntitySheep;
import net.minecraft.EntityWolf;

import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.prupe.mcpatcher.cc.ColorizeEntity;

@Mixin(RenderWolf.class)
public class MixinRenderWolf {

    @Redirect(
        method = "shouldRenderPass(Lnet/minecraft/entity/passive/EntityWolf;IF)I",
        at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glColor3f(FFF)V", ordinal = 1, remap = false))
    private void modifyShouldRenderPass2(float red, float green, float blue, EntityWolf entity) {
        int collarColor = entity.getCollarColor();
        GL11.glColor3f(
            ColorizeEntity.getWolfCollarColor(EntitySheep.fleeceColorTable[collarColor], collarColor)[0],
            ColorizeEntity.getWolfCollarColor(EntitySheep.fleeceColorTable[collarColor], collarColor)[1],
            ColorizeEntity.getWolfCollarColor(EntitySheep.fleeceColorTable[collarColor], collarColor)[2]);
    }
}
