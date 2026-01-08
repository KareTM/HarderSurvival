package kare.kareHardSurvival.Helpers.Prospecting;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public final class Prospector {

    private Prospector() {}

    /* =====================================================
       ORE GROUP DEFINITIONS
       ===================================================== */

    public enum OreGroup {
        COAL,
        CUPROUS,
        FERROUS,
        PRECIOUS,
        RARE
    }

    private static final Map<Material, OreGroup> ORE_MAP = Map.ofEntries(
            Map.entry(Material.COAL_ORE, OreGroup.COAL),
            Map.entry(Material.DEEPSLATE_COAL_ORE, OreGroup.COAL),

            Map.entry(Material.COPPER_ORE, OreGroup.CUPROUS),
            Map.entry(Material.DEEPSLATE_COPPER_ORE, OreGroup.CUPROUS),
            Map.entry(Material.RAW_COPPER_BLOCK, OreGroup.CUPROUS),

            Map.entry(Material.IRON_ORE, OreGroup.FERROUS),
            Map.entry(Material.DEEPSLATE_IRON_ORE, OreGroup.FERROUS),
            Map.entry(Material.RAW_IRON_BLOCK, OreGroup.FERROUS),

            Map.entry(Material.GOLD_ORE, OreGroup.PRECIOUS),
            Map.entry(Material.DEEPSLATE_GOLD_ORE, OreGroup.PRECIOUS),
            Map.entry(Material.DIAMOND_ORE, OreGroup.PRECIOUS),
            Map.entry(Material.DEEPSLATE_DIAMOND_ORE, OreGroup.PRECIOUS),

            Map.entry(Material.EMERALD_ORE, OreGroup.RARE),
            Map.entry(Material.DEEPSLATE_EMERALD_ORE, OreGroup.RARE),
            Map.entry(Material.ANCIENT_DEBRIS, OreGroup.RARE)
    );

    /* =====================================================
       RESULT TYPES
       ===================================================== */

    public enum Strength {
        NONE,
        FAINT,
        MODERATE,
        STRONG
    }

    public enum RelativeHint {
        FORWARD,
        BACKWARD,
        LEFT,
        RIGHT,
        UP,
        DOWN,
        AROUND
    }

    public record ProspectResult(OreGroup oreGroup, Strength strength, RelativeHint hint) {}

    /* =====================================================
       PUBLIC API
       ===================================================== */

    /**
     * Performs a probabilistic prospecting scan.
     * Returns player-relative directional hints.
     */
    public static List<ProspectResult> prospect(Location origin, ProspectingProfile profile) {
        World world = origin.getWorld();
        if (world == null) return List.of();

        int originX = origin.getBlockX();
        int originY = origin.getBlockY();
        int originZ = origin.getBlockZ();
        ThreadLocalRandom random = ThreadLocalRandom.current();

        double yawRad = Math.toRadians(origin.getYaw());

        // Hit counters
        Map<OreGroup, Integer> hits = new EnumMap<>(OreGroup.class);
        Map<OreGroup, Double> weightedHits = new EnumMap<>(OreGroup.class);

        Map<OreGroup, Double> forwardHits = new EnumMap<>(OreGroup.class);
        Map<OreGroup, Double> backwardHits = new EnumMap<>(OreGroup.class);
        Map<OreGroup, Double> leftHits = new EnumMap<>(OreGroup.class);
        Map<OreGroup, Double> rightHits = new EnumMap<>(OreGroup.class);
        Map<OreGroup, Double> upHits = new EnumMap<>(OreGroup.class);
        Map<OreGroup, Double> downHits = new EnumMap<>(OreGroup.class);

        for (int i = 0; i < profile.samples(); i++) {
            // Weighted spherical sampling
            double r = Math.cbrt(random.nextDouble()) * profile.radius();
            double theta = random.nextDouble() * 2 * Math.PI;
            double phi = random.nextDouble() * Math.PI;

            int dx = (int) Math.round(r * Math.sin(phi) * Math.cos(theta));
            int dy = (int) Math.round(r * Math.cos(phi));
            int dz = (int) Math.round(r * Math.sin(phi) * Math.sin(theta));

            int x = originX + dx;
            int y = originY + dy;
            int z = originZ + dz;

            if (y < world.getMinHeight() || y >= world.getMaxHeight()) continue; // skip invalid Y

            Block block = world.getBlockAt(x, y, z);
            OreGroup group = ORE_MAP.get(block.getType());
            if (group == null) continue;

            // Count hits
            hits.merge(group, 1, Integer::sum);

            // Weighted hit by inverse distance squared
            double distSq = dx*dx + dy*dy + dz*dz;
            int radiusSq = profile.radius() * profile.radius();
            double weight = Math.max(0, radiusSq - distSq) / (2 * radiusSq);
            weightedHits.merge(group, weight, Double::sum);

            // Convert to player-relative coordinates
            double forwardOffset = dx * Math.cos(-yawRad) - dz * Math.sin(-yawRad);
            double rightOffset   = dx * Math.sin(-yawRad) + dz * Math.cos(-yawRad);

            // Accumulate relative direction weights
            if (forwardOffset > 2) forwardHits.merge(group, weight, Double::sum);
            else if (forwardOffset < -2) backwardHits.merge(group, weight, Double::sum);

            if (rightOffset > 2) rightHits.merge(group, weight, Double::sum);
            else if (rightOffset < -2) leftHits.merge(group, weight, Double::sum);

            if (dy > 2) upHits.merge(group, weight, Double::sum);
            else if (dy < -2) downHits.merge(group, weight, Double::sum);
        }

        return evaluateWithRelativeHints(hits, weightedHits, forwardHits, backwardHits, leftHits, rightHits, upHits, downHits, profile);
    }

    /* =====================================================
       EVALUATION LOGIC
       ===================================================== */

    private static List<ProspectResult> evaluateWithRelativeHints(
            Map<OreGroup, Integer> hits,
            Map<OreGroup, Double> weightedHits,
            Map<OreGroup, Double> forwardHits,
            Map<OreGroup, Double> backwardHits,
            Map<OreGroup, Double> leftHits,
            Map<OreGroup, Double> rightHits,
            Map<OreGroup, Double> upHits,
            Map<OreGroup, Double> downHits,
            ProspectingProfile profile
    ) {
        if (hits.isEmpty()) return List.of();

        int maxHits = hits.values().stream().max(Integer::compareTo).orElse(0);
        int cutoff = (int) Math.ceil(maxHits * profile.leewayRatio());

        List<ProspectResult> results = new ArrayList<>();

        for (var entry : hits.entrySet()) {
            int count = entry.getValue();
            if (count < cutoff) continue;

            double weighted = weightedHits.getOrDefault(entry.getKey(), 0.0);
            Strength strength = strengthFromWeightedHits(entry.getKey(), weighted);
            if (strength == Strength.NONE) continue;

            double forward = forwardHits.getOrDefault(entry.getKey(), 0.0);
            double backward = backwardHits.getOrDefault(entry.getKey(), 0.0);
            double left = leftHits.getOrDefault(entry.getKey(), 0.0);
            double right = rightHits.getOrDefault(entry.getKey(), 0.0);
            double up = upHits.getOrDefault(entry.getKey(), 0.0);
            double down = downHits.getOrDefault(entry.getKey(), 0.0);

            var hint = relativeHintFromCounts(forward, backward, left, right, up, down);

            results.add(new ProspectResult(entry.getKey(), strength, hint));
        }

        return results;
    }

    /* =====================================================
       STRENGTH & RELATIVE DIRECTION HELPERS
       ===================================================== */

    private static Strength strengthFromWeightedHits(OreGroup group, double weightedHits) {
        double faintThreshold = 0.25;
        double moderateThreshold = 0.75;
        double strongThreshold = 1.5;

        if (group == OreGroup.COAL) {
            faintThreshold = 0.4;
            moderateThreshold = 1;
            strongThreshold = 2;
        }

        if (weightedHits >= strongThreshold) return Strength.STRONG;
        if (weightedHits >= moderateThreshold) return Strength.MODERATE;
        if (weightedHits >= faintThreshold) return Strength.FAINT;
        return Strength.NONE;
    }

    private static RelativeHint relativeHintFromCounts(
            double forward, double backward, double left, double right, double up, double down
    ) {
        double max = Math.max(forward, Math.max(backward, Math.max(left, Math.max(right, Math.max(up, down)))));

        if (max == 0) return RelativeHint.AROUND;

        int closeCount = 0;
        if (forward >= max * 0.8) closeCount++;
        if (backward >= max * 0.8) closeCount++;
        if (left >= max * 0.8) closeCount++;
        if (right >= max * 0.8) closeCount++;
        if (up >= max * 0.8) closeCount++;
        if (down >= max * 0.8) closeCount++;

        if (closeCount > 1) return RelativeHint.AROUND;

        if (forward == max) return RelativeHint.FORWARD;
        if (backward == max) return RelativeHint.BACKWARD;
        if (left == max) return RelativeHint.LEFT;
        if (right == max) return RelativeHint.RIGHT;
        if (up == max) return RelativeHint.UP;
        return RelativeHint.DOWN;
    }

}
