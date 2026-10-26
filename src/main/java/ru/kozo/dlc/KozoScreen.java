package ru.kozo.dlc;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.Click;
import net.minecraft.client.input.KeyInput;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class KozoScreen extends Screen {
    private static final int PURPLE = 0xFFAA66FF;
    private static final int PANEL = 0xF012121A;
    private static final int ROW = 0xFF20202B;
    private static final int MUTED = 0xFF858595;

    private int left;
    private int top;

    private final String[] modules = {
        "Watermark", "FPS Counter", "Ping", "Target HUD"
    };

    public KozoScreen() {
        super(Text.literal("KOZO DLC"));
    }

    @Override
    protected void init() {
        left = (width - 340) / 2;
        top = (height - 220) / 2;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY,
                       float delta) {
        renderBackground(context, mouseX, mouseY, delta);

        context.fill(left, top, left + 340, top + 220, PANEL);
        context.fill(left, top, left + 340, top + 2, PURPLE);
        context.fill(left, top, left + 88, top + 220, 0xFF0D0D13);

        context.drawText(textRenderer, "K", left + 16, top + 14,
            PURPLE, false);
        context.drawText(textRenderer, "KOZO DLC", left + 16, top + 34,
            0xFFFFFFFF, false);
        context.drawText(textRenderer, "VISUAL CLIENT",
            left + 16, top + 48, MUTED, false);

        context.fill(left + 100, top + 12, left + 328, top + 38, ROW);
        context.drawText(textRenderer, "Modules",
            left + 110, top + 21, 0xFFFFFFFF, false);

        for (int i = 0; i < modules.length; i++) {
            int y = top + 48 + i * 35;
            boolean enabled = isEnabled(i);

            context.fill(left + 100, y, left + 328, y + 29, ROW);
            context.drawText(textRenderer, modules[i],
                left + 110, y + 10, 0xFFFFFFFF, false);

            int color = enabled ? PURPLE : MUTED;
            context.fill(left + 292, y + 9, left + 317, y + 21, color);
            context.fill(left + (enabled ? 306 : 293), y + 10,
                left + (enabled ? 316 : 303), y + 20, 0xFFFFFFFF);
        }

        context.drawText(textRenderer, "Right Shift - close",
            left + 100, top + 196, MUTED, false);

        super.render(context, mouseX, mouseY, delta);
    }

    private boolean isEnabled(int index) {
        return switch (index) {
            case 0 -> KozoDlcClient.showWatermark;
            case 1 -> KozoDlcClient.showFps;
            case 2 -> KozoDlcClient.showPing;
            case 3 -> KozoDlcClient.showTargetHud;
            default -> false;
        };
    }

    private void toggle(int index) {
        switch (index) {
            case 0 -> KozoDlcClient.showWatermark =
                !KozoDlcClient.showWatermark;
            case 1 -> KozoDlcClient.showFps = !KozoDlcClient.showFps;
            case 2 -> KozoDlcClient.showPing = !KozoDlcClient.showPing;
            case 3 -> KozoDlcClient.showTargetHud =
                !KozoDlcClient.showTargetHud;
        }
    }

    @Override
    public boolean mouseClicked(Click click, boolean doubled) {
        if (click.button() == 0) {
            double mouseX = click.x();
            double mouseY = click.y();

            for (int i = 0; i < modules.length; i++) {
                int y = top + 48 + i * 35;

                if (mouseX >= left + 100 && mouseX <= left + 328
                    && mouseY >= y && mouseY <= y + 29) {
                    toggle(i);
                    return true;
                }
            }
        }

        return super.mouseClicked(click, doubled);
    }

    @Override
    public boolean keyPressed(KeyInput input) {
        if (input.key() == GLFW.GLFW_KEY_RIGHT_SHIFT
            || input.key() == GLFW.GLFW_KEY_ESCAPE) {
            close();
            return true;
        }

        return super.keyPressed(input);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
                }
