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

import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.LogicalSide;

/**
 * <p>Fired after all world rendering. <br/>
 * This can be used for custom rendering outside of e.g. a tile entity or entity renderer. </p>
 *
 * <p>This event is not {@linkplain Cancelable cancelable}, and does not {@linkplain HasResult have a result}. </p>
 *
 * <p>This event is fired on the {@linkplain MinecraftForge#EVENT_BUS main Forge event bus},
 * only on the {@linkplain LogicalSide#CLIENT logical client}. </p>
 *
 * @see ForgeHooksClient#dispatchRenderLast(WorldRenderer, MatrixStack, float, Matrix4f, long)
 * @see GameRenderer
 * @see WorldRenderer
 */
public class RenderWorldLastEvent extends Event
{
    private final WorldRenderer context;
    private final MatrixStack mat;
    private final float partialTicks;
    private final Matrix4f projectionMatrix;
    private final long finishTimeNano;

    public RenderWorldLastEvent(WorldRenderer context, MatrixStack mat, float partialTicks, Matrix4f projectionMatrix, long finishTimeNano)
    {
        this.context = context;
        this.mat = mat;
        this.partialTicks = partialTicks;
        this.projectionMatrix = projectionMatrix;
        this.finishTimeNano = finishTimeNano;
    }

    /**
     * @return the world renderer
     */
    public WorldRenderer getContext()
    {
        return context;
    }

    /**
     * @return the matrix stack used for rendering
     */
    public MatrixStack getMatrixStack()
    {
        return mat;
    }

    /**
     * @return the amount of partial ticks
     */
    public float getPartialTicks()
    {
        return partialTicks;
    }

    /**
     * @return the projection matrix
     */
    public Matrix4f getProjectionMatrix()
    {
        return projectionMatrix;
    }

    /**
     *
     * @return the time when rendering started, in nanoseconds
     */
    public long getFinishTimeNano()
    {
        return finishTimeNano;
    }
}
