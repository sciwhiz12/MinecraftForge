/*
 * Minecraft Forge
 * Copyright (c) 2016-2020.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation version 2.1
 * of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package net.minecraftforge.client.event;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.LogicalSide;

/**
 * <p>Fired after the FOV (field of vision) modifier for the player is calculated. <br/>
 * This can be used to modify the FOV before the e.g. FOV settings are applied. </p>
 *
 * <p>This event is not {@linkplain Cancelable cancelable}, and does not {@linkplain HasResult have a result}. </p>
 *
 * <p>This event is fired on the {@linkplain MinecraftForge#EVENT_BUS main Forge event bus},
 * only on the {@linkplain LogicalSide#CLIENT logical client}. </p>
 *
 * @see ForgeHooksClient#getOffsetFOV(PlayerEntity, float)
 * @see EntityViewRenderEvent.FOVModifier
 * @author MachineMuse (Claire Semple)
 * @since 6:07 PM, 9/5/13
 */
public class FOVUpdateEvent extends Event
{
    private final PlayerEntity entity;
    private final float fov;
    private float newfov;

    public FOVUpdateEvent(PlayerEntity entity, float fov)
    {
        this.entity = entity;
        this.fov = fov;
        this.setNewfov(fov);
    }

    /**
     * @return the player entity affected by this event
     */
    public PlayerEntity getEntity()
    {
        return entity;
    }

    /**
     * @return the original FOV (field of vision) of the player
     */
    public float getFov()
    {
        return fov;
    }

    /**
     * @return the current FOV (field of vision) of the player
     */
    public float getNewfov()
    {
        return newfov;
    }

    /**
     * Sets the new FOV (field of vision) of the player.
     *
     * @param newfov the new FOV (field of vision)
     */
    public void setNewfov(float newfov)
    {
        this.newfov = newfov;
    }
}
