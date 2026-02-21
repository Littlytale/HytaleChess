package com.richy.chessplugin;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.jspecify.annotations.NonNull;

public class ChessboardCommand extends AbstractPlayerCommand {

    public ChessboardCommand() {
        super("Chessboard", "Create a new Chessboard in your World.");
    }

    @Override
    protected void execute(
            @NonNull CommandContext commandContext,
            @NonNull Store<EntityStore> store,
            @NonNull Ref<EntityStore> ref,
            @NonNull PlayerRef playerRef,
            @NonNull World world) {

        Chessboard chessboard = new Chessboard(world, playerRef.getTransform().getPosition(), new Vector3d(0, 0 ,0), playerRef);
    }
}
