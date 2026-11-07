package com.example.betteraim;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BetterAimMod implements ModInitializer {
    public static final String MOD_ID = "betteraim";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Better Aim loaded");
    }
}
