package com.richy.chessplugin;

import com.hypixel.hytale.server.core.modules.interaction.interaction.config.Interaction;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import java.util.logging.Level;

import javax.annotation.Nonnull;


public class ChessPlugin extends JavaPlugin {

    public ChessPlugin(@Nonnull JavaPluginInit init) {
        super(init);
    }

    @Override
    protected void setup() {
        getLogger().at(Level.INFO).log("ExamplePlugin setup...");

        this.getCodecRegistry(Interaction.CODEC)
                .register("ChessPieceControl",
                        ChessPieceControl.class,
                        ChessPieceControl.CODEC);

        getCodecRegistry(Interaction.CODEC).register(
                "chessplugin:lockedtarget_damage",
                LockedTargetDamageInteraction.class,
                LockedTargetDamageInteraction.CODEC
        );
    }

    @Override
    protected void shutdown() {
        getLogger().at(Level.INFO).log("Shutdown ExamplePlugin...");
    }
}