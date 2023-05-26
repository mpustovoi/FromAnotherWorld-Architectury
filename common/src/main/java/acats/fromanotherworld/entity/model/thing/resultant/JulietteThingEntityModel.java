package acats.fromanotherworld.entity.model.thing.resultant;

import acats.fromanotherworld.FromAnotherWorld;
import acats.fromanotherworld.entity.thing.resultant.JulietteThingEntity;
import mod.azure.azurelib.model.GeoModel;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;

import static acats.fromanotherworld.constants.Variants.VILLAGER;

public class JulietteThingEntityModel extends GeoModel<JulietteThingEntity> {
    @Override
    public Identifier getModelResource(JulietteThingEntity object) {
        return new Identifier(FromAnotherWorld.MOD_ID, "geo/entity/thing/resultant/juliette_thing.geo.json");
    }

    @Override
    public Identifier getTextureResource(JulietteThingEntity object) {
        String variant = "juliette_thing";
        if (object.getVictimType() == VILLAGER)
            variant = "juliette_thing_villagertrousers";
        return new Identifier(FromAnotherWorld.MOD_ID, "textures/entity/thing/resultant/juliette_thing/" + variant + ".png");
    }

    @Override
    public Identifier getAnimationResource(JulietteThingEntity animatable) {
        return new Identifier(FromAnotherWorld.MOD_ID, "animations/entity/thing/resultant/juliette_thing.animation.json");
    }

    @Override
    public RenderLayer getRenderType(JulietteThingEntity animatable, Identifier texture) {
        return RenderLayer.getEntityCutoutNoCull(this.getTextureResource(animatable));
    }
}
