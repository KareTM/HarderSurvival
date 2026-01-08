package kare.kareHardSurvival.Helpers.Prospecting;

import kare.kareHardSurvival.Helpers.FlagHelper;
import org.bukkit.inventory.ItemStack;

public final class ProspectingProfiles {
    private ProspectingProfiles() {}

    public static final ProspectingProfile STANDARD =
            new ProspectingProfile(
                    16,
                    1000,
                    0.75,
                    0.05
            );

    public static final ProspectingProfile ADVANCED =
            new ProspectingProfile(
                    18,
                    200,
                    0.70,
                    0.03
            );

    public static final ProspectingProfile ELITE =
            new ProspectingProfile(
                    24,
                    4000,
                    0.65,
                    0.01
            );

    public static ProspectingProfile getFromTool(ItemStack tool) {
        if (!FlagHelper.hasFlag(tool, FlagHelper.flagProspect)) return null;

        if (FlagHelper.hasFlag(tool, FlagHelper.flagProspectBasic)) return STANDARD;
        //else if (FlagHelper.hasFlag(tool, FlagHelper.flagProspectAdvanced)) return ADVANCED;
        else return null;
    }
}
