package top.xdi8.mod.firefly8.entity;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @see net.minecraft.world.entity.ai.goal.FollowMobGoal
 * @see net.minecraft.world.entity.ai.goal.FollowOwnerGoal
 * @see net.minecraft.world.entity.ai.goal.FollowParentGoal
 */
public sealed abstract class AbstractFollowPlayerGoal extends Goal {
    protected final FireflyEntity self;
    private final double speedModifier;
    @Nullable
    private LivingEntity following;
    private int timeToRecalcPath;

    public AbstractFollowPlayerGoal(FireflyEntity self,
                                    double speedModifier) {
        this.self = self;
        this.speedModifier = speedModifier;
    }

    @Nullable
    protected abstract LivingEntity whomToFollow();

    protected boolean isValidDistance(LivingEntity following) {
        double distance = self.distanceToSqr(following);
        return 9.0D < distance && distance < 256.0D;
        // 3-16 blocks
    }

    @Override
    public boolean canUse() {
        var followed = whomToFollow();
        if (followed == null || followed.isSpectator() || followed.isInvisible()) return false;
        following = followed;
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        if (this.following == null || !this.following.isAlive())
            return false;
        return isValidDistance(this.following);
    }

    @Override
    public void start() {
        super.start();
        this.timeToRecalcPath = 0;
    }

    @Override
    public void stop() {
        this.following = null;
        //self.getNavigation().stop();
    }

    @Override
    public void tick() {
        super.tick();
        if (--this.timeToRecalcPath <= 0) {
            if (following == null) {
                FireflyEntityTypes.LOGGER.warn("Missing follower");
                return;
            }
            this.timeToRecalcPath = this.adjustedTickDelay(6);
            self.getNavigation().moveTo(following, speedModifier);
        }
    }

    static final class FollowOwner extends AbstractFollowPlayerGoal {
        public FollowOwner(FireflyEntity self, double speedModifier) {
            super(self, speedModifier);
        }

        public FollowOwner(FireflyEntity self) {
            super(self, 0.6D);
        }

        @Override
        protected @Nullable LivingEntity whomToFollow() {
            return self.getOwner();
        }
    }

    static final class Randomly extends AbstractFollowPlayerGoal {
        Randomly(FireflyEntity self, double speedModifier) {
            super(self, speedModifier);
        }

        Randomly(FireflyEntity self) {
            this(self, 0.6D);
        }

        @Override
        protected @Nullable LivingEntity whomToFollow() {
            List<Player> list = self.getLevel().getEntitiesOfClass(Player.class,
                    self.getBoundingBox().inflate(16.0D, 16.0D, 16.0D));
            return list.stream().filter(player -> player.distanceToSqr(self) < 9.0D)
                    .findFirst().orElse(null);
        }
    }
}
