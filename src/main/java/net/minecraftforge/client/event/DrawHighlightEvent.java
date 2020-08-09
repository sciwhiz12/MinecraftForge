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
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraftforge.fml.LogicalSide;

/**
 * <p>Fired before a selection highlight is rendered. <br/>
 * See the two subclasses to listen for blocks or entities. </p>
 *
 * @see DrawHighlightEvent.HighlightBlock
 * @see DrawHighlightEvent.HighlightEntity
 * @see ForgeHooksClient#onDrawBlockHighlight(WorldRenderer, ActiveRenderInfo, RayTraceResult, float, MatrixStack, IRenderTypeBuffer)
 */
@Cancelable
public class DrawHighlightEvent extends Event
{
    private final WorldRenderer context;
    private final ActiveRenderInfo info;
    private final RayTraceResult target;
    private final float partialTicks;
    private final MatrixStack matrix;
    private final IRenderTypeBuffer buffers;

    public DrawHighlightEvent(WorldRenderer context, ActiveRenderInfo info, RayTraceResult target, float partialTicks, MatrixStack matrix, IRenderTypeBuffer buffers)
    {
        this.context = context;
        this.info = info;
        this.target = target;
        this.partialTicks= partialTicks;
        this.matrix = matrix;
        this.buffers = buffers;
    }

    /**
     * @return the world renderer
     */
    public WorldRenderer getContext() { return context; }

    /**
     * @return the active render information
     */
    public ActiveRenderInfo getInfo() { return info; }

    /**
     * @return the target of the highlight
     */
    public RayTraceResult getTarget() { return target; }

    /**
     * @return the amount of partial ticks
     */
    public float getPartialTicks() { return partialTicks; }

    /**
     * @return the matrix stack used for rendering
     */
    public MatrixStack getMatrix() { return matrix; }

    /**
     * @return the rendering buffers
     */
    public IRenderTypeBuffer getBuffers() { return buffers; }

    /**
     * <p>Fired before a block's selection highlight is rendered. </p>
     *
     * <p>This event is {@linkplain Cancelable cancelable}, and does not {@linkplain HasResult have a result}. <br/>
     * If the event is cancelled, then the selection highlight will not be rendered. </p>
     *
     * <p>This event is fired on the {@linkplain MinecraftForge#EVENT_BUS main Forge event bus},
     * only on the {@linkplain LogicalSide#CLIENT logical client}. </p>
     */
    @Cancelable
    public static class HighlightBlock extends DrawHighlightEvent
    {
        public HighlightBlock(WorldRenderer context, ActiveRenderInfo info, RayTraceResult target, float partialTicks, MatrixStack matrix, IRenderTypeBuffer buffers)
        {
            super(context, info, target, partialTicks, matrix, buffers);
        }

        /**
         * @return the block target of the highlight
         */
        @Override
        public BlockRayTraceResult getTarget()
        {
            return (BlockRayTraceResult) super.target;
        }
    }

    /**
     * <p>Fired before a block's selection highlight is rendered. </p>
     *
     * <p>This event is {@linkplain Cancelable cancelable}, and does not {@linkplain HasResult have a result}. <br/>
     * Cancelling this event has no effect. </p>
     *
     * TODO: this event cannot be fired because of where the hook is called; remove this event or move the hook
     *
     * <p>This event is fired on the {@linkplain MinecraftForge#EVENT_BUS main Forge event bus},
     * only on the {@linkplain LogicalSide#CLIENT logical client}. </p>
     */
    @Cancelable
    public static class HighlightEntity extends DrawHighlightEvent
    {
        public HighlightEntity(WorldRenderer context, ActiveRenderInfo info, RayTraceResult target, float partialTicks, MatrixStack matrix, IRenderTypeBuffer buffers)
        {
            super(context, info, target, partialTicks, matrix, buffers);
        }

        /**
         * @return the entity target of the highlight
         */
        @Override
        public EntityRayTraceResult getTarget()
        {
            return (EntityRayTraceResult) super.target;
        }
    }
}
