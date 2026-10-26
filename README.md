# KOZO DLC — initial project

Client-side visual mod project for Minecraft Java 1.21.11 + Fabric, designed to avoid native libraries/MCEF and be lightweight for Fold Craft Launcher on Android.

## Current prototype scope
- Right Shift opens the KOZO DLC GUI.
- Dark three-column GUI with purple accent and module toggles.
- HUD watermark with KOZO DLC, live FPS, and server ping.
- Basic toggle states for watermark/FPS/ping and placeholder entries for upcoming visual modules.
- No Combat category, Attack Aura, or automated attack behavior.

## Build
Requires JDK 21 and internet access for Gradle to download Fabric/Minecraft dependencies.

```bash
gradle build
```

The remapped mod JAR should be in `build/libs/`.

## Important
This first source drop has not been compiled or launched in Minecraft in this environment because Gradle is not installed and external Maven repositories are unreachable here. Treat it as an initial development build, not a tested release. Compile/test logs should be checked before installing on a main profile.
