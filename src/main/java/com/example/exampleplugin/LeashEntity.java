package com.example.exampleplugin;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractTargetEntityCommand;
import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent;
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.npc.entities.NPCEntity;
import it.unimi.dsi.fastutil.objects.ObjectList;
import org.jspecify.annotations.NonNull;

public class LeashEntity extends AbstractTargetEntityCommand {

    public LeashEntity(){
        super("leashentity", "Leash an entity to your current positon");

        addAliases("le");
    }

    @Override
    protected void execute(@NonNull CommandContext ctx, @NonNull ObjectList<Ref<EntityStore>> refList, @NonNull World world, @NonNull Store<EntityStore> store) {
        Vector3d playerPos = store.getComponent(ctx.senderAsPlayerRef(), TransformComponent.getComponentType()).getPosition();

        NPCEntity npcComponent = store.getComponent(refList.getFirst(), NPCEntity.getComponentType());

        ctx.sendMessage(Message.raw(npcComponent.getLeashPoint().toString()));

        npcComponent.setLeashPoint(playerPos);

        ctx.sendMessage(Message.raw("Das Entity " + store.getComponent(refList.getFirst(), ModelComponent.getComponentType()).getModel().getModelAssetId() + " wird zu dir geführt!"));

        ctx.sendMessage(Message.raw(npcComponent.getLeashPoint().toString()));
    }
}
