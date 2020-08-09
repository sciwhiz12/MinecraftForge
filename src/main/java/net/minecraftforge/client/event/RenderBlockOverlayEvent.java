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

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.block.BlockState;
import net.minecraft.block.FireBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.LogicalSide;

/**
 * <p>Fired before a block texture will be overlaid on the player's view. </p>
 *
 * <p>This event is {@linkplain Cancelable cancelable}, and does not {@linkplain HasResult have a result}. <br/>
 * If this event is cancelled, then the overlay will not be rendered. </p>
 *
 * <p>This event is fired on the {@linkplain MinecraftForge#EVENT_BUS main Forge event bus},
 * only on the {@linkplain LogicalSide#CLIENT logical client}. </p>
 *
 * @see ForgeEventFactory#renderBlockOverlay(PlayerEntity, MatrixStack, OverlayType, BlockState, BlockPos)
 */
@Cancelable
public class RenderBlockOverlayEvent extends Event
{

    public static enum OverlayType {
        /**
         * The type of the overlay when the player is burning / on fire.
         */
        FIRE,
        /**
         * The type of overlay when the player is suffocating inside a solid block.
         */
        BLOCK,
        /**
         * The type of overlay when the player is underwater.
         */
        WATER
    }
    
    private final PlayerEntity player;
    private final MatrixStack mat;
    private final OverlayType overlayType;
    private final BlockState blockForOverlay;
    private final BlockPos blockPos;
    
    public RenderBlockOverlayEvent(PlayerEntity player, MatrixStack mat, OverlayType type, BlockState block, BlockPos blockPos)
    {
        this.player = player;
        this.mat = mat;
        this.overlayType = type;
        this.blockForOverlay = block;
        this.blockPos = blockPos;
        
    }

    /**
     * @return the player which the overlay will apply to
     */
    public PlayerEntity getPlayer() { return player; }

    /**
     * @return the matrix stack used for rendering
     */
    public MatrixStack getMatrixStack() { return mat; }

    /**
     * @return the type of the overlay
     */
    public OverlayType getOverlayType() { return overlayType; }

    /**
     * @return the block from which the overlay is gotten from
     */
    public BlockState getBlockForOverlay() { return blockForOverlay; }

    /**
     * @return the position of the block from which the overlay is gotten from
     */
    public BlockPos getBlockPos() { return blockPos; }
}
