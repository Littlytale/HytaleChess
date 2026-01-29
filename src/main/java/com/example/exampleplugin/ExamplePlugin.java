package com.example.exampleplugin;

import com.example.exampleplugin.commands.ExampleCommand;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.Interaction;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import static com.hypixel.hytale.logger.HytaleLogger.getLogger;
import java.util.logging.Level;

import javax.annotation.Nonnull;


public class ExamplePlugin extends JavaPlugin {

    public ExamplePlugin(@Nonnull JavaPluginInit init) {
        super(init);
    }

    @Override
    protected void setup() {
        getLogger().at(Level.INFO).log("ExamplePlugin setup...");

        getCommandRegistry().registerCommand(new ExampleCommand());
        getCommandRegistry().registerCommand(new ChessPieceController());

        this.getCodecRegistry(Interaction.CODEC)
                .register("ChessPieceControl", ChessPieceControl.class, ChessPieceControl.CODEC);
    }

    @Override
    protected void shutdown() {
        getLogger().at(Level.INFO).log("Shutdown ExamplePlugin...");
    }
}