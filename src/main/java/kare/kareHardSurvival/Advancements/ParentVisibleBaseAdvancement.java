package kare.kareHardSurvival.Advancements;

import com.fren_gor.ultimateAdvancementAPI.advancement.Advancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.BaseAdvancement;
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementDisplay;
import com.fren_gor.ultimateAdvancementAPI.visibilities.ParentGrantedVisibility;

public class ParentVisibleBaseAdvancement extends BaseAdvancement implements ParentGrantedVisibility {
    public ParentVisibleBaseAdvancement(String key, AdvancementDisplay advancementDisplay, Advancement parent, int maxProgression) {
        super(key, advancementDisplay, parent, maxProgression);
    }
}
