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
import net.minecraftforge.eventbus.api.Event;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.fml.LogicalSide;

/**
 * <p>Fired when a {@link LivingEntity} is rendered. <br/>
 * See the two subclasses to listen for before and after rendering. </p>
 *
 * @param <T> the living entity that is being rendered
 * @param <M> the model for the living entity
 *
 * @see RenderLivingEvent.Pre
 * @see RenderLivingEvent.Post
 * @see RenderPlayerEvent
 * @see LivingRenderer
 */
public abstract class RenderLivingEvent<T extends LivingEntity, M extends EntityModel<T>> extends Event
{

    private final LivingEntity entity;
    private final LivingRenderer<T, M> renderer;
    private final float partialRenderTick;
    private final MatrixStack matrixStack;
    private final IRenderTypeBuffer buffers;
    private final int light;

    public RenderLivingEvent(LivingEntity entity, LivingRenderer<T, M> renderer, float partialRenderTick, MatrixStack matrixStack,
                             IRenderTypeBuffer buffers, int light)
    {
        this.entity = entity;
        this.renderer = renderer;
        this.partialRenderTick = partialRenderTick;
        this.matrixStack = matrixStack;
        this.buffers = buffers;
        this.light = light;
    }

    /**
     * @return the living entity being rendered
     */
    public LivingEntity getEntity() { return entity; }

    /**
     * @return the renderer for the living entity
     */
    public LivingRenderer<T, M> getRenderer() { return renderer; }

    /**
     * @return the amount of partial ticks
     */
    public float getPartialRenderTick() { return partialRenderTick; }

    /**
     * @return the matrix stack used for rendering
     */
    public MatrixStack getMatrixStack() { return matrixStack; }

    /**
     * @return the rendering buffers
     */
    public IRenderTypeBuffer getBuffers() { return buffers; }

    /**
     * @return the amount of packed (sky and block) light for rendering
     */
    public int getLight() { return light; }

    /**
     * <p>Fired <b>before</b> an entity is rendered. <br/>
     * This can be used to render additional effects or suppress rendering. </p>
     *
     * <p>This event is {@linkplain Cancelable cancelable}, and does not {@linkplain HasResult have a result}. <br/>
     * If this event is cancelled, then the entity will not be rendered and the corresponding
     * {@link RenderLivingEvent.Post} will not be fired. </p>
     *
     * <p>This event is fired on the {@linkplain MinecraftForge#EVENT_BUS main Forge event bus},
     * only on the {@linkplain LogicalSide#CLIENT logical client}. </p>
     *
     * @param <T> the living entity that is being rendered
     * @param <M> the model for the living entity
     */
    @Cancelable
    public static class Pre<T extends LivingEntity, M extends EntityModel<T>> extends RenderLivingEvent<T, M>
    {
        public Pre(LivingEntity entity, LivingRenderer<T, M> renderer, float partialRenderTick, MatrixStack matrixStack, IRenderTypeBuffer buffers, int light) {
            super(entity, renderer, partialRenderTick, matrixStack, buffers, light);
        }
    }

    /**
     * <p>Fired <b>after</b> an entity is rendered, if the corresponding {@link RenderLivingEvent.Post} is not cancelled. </p>
     *
     * <p>This event is not {@linkplain Cancelable cancelable}, and does not {@linkplain HasResult have a result}. </p>
     *
     * <p>This event is fired on the {@linkplain MinecraftForge#EVENT_BUS main Forge event bus},
     * only on the {@linkplain LogicalSide#CLIENT logical client}. </p>
     *
     * @param <T> the living entity that was rendered
     * @param <M> the model for the living entity
     */
    public static class Post<T extends LivingEntity, M extends EntityModel<T>> extends RenderLivingEvent<T, M>
    {
        public Post(LivingEntity entity, LivingRenderer<T, M> renderer, float partialRenderTick, MatrixStack matrixStack, IRenderTypeBuffer buffers, int light) {
            super(entity, renderer, partialRenderTick, matrixStack, buffers, light);
        }
    }
}
