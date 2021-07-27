package coda.weecore.common.entities;

import coda.weecore.common.entities.ai.BabyFishEntitySpawnEvent;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.passive.fish.AbstractFishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.*;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.UUID;

public abstract class AbstractBreedableFishEntity extends AbstractFishEntity {
    private static final DataParameter<Boolean> DATA_BABY_ID = EntityDataManager.defineId(AbstractBreedableFishEntity.class, DataSerializers.BOOLEAN);
    protected int age;
    protected int forcedAge;
    protected int forcedAgeTimer;

    protected AbstractBreedableFishEntity(EntityType<? extends AbstractBreedableFishEntity> p_i48581_1_, World p_i48581_2_) {
        super(p_i48581_1_, p_i48581_2_);
    }

    public ILivingEntityData finalizeSpawn(IServerWorld p_213386_1_, DifficultyInstance p_213386_2_, SpawnReason p_213386_3_, @Nullable ILivingEntityData p_213386_4_, @Nullable CompoundNBT p_213386_5_) {
        if (p_213386_4_ == null) {
            p_213386_4_ = new AbstractBreedableFishEntity.AgeableData(true);
        }

        AbstractBreedableFishEntity.AgeableData ageableentity$ageabledata = (AbstractBreedableFishEntity.AgeableData)p_213386_4_;
        if (ageableentity$ageabledata.isShouldSpawnBaby() && ageableentity$ageabledata.getGroupSize() > 0 && this.random.nextFloat() <= ageableentity$ageabledata.getBabySpawnChance()) {
            this.setAge(-24000);
        }

        ageableentity$ageabledata.increaseGroupSizeByOne();
        return super.finalizeSpawn(p_213386_1_, p_213386_2_, p_213386_3_, p_213386_4_, p_213386_5_);
    }

    @Nullable
    public abstract AbstractBreedableFishEntity getBreedOffspring(ServerWorld p_241840_1_, AbstractBreedableFishEntity p_241840_2_);

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_BABY_ID, false);
    }

    public int getAge() {
        if (this.level.isClientSide) {
            return this.entityData.get(DATA_BABY_ID) ? -1 : 1;
        } else {
            return this.age;
        }
    }

    public void ageUp(int p_175501_1_, boolean p_175501_2_) {
        int i = this.getAge();
        i = i + p_175501_1_ * 20;
        if (i > 0) {
            i = 0;
        }

        int j = i - i;
        this.setAge(i);
        if (p_175501_2_) {
            this.forcedAge += j;
            if (this.forcedAgeTimer == 0) {
                this.forcedAgeTimer = 40;
            }
        }

        if (this.getAge() == 0) {
            this.setAge(this.forcedAge);
        }

    }

    public void ageUp(int p_110195_1_) {
        this.ageUp(p_110195_1_, false);
    }

    public void setAge(int p_70873_1_) {
        int i = this.age;
        this.age = p_70873_1_;
        if (i < 0 && p_70873_1_ >= 0 || i >= 0 && p_70873_1_ < 0) {
            this.entityData.set(DATA_BABY_ID, p_70873_1_ < 0);
            this.ageBoundaryReached();
        }

    }

    public void addAdditionalSaveData(CompoundNBT p_213281_1_) {
        super.addAdditionalSaveData(p_213281_1_);
        p_213281_1_.putInt("Age", this.getAge());
        p_213281_1_.putInt("ForcedAge", this.forcedAge);
        p_213281_1_.putInt("InLove", this.inLove);
        if (this.loveCause != null) {
            p_213281_1_.putUUID("LoveCause", this.loveCause);
        }

    }

    public void readAdditionalSaveData(CompoundNBT p_70037_1_) {
        super.readAdditionalSaveData(p_70037_1_);
        this.setAge(p_70037_1_.getInt("Age"));
        this.forcedAge = p_70037_1_.getInt("ForcedAge");
        this.inLove = p_70037_1_.getInt("InLove");
        this.loveCause = p_70037_1_.hasUUID("LoveCause") ? p_70037_1_.getUUID("LoveCause") : null;
    }

    public void onSyncedDataUpdated(DataParameter<?> p_184206_1_) {
        if (DATA_BABY_ID.equals(p_184206_1_)) {
            this.refreshDimensions();
        }

        super.onSyncedDataUpdated(p_184206_1_);
    }

    public void aiStep() {
        super.aiStep();
        if (this.level.isClientSide) {
            if (this.forcedAgeTimer > 0) {
                if (this.forcedAgeTimer % 4 == 0) {
                    this.level.addParticle(ParticleTypes.HAPPY_VILLAGER, this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D), 0.0D, 0.0D, 0.0D);
                }

                --this.forcedAgeTimer;
            }
        } else if (this.isAlive()) {
            int i = this.getAge();
            if (i < 0) {
                ++i;
                this.setAge(i);
            } else if (i > 0) {
                --i;
                this.setAge(i);
            }
        }

        if (this.getAge() != 0) {
            this.inLove = 0;
        }

        if (this.inLove > 0) {
            --this.inLove;
            if (this.inLove % 10 == 0) {
                double d0 = this.random.nextGaussian() * 0.02D;
                double d1 = this.random.nextGaussian() * 0.02D;
                double d2 = this.random.nextGaussian() * 0.02D;
                this.level.addParticle(ParticleTypes.HEART, this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D), d0, d1, d2);
            }
        }
    }

    protected void ageBoundaryReached() {
    }

    public boolean isBaby() {
        return this.getAge() < 0;
    }

    public void setBaby(boolean p_82227_1_) {
        this.setAge(p_82227_1_ ? -24000 : 0);
    }

    private int inLove;
    private UUID loveCause;

    protected void customServerAiStep() {
        if (this.getAge() != 0) {
            this.inLove = 0;
        }

        super.customServerAiStep();
    }

    public boolean hurt(DamageSource p_70097_1_, float p_70097_2_) {
        if (this.isInvulnerableTo(p_70097_1_)) {
            return false;
        } else {
            this.inLove = 0;
            return super.hurt(p_70097_1_, p_70097_2_);
        }
    }

    public float getWalkTargetValue(BlockPos p_205022_1_, IWorldReader p_205022_2_) {
        return p_205022_2_.getBlockState(p_205022_1_.below()).is(Blocks.GRASS_BLOCK) ? 10.0F : p_205022_2_.getBrightness(p_205022_1_) - 0.5F;
    }

    public double getMyRidingOffset() {
        return 0.14D;
    }

    public int getAmbientSoundInterval() {
        return 120;
    }

    public boolean removeWhenFarAway(double p_213397_1_) {
        return false;
    }

    protected int getExperienceReward(PlayerEntity p_70693_1_) {
        return 1 + this.level.random.nextInt(3);
    }

    public boolean isFood(ItemStack p_70877_1_) {
        return p_70877_1_.getItem() == Items.WHEAT;
    }

    public ActionResultType mobInteract(PlayerEntity p_230254_1_, Hand p_230254_2_) {
        ItemStack itemstack = p_230254_1_.getItemInHand(p_230254_2_);
        if (this.isFood(itemstack)) {
            int i = this.getAge();
            if (!this.level.isClientSide && i == 0 && this.canFallInLove()) {
                this.usePlayerItem(p_230254_1_, itemstack);
                this.setInLove(p_230254_1_);
                return ActionResultType.SUCCESS;
            }

            if (this.isBaby()) {
                this.usePlayerItem(p_230254_1_, itemstack);
                this.ageUp((int)((float)(-i / 20) * 0.1F), true);
                return ActionResultType.sidedSuccess(this.level.isClientSide);
            }

            if (this.level.isClientSide) {
                return ActionResultType.CONSUME;
            }
        }

        return super.mobInteract(p_230254_1_, p_230254_2_);
    }

    protected void usePlayerItem(PlayerEntity p_175505_1_, ItemStack p_175505_2_) {
        if (!p_175505_1_.abilities.instabuild) {
            p_175505_2_.shrink(1);
        }

    }

    public boolean canFallInLove() {
        return this.inLove <= 0;
    }

    public void setInLove(@Nullable PlayerEntity p_146082_1_) {
        this.inLove = 600;
        if (p_146082_1_ != null) {
            this.loveCause = p_146082_1_.getUUID();
        }

        this.level.broadcastEntityEvent(this, (byte)18);
    }

    public void setInLoveTime(int p_204700_1_) {
        this.inLove = p_204700_1_;
    }

    public int getInLoveTime() {
        return this.inLove;
    }

    @Nullable
    public ServerPlayerEntity getLoveCause() {
        if (this.loveCause == null) {
            return null;
        } else {
            PlayerEntity playerentity = this.level.getPlayerByUUID(this.loveCause);
            return playerentity instanceof ServerPlayerEntity ? (ServerPlayerEntity)playerentity : null;
        }
    }

    public boolean isInLove() {
        return this.inLove > 0;
    }

    public void resetLove() {
        this.inLove = 0;
    }

    public boolean canMate(AbstractBreedableFishEntity p_70878_1_) {
        if (p_70878_1_ == this) {
            return false;
        } else if (p_70878_1_.getClass() != this.getClass()) {
            return false;
        } else {
            return this.isInLove() && p_70878_1_.isInLove();
        }
    }

    public void spawnChildFromBreeding(ServerWorld p_234177_1_, AbstractBreedableFishEntity p_234177_2_) {
        AbstractBreedableFishEntity ageableentity = this.getBreedOffspring(p_234177_1_, p_234177_2_);
        final BabyFishEntitySpawnEvent event = new BabyFishEntitySpawnEvent(this, p_234177_2_, ageableentity);
        final boolean cancelled = net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event);
        ageableentity = event.getChild();
        if (cancelled) {
            //Reset the "inLove" state for the animals
            this.setAge(6000);
            p_234177_2_.setAge(6000);
            this.resetLove();
            p_234177_2_.resetLove();
            return;
        }
        if (ageableentity != null) {
            ServerPlayerEntity serverplayerentity = this.getLoveCause();
            if (serverplayerentity == null && p_234177_2_.getLoveCause() != null) {
                serverplayerentity = p_234177_2_.getLoveCause();
            }

/*
            if (serverplayerentity != null) {
                serverplayerentity.awardStat(Stats.ANIMALS_BRED);
                CriteriaTriggers.BRED_ANIMALS.trigger(serverplayerentity, this, p_234177_2_, ageableentity);
            }
*/

            this.setAge(6000);
            p_234177_2_.setAge(6000);
            this.resetLove();
            p_234177_2_.resetLove();
            ageableentity.setBaby(true);
            ageableentity.moveTo(this.getX(), this.getY(), this.getZ(), 0.0F, 0.0F);
            p_234177_1_.addFreshEntityWithPassengers(ageableentity);
            p_234177_1_.broadcastEntityEvent(this, (byte)18);
            if (p_234177_1_.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
                p_234177_1_.addFreshEntity(new ExperienceOrbEntity(p_234177_1_, this.getX(), this.getY(), this.getZ(), this.getRandom().nextInt(7) + 1));
            }

        }
    }

    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte p_70103_1_) {
        if (p_70103_1_ == 18) {
            for(int i = 0; i < 7; ++i) {
                double d0 = this.random.nextGaussian() * 0.02D;
                double d1 = this.random.nextGaussian() * 0.02D;
                double d2 = this.random.nextGaussian() * 0.02D;
                this.level.addParticle(ParticleTypes.HEART, this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D), d0, d1, d2);
            }
        } else {
            super.handleEntityEvent(p_70103_1_);
        }

    }

    public static class AgeableData implements ILivingEntityData {
        private int groupSize;
        private final boolean shouldSpawnBaby;
        private final float babySpawnChance;

        private AgeableData(boolean p_i241905_1_, float p_i241905_2_) {
            this.shouldSpawnBaby = p_i241905_1_;
            this.babySpawnChance = p_i241905_2_;
        }

        public AgeableData(boolean p_i241904_1_) {
            this(p_i241904_1_, 0.05F);
        }

        public AgeableData(float p_i241903_1_) {
            this(true, p_i241903_1_);
        }

        public int getGroupSize() {
            return this.groupSize;
        }

        public void increaseGroupSizeByOne() {
            ++this.groupSize;
        }

        public boolean isShouldSpawnBaby() {
            return this.shouldSpawnBaby;
        }

        public float getBabySpawnChance() {
            return this.babySpawnChance;
        }
    }
}
