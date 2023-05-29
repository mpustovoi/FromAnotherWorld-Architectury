package acats.fromanotherworld.entity.render.thing.resultant;

import acats.fromanotherworld.entity.render.thing.Tentacle;
import acats.fromanotherworld.entity.render.thing.ThingEntityRenderer;
import acats.fromanotherworld.entity.thing.resultant.AbsorberThingEntity;
import mod.azure.azurelib.model.GeoModel;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;

public class AbsorberThingEntityRenderer<T extends AbsorberThingEntity> extends ThingEntityRenderer<T> {
    public AbsorberThingEntityRenderer(EntityRendererFactory.Context renderManager, GeoModel<T> model) {
        super(renderManager, model);
    }

    @Override
    public void render(T entity, float entityYaw, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight) {
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
        for (Tentacle tentacle:
             entity.absorbTentacles) {
            tentacle.render(poseStack, bufferSource, partialTick, packedLight);
        }
    }

    @Override
    public boolean shouldRender(T entity, Frustum frustum, double x, double y, double z) {
        for (Tentacle tentacle:
                entity.absorbTentacles) {
            if (tentacle.shouldRender(frustum))
                return true;
        }
        return super.shouldRender(entity, frustum, x, y, z);
    }
}
