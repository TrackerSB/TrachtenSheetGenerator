package de.traunviertler_traunwalchen.trachtenSheetGenerator.utility;

import com.profesorfalken.jpowershell.PowerShell;
import com.profesorfalken.jpowershell.PowerShellNotAvailableException;
import com.profesorfalken.jpowershell.PowerShellResponse;
import org.apache.commons.lang3.SystemUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class SystemCommandUtility {
    private static final Logger LOGGER = Logger.getLogger(SystemCommandUtility.class.getName());

    private SystemCommandUtility() {
    }

    public static boolean isCommandAvailable(@NotNull String command) {
        if (SystemUtils.IS_OS_WINDOWS) {
            boolean isCommandAvailable;
            String params = "-cmdName " + command;
            try {
                PowerShellResponse result = executePowerShellScript("scripts/commandAvailable.ps1", params);
                isCommandAvailable = result.getCommandOutput().equalsIgnoreCase("yes");
            } catch (SystemCommandFailedException ex) {
                LOGGER.log(Level.WARNING, "Can not determine whether command \"" + command + "\" is available", ex);
                isCommandAvailable = false;
            }
            return isCommandAvailable;
        } else {
            throw new UnsupportedOperationException("OS not supported");
        }
    }

    @NotNull
    public static Optional<String> resolvePath(@NotNull Path path) {
        String resolvedPath;
        if (SystemUtils.IS_OS_WINDOWS) {
            try {
                resolvedPath = executePowerShellScript("scripts/getFullPath.ps1", "-path \"" + path.toString() + "\"")
                        .getCommandOutput();
            } catch (SystemCommandFailedException ex) {
                LOGGER.log(Level.WARNING, "Could not resolve path", ex);
                resolvedPath = null;
            }
        } else {
            throw new UnsupportedOperationException("OS not supported");
        }
        return Optional.ofNullable(resolvedPath);
    }

    public static void execute(@NotNull String command, @NotNull String params,
                               @Nullable Consumer<String> stdOutConsumer) throws SystemCommandFailedException {
        if (SystemUtils.IS_OS_WINDOWS) {
            PowerShellResponse response = executePowerShellCommand(command + " " + params);
            if (stdOutConsumer != null) {
                stdOutConsumer.accept(response.getCommandOutput());
            }
        } else {
            throw new UnsupportedOperationException("OS not supported");
        }
    }

    /////////////////////////
    // Windows OS specific //
    /////////////////////////
    private static <T> PowerShellResponse executePowerShellScript(
            @NotNull String script, @NotNull String params) throws SystemCommandFailedException {
        assert SystemUtils.IS_OS_WINDOWS : "Tried to execute PowerShell command on non-Windows OS";
        try (PowerShell powerShell = PowerShell.openSession();
                BufferedReader scriptReader = new BufferedReader(new InputStreamReader(
                        SystemCommandUtility.class.getResourceAsStream(script)))) {
            return powerShell.executeScript(scriptReader, params);
        } catch (PowerShellNotAvailableException | IOException ex) {
            throw new SystemCommandFailedException(ex);
        }
    }

    private static PowerShellResponse executePowerShellCommand(@NotNull String command)
            throws SystemCommandFailedException {
        assert SystemUtils.IS_OS_WINDOWS : "Tried to execute PowerShell command on non-Windows OS";
        try (PowerShell powerShell = PowerShell.openSession()) {
            return powerShell.executeCommand(command);
        } catch (PowerShellNotAvailableException ex) {
            throw new SystemCommandFailedException(ex);
        }
    }
}
