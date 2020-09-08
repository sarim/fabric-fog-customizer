package me.sarim.mixin;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import me.sarim.config.Config;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.fluid.FluidState;
import net.minecraft.tag.FluidTags;

@Mixin(BackgroundRenderer.class)
public abstract class RendererMixin {
	@Inject(method = "applyFog", at=@At(value = "INVOKE", target = "com/mojang/blaze3d/systems/RenderSystem.setupNvFogDistance()V"), locals = LocalCapture.CAPTURE_FAILSOFT)
	private static void applyCustomFog(Camera camera, BackgroundRenderer.FogType fogType, float viewDistance, boolean thickFog, CallbackInfo ci, FluidState fluidState, Entity entity, float u) {
		if ((fluidState.isIn(FluidTags.LAVA)) || (entity instanceof LivingEntity && ((LivingEntity)entity).hasStatusEffect(StatusEffects.BLINDNESS))) return;

		if (thickFog) {
			switch(Config.Thick.fogType.getOptionListValue()) {
				case Config.FogType.LINEAR:
					RenderSystem.fogStart(viewDistance * Config.Thick.fogStartMultiplier.getFloatValue());
					RenderSystem.fogEnd(viewDistance * Config.Thick.fogEndMultiplier.getFloatValue());
					RenderSystem.fogMode(GlStateManager.FogMode.LINEAR);
					break;
				case Config.FogType.EXPONENTIAL:
					RenderSystem.fogDensity(Config.Thick.expFogMultiplier.getFloatValue() / viewDistance);
					RenderSystem.fogMode(GlStateManager.FogMode.EXP);
					break;
				case Config.FogType.EXPONENTIAL_TWO:
					RenderSystem.fogDensity(Config.Thick.exp2FogMultiplier.getFloatValue() / viewDistance);
					RenderSystem.fogMode(GlStateManager.FogMode.EXP2);
					break;
			}
		} else {
			switch(Config.Generic.fogType.getOptionListValue()) {
				case Config.FogType.LINEAR:
					RenderSystem.fogStart(viewDistance * Config.Generic.fogStartMultiplier.getFloatValue());
					RenderSystem.fogEnd(viewDistance * Config.Generic.fogEndMultiplier.getFloatValue());
					RenderSystem.fogMode(GlStateManager.FogMode.LINEAR);
					break;
				case Config.FogType.EXPONENTIAL:
					RenderSystem.fogDensity(Config.Generic.expFogMultiplier.getFloatValue() / viewDistance);
					RenderSystem.fogMode(GlStateManager.FogMode.EXP);
					break;
				case Config.FogType.EXPONENTIAL_TWO:
					RenderSystem.fogDensity(Config.Generic.exp2FogMultiplier.getFloatValue() / viewDistance);
					RenderSystem.fogMode(GlStateManager.FogMode.EXP2);
					break;
			}
		}
		
	}
}
