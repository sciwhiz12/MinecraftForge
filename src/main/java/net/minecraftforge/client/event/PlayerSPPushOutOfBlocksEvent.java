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
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.fml.LogicalSide;

/**
 * <p>Fired before a player is checked for being inside a block, to push out if the block is solid. </p>
 *
 * <p>This event is {@linkplain Cancelable cancelable}, and does not {@linkplain HasResult have a result}. <br/>
 * If this event is cancelled, then the player will not be pushed. </p>
 *
 * <p>This event is fired on the {@linkplain MinecraftForge#EVENT_BUS main Forge event bus},
 * only on the {@linkplain LogicalSide#CLIENT logical client}. </p>
 */
@Cancelable
public class PlayerSPPushOutOfBlocksEvent extends PlayerEvent
{
    private double minY;

    public PlayerSPPushOutOfBlocksEvent(PlayerEntity player)
    {
        super(player);
        this.minY = player.getPosY() + 0.5D;
    }

    /**
     * Sets the minimum Y value to check for the block that can push the player.
     *
     * @param value the new minimum Y value
     */
    public void setMinY(double value) {
        this.minY = value;
    }

    /**
     * @return the minimum Y value to check for a block; defaults to player's Y postition + {@code 0.5D}
     */
    public double getMinY() {
        return this.minY;
    }
}
