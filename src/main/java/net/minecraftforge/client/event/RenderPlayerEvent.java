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
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.LogicalSide;

/**
 * <p>Fired when a player is being rendered. <br/>
 * See the two subclasses for listening for before and after rendering. </p>
 *
 * @see RenderPlayerEvent.Pre
 * @see RenderPlayerEvent.Post
 * @see PlayerRenderer
 */
public abstract class RenderPlayerEvent extends PlayerEvent
{
    private final PlayerRenderer renderer;
    private final float partialRenderTick;
    private final MatrixStack stack;
    private final IRenderTypeBuffer buffers;
    private final int light;

    public RenderPlayerEvent(PlayerEntity player, PlayerRenderer renderer, float partialRenderTick, MatrixStack stack, IRenderTypeBuffer buffers, int light)
    {
        super(player);
        this.renderer = renderer;
        this.partialRenderTick = partialRenderTick;
        this.stack = stack;
        this.buffers = buffers;
        this.light = light;
    }

    /**
     * @return the renderer for the player entity
     */
    public PlayerRenderer getRenderer() { return renderer; }

    /**
     * @return the amount of partial ticks
     */
    public float getPartialRenderTick() { return partialRenderTick; }

    /**
     * @return the matrix stack used for rendering
     */
    public MatrixStack getMatrixStack() { return stack; }

    /**
     * @return the rendering buffers
     */
    public IRenderTypeBuffer getBuffers() { return buffers; }

    /**
     * @return the amount of packed (sky and block) light for rendering
     */
    public int getLight() { return light; }

    /**
     * <p>Fired <b>before</b> the player is rendered. <br/>
     * This can be used for rendering additional effects or suppressing rendering. </p>
     *
     * <p>This event is {@linkplain Cancelable cancelable}, and does not {@linkplain HasResult have a result}. <br/>
     * If this event is cancelled, then the player will not be rendered and the corresponding
     * {@link RenderPlayerEvent.Post} will not be fired. </p>
     *
     * <p>This event is fired on the {@linkplain MinecraftForge#EVENT_BUS main Forge event bus},
     * only on the {@linkplain LogicalSide#CLIENT logical client}. </p>
     */
    @Cancelable
    public static class Pre extends RenderPlayerEvent
    {
        public Pre(PlayerEntity player, PlayerRenderer renderer, float tick, MatrixStack stack, IRenderTypeBuffer buffers, int light) {
            super(player, renderer, tick, stack, buffers, light);
        }
    }

    /**
     * <p>Fired <b>after</b> the player is rendered, if the corresponding {@link RenderPlayerEvent.Pre} is not cancelled. </p>
     *
     * <p>This event is not {@linkplain Cancelable cancelable}, and does not {@linkplain HasResult have a result}. </p>
     *
     * <p>This event is fired on the {@linkplain MinecraftForge#EVENT_BUS main Forge event bus},
     * only on the {@linkplain LogicalSide#CLIENT logical client}. </p>
     */
    public static class Post extends RenderPlayerEvent
    {
        public Post(PlayerEntity player, PlayerRenderer renderer, float tick, MatrixStack stack, IRenderTypeBuffer buffers, int light) {
            super(player, renderer, tick, stack, buffers, light);
        }
    }
    
}
