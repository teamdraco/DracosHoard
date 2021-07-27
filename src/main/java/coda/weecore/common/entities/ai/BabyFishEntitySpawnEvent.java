package coda.weecore.common.entities.ai;

import coda.weecore.common.entities.AbstractBreedableFishEntity;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.eventbus.api.Cancelable;

import javax.annotation.Nullable;

@Cancelable
public class BabyFishEntitySpawnEvent extends net.minecraftforge.eventbus.api.Event {
    private final MobEntity parentA;
    private final MobEntity parentB;
    private final PlayerEntity causedByPlayer;
    private AbstractBreedableFishEntity child;

    public BabyFishEntitySpawnEvent(MobEntity parentA, MobEntity parentB, @Nullable AbstractBreedableFishEntity proposedChild) {
        //causedByPlayer calculated here to simplify the patch.
        PlayerEntity causedByPlayer = null;
        if (parentA instanceof AnimalEntity) {
            causedByPlayer = ((AnimalEntity)parentA).getLoveCause();
        }

        if (causedByPlayer == null && parentB instanceof AnimalEntity) {
            causedByPlayer = ((AnimalEntity)parentB).getLoveCause();
        }

        this.parentA = parentA;
        this.parentB = parentB;
        this.causedByPlayer = causedByPlayer;
        this.child = proposedChild;
    }

    public MobEntity getParentA() {
        return parentA;
    }

    public MobEntity getParentB() {
        return parentB;
    }

    @Nullable
    public PlayerEntity getCausedByPlayer() {
        return causedByPlayer;
    }

    @Nullable
    public AbstractBreedableFishEntity getChild() {
        return child;
    }

    public void setChild(AbstractBreedableFishEntity proposedChild) {
        child = proposedChild;
    }
}
