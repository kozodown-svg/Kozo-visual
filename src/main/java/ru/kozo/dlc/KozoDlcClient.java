package ru.kozo.dlc;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

public class KozoDlcClient implements ClientModInitializer {
    public static boolean showWatermark = true;
    public static boolean showFps = true;
    public static boolean showPing = true;
    public static boolean showTargetHud = true;

    private boolean wasPressed = false;

    @Override
    public void onInitializeClient() {
        HudElementRegistry.addLast(
            Identifier.of("kozodlc", "watermark"),
            (context, tickCounter) -> renderHud(context)
        );

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.getWindow() == null) return;

            boolean pressed = InputUtil.isKeyPressed(
                client.getWindow(), GLFW.GLFW_KEY_RIGHT_SHIFT
            );

            if (pressed && !wasPressed && client.currentScreen == null) {
                client.setScreen(new KozoScreen());
            }

            wasPressed = pressed;
        });
    }

    private void renderHud(DrawContext context) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        int x = 8;
        int y = 8;
        int purple = 0xFFAA66FF;
        int white = 0xFFFFFFFF;

        if (showWatermark) {
            context.fill(x - 4, y - 4, x + 94, y + 15, 0xB0101018);
            context.fill(x - 4, y - 4, x - 1, y + 15, purple);
            context.drawText(client.textRenderer, "KOZO DLC", x + 3, y, purple, false);
            x += 66;
        }

        if (showFps) {
            String fps = client.getCurrentFps() + " FPS";
            context.drawText(client.textRenderer, fps, x, y, white, true);
            x += client.textRenderer.getWidth(fps) + 8;
        }

        if (showPing && client.getNetworkHandler() != null) {
            var entry = client.getNetworkHandler()
                .getPlayerListEntry(client.player.getUuid());

            if (entry != null) {
                String ping = entry.getLatency() + " MS";
                context.drawText(client.textRenderer, ping, x, y, white, true);
            }
        }
    }
}
