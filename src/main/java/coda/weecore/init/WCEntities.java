package coda.weecore.init;

import coda.weecore.WeeCore;
import coda.weecore.common.entities.companion.DragonCompanionEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class WCEntities {
    public static final DeferredRegister<EntityType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.ENTITIES, WeeCore.MOD_ID);

    public static final RegistryObject<EntityType<DragonCompanionEntity>> DRAGON_COMPANION = create("dragon_companion", EntityType.Builder.of(DragonCompanionEntity::new, EntityClassification.MISC).sized(0.5F, 0.5F).noSummon());

    private static <T extends Entity> RegistryObject<EntityType<T>> create(String name, EntityType.Builder<T> builder) {
        return REGISTER.register(name, () -> builder.build(WeeCore.MOD_ID + "." + name));
    }
}
