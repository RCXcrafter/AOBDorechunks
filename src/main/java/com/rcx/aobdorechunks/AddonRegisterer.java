package com.rcx.aobdorechunks;

import ganymedes01.aobd.api.AOBDAddonManager;

public class AddonRegisterer {

	public static void registerAddon() {
		AOBDAddonManager.registerAddon(new OreChunkAddon());
	}
}