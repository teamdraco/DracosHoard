package coda.weecore.common.entities.goals;

import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.JumpGoal;
import net.minecraft.fluid.FluidState;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

public class WaterJumpGoal extends JumpGoal {
    private static final int[] JUMP_DISTANCES = new int[]{0, 1, 4, 5, 6, 7};
    private final MobEntity mob;
    private final int interval;
    private boolean breached;

    public WaterJumpGoal(MobEntity mob, int interval) {
        this.mob = mob;
        this.interval = interval;
    }

    public boolean canUse() {
        if (this.mob.getRandom().nextInt(this.interval) != 0) {
            return false;
        } else {
            Direction direction = this.mob.getMotionDirection();
            int i = direction.getStepX();
            int j = direction.getStepZ();
            BlockPos blockpos = this.mob.blockPosition();

            for(int k : JUMP_DISTANCES) {
                if (!this.canJumpTo(blockpos, i, j, k) || !this.isAirAbove(blockpos, i, j, k)) {
                    return false;
                }
            }

            return true;
        }
    }

    private boolean canJumpTo(BlockPos pos, int dx, int dz, int scale) {
        BlockPos blockpos = pos.offset(dx * scale, 0, dz * scale);
        return this.mob.level.getFluidState(blockpos).is(FluidTags.WATER) && !this.mob.level.getBlockState(blockpos).getMaterial().blocksMotion();
    }

    private boolean isAirAbove(BlockPos pos, int dx, int dz, int scale) {
        return this.mob.level.getBlockState(pos.offset(dx * scale, 1, dz * scale)).isAir() && this.mob.level.getBlockState(pos.offset(dx * scale, 2, dz * scale)).isAir();
    }

    public boolean canContinueToUse() {
        double d0 = this.mob.getDeltaMovement().y;
        return (!(d0 * d0 < (double)0.03F) || this.mob.xRot == 0.0F || !(Math.abs(this.mob.xRot) < 10.0F) || !this.mob.isInWater()) && !this.mob.isOnGround();
    }

    public boolean isInterruptable() {
        return false;
    }

    public void start() {
        Direction direction = this.mob.getMotionDirection();
        this.mob.setDeltaMovement(this.mob.getDeltaMovement().add((double)direction.getStepX() * 0.6D, 0.7D, (double)direction.getStepZ() * 0.6D));
        this.mob.getNavigation().stop();
    }

    public void stop() {
        this.mob.xRot = 0.0F;
    }

    public void tick() {
        boolean flag = this.breached;
        if (!flag) {
            FluidState ifluidstate = this.mob.level.getFluidState(new BlockPos(this.mob.blockPosition()));
            this.breached = ifluidstate.is(FluidTags.WATER);
        }

        if (this.breached && !flag) {
            this.mob.playSound(SoundEvents.DOLPHIN_JUMP, 1.0F, 1.0F);
        }

        Vector3d vector3d = this.mob.getDeltaMovement();
        if (vector3d.y * vector3d.y < (double)0.03F && this.mob.xRot != 0.0F) {
            this.mob.xRot = MathHelper.rotlerp(this.mob.xRot, 0.0F, 0.2F);
        } else {
            double d0 = Math.sqrt(Entity.getHorizontalDistanceSqr(vector3d));
            double d1 = Math.signum(-vector3d.y) * Math.acos(d0 / vector3d.length()) * (double)(180F / (float)Math.PI);
            this.mob.xRot = (float)d1;
        }
    }
}
