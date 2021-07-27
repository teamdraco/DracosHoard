package coda.weecore.common.entities.goals;

import coda.weecore.common.entities.AbstractBreedableFishEntity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;

public class FishBreedGoal extends Goal {
    private static final EntityPredicate PARTNER_TARGETING = (new EntityPredicate()).range(8.0D).allowInvulnerable().allowSameTeam().allowUnseeable();
    protected final AbstractBreedableFishEntity fish;
    private final Class<? extends AbstractBreedableFishEntity> partnerClass;
    protected final World level;
    protected AbstractBreedableFishEntity partner;
    private int loveTime;
    private final double speedModifier;

    public FishBreedGoal(AbstractBreedableFishEntity p_i1619_1_, double p_i1619_2_) {
        this(p_i1619_1_, p_i1619_2_, p_i1619_1_.getClass());
    }

    public FishBreedGoal(AbstractBreedableFishEntity p_i47306_1_, double p_i47306_2_, Class<? extends AbstractBreedableFishEntity> p_i47306_4_) {
        this.fish = p_i47306_1_;
        this.level = p_i47306_1_.level;
        this.partnerClass = p_i47306_4_;
        this.speedModifier = p_i47306_2_;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    public boolean canUse() {
        if (!this.fish.isInLove()) {
            return false;
        } else {
            this.partner = this.getFreePartner();
            return this.partner != null;
        }
    }

    public boolean canContinueToUse() {
        return this.partner.isAlive() && this.partner.isInLove() && this.loveTime < 60;
    }

    public void stop() {
        this.partner = null;
        this.loveTime = 0;
    }

    public void tick() {
        this.fish.getLookControl().setLookAt(this.partner, 10.0F, (float)this.fish.getMaxHeadXRot());
        this.fish.getNavigation().moveTo(this.partner, this.speedModifier);
        ++this.loveTime;
        if (this.loveTime >= 60 && this.fish.distanceToSqr(this.partner) < 9.0D) {
            this.breed();
        }

    }

    @Nullable
    private AbstractBreedableFishEntity getFreePartner() {
        List<AbstractBreedableFishEntity> list = this.level.getNearbyEntities(this.partnerClass, PARTNER_TARGETING, this.fish, this.fish.getBoundingBox().inflate(8.0D));
        double d0 = Double.MAX_VALUE;
        AbstractBreedableFishEntity fishentity = null;

        for(AbstractBreedableFishEntity fishentity1 : list) {
            if (this.fish.canMate(fishentity1) && this.fish.distanceToSqr(fishentity1) < d0) {
                fishentity = fishentity1;
                d0 = this.fish.distanceToSqr(fishentity1);
            }
        }

        return fishentity;
    }

    protected void breed() {
        this.fish.spawnChildFromBreeding((ServerWorld)this.level, this.partner);
    }
}