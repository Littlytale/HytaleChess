package com.richy.chessplugin;

import com.hypixel.hytale.component.AddReason;
import com.hypixel.hytale.component.Holder;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.math.vector.Vector2d;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.math.vector.Vector3f;
import com.hypixel.hytale.server.core.asset.type.model.config.Model;
import com.hypixel.hytale.server.core.asset.type.model.config.ModelAsset;
import com.hypixel.hytale.server.core.entity.Entity;
import com.hypixel.hytale.server.core.entity.UUIDComponent;
import com.hypixel.hytale.server.core.event.events.entity.EntityRemoveEvent;
import com.hypixel.hytale.server.core.modules.entity.EntityModule;
import com.hypixel.hytale.server.core.modules.entity.component.*;
import com.hypixel.hytale.server.core.modules.entity.tracker.NetworkId;
import com.hypixel.hytale.server.core.modules.interaction.Interactions;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import javax.annotation.Nonnull;
import java.util.Map;

public class Chessboard {

    // Chess edge positions
    public Vector3d pos1;
    public Vector3d pos2;

    public World world;
    public Store<EntityStore> store;
    public PlayerRef playerRef;

    // Chess pieces and the positons
    Map<Vector2d, Ref<EntityStore>> chessboardOccupancy;

    // Even = White turn; Odd = Black turn
    public int turns = 0;

    public Chessboard(World world, Vector3d pos1, Vector3d pos2, @Nonnull PlayerRef playerRef) {
        this.world = world;
        this.store = world.getEntityStore().getStore();
        this.playerRef = playerRef;
        this.pos1 = pos1;
        this.pos2 = pos2;

        setup();
    }

    public void setup(){
        // Spawning Mannequin pieces
        Holder<EntityStore> holder = EntityStore.REGISTRY.newHolder();
        ModelAsset modelAsset = ModelAsset.getAssetMap().getAsset("Pig");
        Model model = Model.createScaledModel(modelAsset, 1.0f);
        TransformComponent transform = store.getComponent(playerRef.getReference(), EntityModule.get().getTransformComponentType());

        // Add all components to new Entity
        holder.addComponent(TransformComponent.getComponentType(), new TransformComponent(Vector3d.add(pos1, new Vector3d(0, 1, 0)), new Vector3f(0, 0, 0)));
        holder.addComponent(PersistentModel.getComponentType(), new PersistentModel(model.toReference()));
        holder.addComponent(ModelComponent.getComponentType(), new ModelComponent(model));
        holder.addComponent(BoundingBox.getComponentType(), new BoundingBox(model.getBoundingBox()));
        holder.addComponent(NetworkId.getComponentType(), new NetworkId(store.getExternalData().takeNextNetworkId()));
        holder.addComponent(Interactions.getComponentType(), new Interactions()); // you need to add interactions here if you want your entity to be interactable

        holder.ensureComponent(UUIDComponent.getComponentType());
        holder.ensureComponent(Interactable.getComponentType()); // if you want your entity to be interactable

        // Finally Spawning the Entity
        world.execute(() -> {
            store.addEntity(holder, AddReason.SPAWN);
        });
    }

    public void clear(){

    }

    public void reset(){
        clear();
        setup();
    }
}
