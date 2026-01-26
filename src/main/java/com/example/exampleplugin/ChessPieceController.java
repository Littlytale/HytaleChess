package com.example.exampleplugin;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.math.vector.Vector3i;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractTargetEntityCommand;
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import it.unimi.dsi.fastutil.objects.ObjectList;
import org.jspecify.annotations.NonNull;

public class ChessPieceController extends AbstractTargetEntityCommand {
    private final OptionalArg<Vector3i> targetPos;

    public ChessPieceController() {
        super("cpc", "Set a new position target for a NPC");

        this.targetPos = this.withOptionalArg("pos", "Ziel Position", ArgTypes.VECTOR3I);
    }

    @Override
    protected void execute(
            @NonNull CommandContext commandContext,
            @NonNull ObjectList<Ref<EntityStore>> objectList,
            @NonNull World world,
            @NonNull Store<EntityStore> store) {

        Vector3d targetPos;

        if (commandContext.isPlayer()) {
            Ref<EntityStore> playerRef = commandContext.senderAsPlayerRef();
            TransformComponent playerTransform = store.getComponent(playerRef, TransformComponent.getComponentType());
            targetPos = playerTransform.getPosition();
        } else {
            targetPos = this.targetPos.get(commandContext).toVector3d();
        }


        TransformComponent transform = store.getComponent(objectList.getFirst(), TransformComponent.getComponentType());
        transform.setPosition(targetPos);
    }
}
