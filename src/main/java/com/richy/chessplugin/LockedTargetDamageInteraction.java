package com.richy.chessplugin;
import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.protocol.InteractionConfiguration;
import com.hypixel.hytale.protocol.InteractionState;
import com.hypixel.hytale.protocol.InteractionType;
import com.hypixel.hytale.server.core.entity.InteractionContext;
import com.hypixel.hytale.server.core.modules.entity.damage.Damage;
import com.hypixel.hytale.server.core.modules.entity.damage.DamageCause;
import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.Interaction;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.npc.entities.NPCEntity;

import javax.annotation.Nonnull;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.hypixel.hytale.logger.HytaleLogger.getLogger;

public class LockedTargetDamageInteraction extends SimpleInstantInteraction{

    private float baseDamage = 10.0f;

    public float getBaseDamage(){return baseDamage;}
    public void setBaseDamage(float baseDamage) {this.baseDamage = baseDamage;}

    public static final BuilderCodec<LockedTargetDamageInteraction> CODEC = BuilderCodec.builder(
                    LockedTargetDamageInteraction.class,
                    LockedTargetDamageInteraction::new,
                    SimpleInstantInteraction.CODEC
            )
            .append(
                    new KeyedCodec<>("BaseDamage", Codec.FLOAT),
                    (self, value, extra) -> self.baseDamage = value,
                    (self, extra) -> self.baseDamage
            )
            .add()
            .build();

    @Override
    protected void firstRun(
            @Nonnull InteractionType interactionType,
            @Nonnull InteractionContext ctx,
            @Nonnull CooldownHandler cooldownHandler
    ){

        getLogger().at(Level.INFO).log("Entity attack an entity!");

        CommandBuffer<EntityStore> commandBuffer = ctx.getCommandBuffer();

        Ref<EntityStore> attacker = ctx.getEntity();

        Ref<EntityStore> target = attacker.getStore().getComponent(attacker, NPCEntity.getComponentType()).getRole().getMarkedEntitySupport().getMarkedEntityRef("LockedTarget");
        if (target == null || !target.isValid()) {
            ctx.getState().state = InteractionState.Failed;
            return;
        }

        Damage.EntitySource source = new Damage.EntitySource(attacker);
        DamageCause cause = DamageCause.getAssetMap().getAsset("Physical");
        Damage damage = new Damage(source, cause, this.baseDamage);

        commandBuffer.invoke(target, damage);

        ctx.getState().state = InteractionState.Finished;
    }
}
