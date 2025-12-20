package kare.kareHardSurvival.Advancements;

import com.fren_gor.ultimateAdvancementAPI.advancement.BaseAdvancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementDisplay;
import com.fren_gor.ultimateAdvancementAPI.advancement.multiParents.MultiParentsAdvancement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

public class ParentVisibleMultiParentAdvancement extends MultiParentsAdvancement implements AllParentVIsible {

    public ParentVisibleMultiParentAdvancement(@NotNull String key, @NotNull AdvancementDisplay display, @Range(from = 1L, to = 2147483647L) int maxProgression, @NotNull BaseAdvancement... parents) {
        super(key, display, maxProgression, parents);
    }
}
