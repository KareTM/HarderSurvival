package kare.kareHardSurvival.Advancements;

import com.fren_gor.ultimateAdvancementAPI.advancement.Advancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.BaseAdvancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.multiParents.AbstractMultiParentsAdvancement;
import com.fren_gor.ultimateAdvancementAPI.database.TeamProgression;
import com.fren_gor.ultimateAdvancementAPI.visibilities.IVisibility;
import com.google.common.base.Preconditions;
import org.jetbrains.annotations.NotNull;

public interface AllParentVIsible extends IVisibility {
    default boolean isVisible(@NotNull Advancement advancement, @NotNull TeamProgression progression) {
        Preconditions.checkNotNull(advancement, "Advancement is null.");
        Preconditions.checkNotNull(progression, "TeamProgression is null.");
        if (advancement.getProgression(progression) > 0) {
            return true;
        } else if (advancement instanceof AbstractMultiParentsAdvancement multiParent) {
            return multiParent.isEveryParentGranted(progression);
        } else if (advancement instanceof BaseAdvancement base) {
            return base.getParent().isGranted(progression);
        } else {
            return false;
        }
    }
}
