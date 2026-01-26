package com.example.exampleplugin;

import com.example.exampleplugin.commands.ExampleCommand;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;

import javax.annotation.Nonnull;
import java.util.logging.Level;

public class ExamplePlugin extends JavaPlugin {

    public ExamplePlugin(@Nonnull JavaPluginInit init) {
        super(init);
    }

    @Override
    protected void setup() {
        getLogger().at(Level.INFO).log("ExamplePlugin setup...");

        getCommandRegistry().registerCommand(new ExampleCommand());
        getCommandRegistry().registerCommand(new ChessPieceController());
        getCommandRegistry().registerCommand(new LeashEntity());
    }

    @Override
    protected void shutdown() {
        getLogger().at(Level.INFO).log("Shutdown ExamplePlugin...");
    }

}