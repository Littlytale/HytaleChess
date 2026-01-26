package com.example.exampleplugin.commands;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.protocol.ChangeVelocityType;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.arguments.system.DefaultArg;
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractTargetPlayerCommand;
import com.hypixel.hytale.server.core.modules.physics.component.Velocity;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;


public class ExampleCommand extends AbstractTargetPlayerCommand {
    private final DefaultArg<Float> jumpHighArg;


    public ExampleCommand() {
        super("test", "Testing current feature!");

        this.jumpHighArg = this.withDefaultArg("high", "Jump high", ArgTypes.FLOAT, (float)20, "Desc of Default: 20");
    }

    @Override
    protected void execute(@NonNull CommandContext ctx, @Nullable Ref<EntityStore> ref2, @NonNull Ref<EntityStore> ref, @NonNull PlayerRef playerRef, @NonNull World world, @NonNull Store<EntityStore> store) {
        Velocity velocity = store.getComponent(ref, Velocity.getComponentType());
        velocity.addInstruction(new Vector3d(0, jumpHighArg.get(ctx), 0), null, ChangeVelocityType.Set);
        ctx.sendMessage(Message.raw(playerRef.getUsername() + " wurde hoch katapultiert!"));
    }
}
