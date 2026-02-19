package dev.hyperlynx.pulsetech.client;

import dev.hyperlynx.pulsetech.feature.datasheet.Datasheet;
import dev.hyperlynx.pulsetech.feature.debugger.DebuggerInfoManifest;
import dev.hyperlynx.pulsetech.feature.debugger.infotype.DebuggerByteInfo;
import dev.hyperlynx.pulsetech.feature.debugger.infotype.DebuggerPosInfo;
import dev.hyperlynx.pulsetech.feature.debugger.infotype.DebuggerSequenceInfo;
import dev.hyperlynx.pulsetech.feature.debugger.infotype.DebuggerTextInfo;
import dev.hyperlynx.pulsetech.feature.screen.ScreenData;
import net.minecraft.core.BlockPos;

/// The ONLY class within the client package that is safe to call upon from outside the package!
public class ClientWrapper {
    public static void openConsoleScreen(BlockPos pos, String prior_lines, String command_box_text) {
        PulsetechClient.openConsoleScreen(pos, prior_lines, command_box_text);
    }

    public static void acceptConsoleLine(BlockPos pos, String line) {
        PulsetechClient.acceptConsoleLine(pos, line);
    }

    public static void setPriorConsoleLines(BlockPos pos, String lines, String command_box_text) {
        PulsetechClient.setPriorConsoleLines(pos, lines, command_box_text);
    }

    public static void openSequenceScreen(BlockPos pos) {
        PulsetechClient.openSequenceScreen(pos);
    }

    public static void openDatasheetScreen(Datasheet datasheet) {
        PulsetechClient.openDatasheetScreen(datasheet);
    }

    public static void updateScreenBlock(ScreenData data, BlockPos pos) {
        PulsetechClient.updateScreenBlock(data, pos);
    }

    public static void openNumberChooseScreen(BlockPos pos) {
        PulsetechClient.openNumberChooseScreen(pos);
    }

    public static void openDebuggerScreen(DebuggerInfoManifest manifest) {
        PulsetechClient.openDebuggerScreen(manifest);
    }

    public static void acceptDebuggerSequenceInfo(DebuggerSequenceInfo info) {
        PulsetechClient.acceptDebuggerSequenceInfo(info);
    }

    public static void acceptDebuggerByteInfo(DebuggerByteInfo info) {
        PulsetechClient.acceptDebuggerByteInfo(info);
    }

    public static void acceptDebuggerTextInfo(DebuggerTextInfo info) {
        PulsetechClient.acceptDebuggerTextInfo(info);
    }

    public static void acceptDebuggerPosInfo(DebuggerPosInfo info) {
        PulsetechClient.acceptDebuggerPosInfo(info);
    }
}
