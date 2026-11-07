package com.example.betteraim;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import org.lwjgl.glfw.GLFW;

public class BetterAimClient implements ClientModInitializer {
    private static boolean enabled = false;
    private static KeyBinding toggleKey;

    @Override
    public void onInitializeClient() {
        KeyBinding.Category category = KeyBinding.Category.create(Identifier.of("betteraim", "main"));
        toggleKey = KeyBindingHelper.registerKeyBinding(
            new KeyBinding("key.betteraim.toggle", GLFW.GLFW_KEY_R, category)
        );

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (toggleKey.wasPressed()) {
                enabled = !enabled;
                if (client.player != null) {
                    client.player.sendMessage(Text.translatable(
                        enabled ? "message.betteraim.enabled" : "message.betteraim.disabled"
                    ), true);
                }
            }

            if (enabled && client.player != null && client.interactionManager != null) {
                if (client.player.getAttackCooldownProgress(0.5f) < 1.0f) return;

                if (client.crosshairTarget != null && client.crosshairTarget.getType() == HitResult.Type.ENTITY) {
                    EntityHitResult hitResult = (EntityHitResult) client.crosshairTarget;
                    Entity target = hitResult.getEntity();

                    if (target.isAttackable() && target.isAlive()) {
                        client.interactionManager.attackEntity(client.player, target);
                        client.player.swingHand(Hand.MAIN_HAND);
                    }
                }
            }
        });
    }
}
