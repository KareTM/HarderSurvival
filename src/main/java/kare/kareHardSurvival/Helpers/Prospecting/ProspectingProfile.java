package kare.kareHardSurvival.Helpers.Prospecting;

/**
 * @param leewayRatio how many results show up
 * @param noiseChance future-proofing
 */
public record ProspectingProfile(int radius, int samples, double leewayRatio, double noiseChance) {
}
