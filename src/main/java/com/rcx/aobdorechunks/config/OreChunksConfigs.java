package com.rcx.aobdorechunks.config;

public class OreChunksConfigs {

	private boolean isEnabled = true;
	private int dropCount = 1;
	private int minXPDrop = 1;
	private int maxXPDrop = 4;
	private String stoneType = "stone";

	public OreChunksConfigs(String name) {

		if ("eximite".equals(name) || "meutoite".equals(name) || "endium".equals(name)) {
			stoneType = "end";
		} else if ("cobalt".equals(name) || "ardite".equals(name) || "ignatius".equals(name) || "shadowiron".equals(name) || "lemurite".equals(name) || "midasium".equals(name) || "vyroxeres".equals(name) || "ceruclase".equals(name) || "alduorite".equals(name) || "kalendrite".equals(name) || "vulcanite".equals(name) || "sanguinite".equals(name)) {
			stoneType = "nether";
		} else {
			dropCount = 1;
			minXPDrop = 1;
			maxXPDrop = 4;
			stoneType = "stone";
		}
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public int getDropCount() {
		return dropCount;
	}

	public void setDropCount(int dropAmount) {
		this.dropCount = dropAmount;
	}

	public int getMinXPDrop() {
		return minXPDrop;
	}

	public void setMinXPDrop(int XPAmount) {
		this.minXPDrop = XPAmount;
	}

	public int getMaxXPDrop() {
		return maxXPDrop;
	}

	public void setMaxXPDrop(int XPAmount) {
		this.maxXPDrop = XPAmount;
	}

	public String getStoneType() {
		return stoneType;
	}

	public void setStoneType(String StoneType) {
		this.stoneType = StoneType;
	}

}
