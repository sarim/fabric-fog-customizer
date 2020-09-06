package me.sarim.mixin;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.fluid.FluidState;
import net.minecraft.tag.FluidTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import me.sarim.config.Config;

@Mixin(BackgroundRenderer.class)
// This mod shouldn't even be installed on a server but w/e
@Environment(EnvType.CLIENT)
public abstract class RendererMixin {
	@Inject(method = "applyFog", at=@At(value = "INVOKE", target = "com/mojang/blaze3d/systems/RenderSystem.setupNvFogDistance()V"), locals = LocalCapture.CAPTURE_FAILSOFT)
	private static void applyCustomFog(Camera camera, BackgroundRenderer.FogType fogType, float viewDistance, boolean thickFog, CallbackInfo ci, FluidState fluidState, Entity entity, float u) {
		if (! (fluidState.isIn(FluidTags.LAVA)) || (entity instanceof LivingEntity && ((LivingEntity)entity).hasStatusEffect(StatusEffects.BLINDNESS))) {
			RenderSystem.fogStart(viewDistance * Config.Generic.linearFogMultiplier.getFloatValue());
			if (Config.Generic.fogType.getOptionListValue().equals(Config.FogType.LINEAR))
				RenderSystem.fogMode(GlStateManager.FogMode.LINEAR);
			else if (Config.Generic.fogType.getOptionListValue().equals(Config.FogType.EXPONENTIAL)) {
				RenderSystem.fogDensity(Config.Generic.expFogMultiplier.getFloatValue() / viewDistance);
				RenderSystem.fogMode(GlStateManager.FogMode.EXP);
			} else if (Config.Generic.fogType.getOptionListValue().equals(Config.FogType.EXPONENTIAL_TWO)) {
				RenderSystem.fogDensity(Config.Generic.exp2FogMultiplier.getFloatValue() / viewDistance);
				RenderSystem.fogMode(GlStateManager.FogMode.EXP2);
			}
		}
	}
}
