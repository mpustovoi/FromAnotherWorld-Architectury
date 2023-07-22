package acats.fromanotherworld.entity.render.thing;

import acats.fromanotherworld.entity.thing.Thing;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import acats.fromanotherworld.entity.render.feature.ThingDamagedFeatureRenderer;
import acats.fromanotherworld.entity.render.feature.ThingSnowFeatureRenderer;
import mod.azure.azurelib.cache.object.BakedGeoModel;
import mod.azure.azurelib.model.GeoModel;
import mod.azure.azurelib.renderer.GeoEntityRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.util.Mth;

public class ThingRenderer<T extends Thing> extends GeoEntityRenderer<T> {
    public ThingRenderer(EntityRendererProvider.Context renderManager, GeoModel<T> model) {
        super(renderManager, model);
        this.addRenderLayer(new ThingDamagedFeatureRenderer<>(this));
        this.addRenderLayer(new ThingSnowFeatureRenderer<>(this));
    }

    private float lerpedClimbProgress(T entity, float partialTick){
        return Mth.lerp(partialTick, entity.climbRotateProgress, entity.nextClimbRotateProgress);
    }

    @Override
    public void preRender(PoseStack poseStack, T animatable, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        if (!isReRender && animatable.getBurrowDepth() != 0.0F) {
            float f = 0.0F;
            if (animatable.isThingBurrowing()) {
                f = (float) animatable.getBurrowProgress() / Thing.BURROW_TIME;
            }
            else if (animatable.isThingUnderground()) {
                f = 1.0F;
            }
            else if (animatable.isThingEmerging()) {
                f = 1.0F - (float) (animatable.getBurrowProgress() - Thing.BURROW_TIME - Thing.UNDERGROUND_TIME) / Thing.EMERGE_TIME;
            }
            poseStack.translate(0.0F, animatable.getBurrowDepth() * -f, 0.0F);
        }

        super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    protected void applyRotations(T animatable, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTick) {
        super.applyRotations(animatable, poseStack, ageInTicks, rotationYaw, partialTick);
        float prog = this.lerpedClimbProgress(animatable, partialTick);
        if (animatable.rotateWhenClimbing() && prog > 0) {
            poseStack.mulPose(Axis.XP.rotationDegrees(90 * prog));
            poseStack.translate(0.0F, animatable.offsetWhenClimbing() * prog, 0.0F);
        }
    }
}
