package kare.kareHardSurvival;

import com.fren_gor.ultimateAdvancementAPI.AdvancementMain;
import com.fren_gor.ultimateAdvancementAPI.database.impl.SQLite;
import kare.kareHardSurvival.Advancements.AdvancementManager;
import kare.kareHardSurvival.Helpers.Commands.CommandManager;
import kare.kareHardSurvival.Items.ItemManager;
import kare.kareHardSurvival.Items.ItemRegistry;
import kare.kareHardSurvival.Listeners.*;
import kare.kareHardSurvival.Recipes.RecipeAdder;
import kare.kareHardSurvival.Recipes.RecipeDisabler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Objects;

public final class KareHardSurvival extends JavaPlugin implements Listener {
    AdvancementMain AdvancementLib;

    @Override
    public void onLoad() {
        AdvancementLib = new AdvancementMain(this);
        AdvancementLib.load();
    }

    @Override
    public void onEnable() {
        AdvancementLib.enable(() -> new SQLite(AdvancementLib, new File(getDataFolder(), "database.db")));

        ItemManager.setPlugin(this);
        // Plugin startup logic
        RecipeDisabler rd = new RecipeDisabler(getServer());
        rd.disableRecipes();

        RecipeAdder ra = new RecipeAdder(getServer(), this);
        ra.addAllRecipes();

        AdvancementManager adm = new AdvancementManager(this);
        adm.Advancement();

        HealthManager hm = new HealthManager();
        getServer().getPluginManager().registerEvents(hm, this);

        BlockBreakManager bbm = new BlockBreakManager(this);
        getServer().getPluginManager().registerEvents(bbm, this);

        BlockInteractManager bim = new BlockInteractManager(this);
        getServer().getPluginManager().registerEvents(bim, this);

        CraftManager cm = new CraftManager(this);
        getServer().getPluginManager().registerEvents(cm, this);

        PlayerManager pm = new PlayerManager();
        getServer().getPluginManager().registerEvents(pm, this);

        ItemPickupManager ipm = new ItemPickupManager(this);
        getServer().getPluginManager().registerEvents(ipm, this);

        VillagerManager vm = new VillagerManager(this);
        getServer().getPluginManager().registerEvents(vm, this);

        FurnaceManager fm = new FurnaceManager(this);
        getServer().getPluginManager().registerEvents(fm, this);

        LootManager lm = new LootManager(this);
        getServer().getPluginManager().registerEvents(lm, this);

        ItemRegistry.initialize();
        Objects.requireNonNull(getCommand("migrateitem"))
                .setExecutor(new CommandManager());
    }

    @Override
    public void onDisable() {
        AdvancementLib.disable();
        // Plugin shutdown logic
    }
}
