package com.richy.chessplugin;

import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.protocol.BlockPosition;
import com.hypixel.hytale.protocol.InteractionType;
import com.hypixel.hytale.server.core.entity.InteractionContext;
import com.hypixel.hytale.server.core.entity.UUIDComponent;
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.npc.entities.NPCEntity;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Nonnull;


class ChessPieceControl extends SimpleInstantInteraction {

    public static final BuilderCodec<ChessPieceControl> CODEC =
            BuilderCodec.builder(
                    ChessPieceControl.class,
                    ChessPieceControl::new,
                    SimpleInstantInteraction.CODEC).build();

    private static final Map<UUID, Ref<EntityStore>> selectedEntity = new ConcurrentHashMap<>();


    @Override
    protected void firstRun(@Nonnull InteractionType interactionType,
                            @Nonnull InteractionContext interactionContext,
                            @Nonnull CooldownHandler cooldownHandler) {

        // Holt sich Spieler und die UUID vom Spieler
        Ref<EntityStore> playerRef = interactionContext.getOwningEntity();
        UUID playerUUID = playerRef.getStore().getComponent(playerRef, UUIDComponent.getComponentType()).getUuid();

        // Holt sich den TargetEntity ...
        Ref<EntityStore> targetEntityRef = interactionContext.getTargetEntity();
        // ... oder den TargetBlock
        BlockPosition targetBlockPos = interactionContext.getTargetBlock();

        // Speichert Entity
        if (targetEntityRef != null ) {
            if (interactionType.getValue() == InteractionType.Secondary.getValue()) {

                selectedEntity.put(playerUUID, targetEntityRef);

                return;
            } else if (interactionType.getValue() == InteractionType.Primary.getValue() && targetEntityRef != selectedEntity.get(playerUUID)) {
                Ref<EntityStore> currentEntity = selectedEntity.get(playerUUID);
                if (!currentEntity.isValid()) {
                    selectedEntity.remove(playerUUID);
                    return;
                }
                NPCEntity entityComponent = selectedEntity.get(playerUUID).getStore().getComponent(currentEntity, NPCEntity.getComponentType());

                entityComponent.getRole().setMarkedTarget("LockedTarget", targetEntityRef);
                entityComponent.getRole().getStateSupport().setState(currentEntity, "Combat", null, currentEntity.getStore());

                Vector3d targetEntityPos = targetEntityRef.getStore().getComponent(targetEntityRef,  TransformComponent.getComponentType()).getPosition();
                targetEntityPos = new Vector3d(Math.ceil(targetEntityPos.x) - 0.5, targetEntityPos.y, Math.ceil(targetEntityPos.z) - 0.5);
                entityComponent.setLeashPoint(targetEntityPos);
            }
        } else if (targetBlockPos != null && selectedEntity.get(playerUUID) != null) {
            Ref<EntityStore> currentEntity = selectedEntity.get(playerUUID);
            if (!currentEntity.isValid()) {
                selectedEntity.remove(playerUUID);
                return;
            }
            NPCEntity entityComponent = selectedEntity.get(playerUUID).getStore().getComponent(currentEntity, NPCEntity.getComponentType());

            entityComponent.setLeashPoint(new Vector3d(targetBlockPos.x + 0.5, targetBlockPos.y, targetBlockPos.z + 0.5));

            entityComponent.getRole().getStateSupport().setState(selectedEntity.get(playerUUID), "ReturnHome", null, selectedEntity.get(playerUUID).getStore());

            //selectedEntity.remove(playerUUID);

            return;
        }
    }
}
