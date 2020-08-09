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
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.LogicalSide;

/**
 * <p>Fired before an entity renderer renders the nameplate of an entity. </p>
 *
 * <p>This event is not {@linkplain Cancelable cancelable}, and  {@linkplain HasResult has a result}. <br/>
 * <ul>
 *     <li><b>ALLOW</b> - the nameplate will be forcibly rendered</li>
 *     <li><b>DEFAULT</b> - the vanilla logic will be used</li>
 *     <li><b>DENY</b> - the nameplate will not be rendered</li>
 * </ul>
 * </p>
 *
 * <p>This event is fired on the {@linkplain MinecraftForge#EVENT_BUS main Forge event bus},
 * only on the {@linkplain LogicalSide#CLIENT logical client}. </p>
 *
 * @see EntityRenderer
 */
@Event.HasResult
public class RenderNameplateEvent extends EntityEvent
{

    private ITextComponent nameplateContent;
    private final ITextComponent originalContent;
    private final EntityRenderer<?> entityRenderer;
    private final MatrixStack matrixStack;
    private final IRenderTypeBuffer renderTypeBuffer;
    private final int packedLight;

    public RenderNameplateEvent(Entity entity, ITextComponent content, EntityRenderer<?> entityRenderer, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int packedLight)
    {
        super(entity);
        this.originalContent = content;
        this.setContent(this.originalContent);
        this.entityRenderer = entityRenderer;
        this.matrixStack = matrixStack;
        this.renderTypeBuffer = renderTypeBuffer;
        this.packedLight = packedLight;
    }

    /**
     * Sets the new text on the nameplate
     *
     * @param contents the new text
     */
    public void setContent(ITextComponent contents)
    {
        this.nameplateContent = contents;
    }

    /**
     * @return the text on the nameplate that will be rendered, if the event is not {@link Result#DENY DENIED}
     */
    public ITextComponent getContent()
    {
        return this.nameplateContent;
    }

    /**
     * @return the original text on the nameplate
     */
    public ITextComponent getOriginalContent()
    {
        return this.originalContent;
    }

    /**
     * @return the entity renderer rendering the nameplate
     */
    public EntityRenderer<?> getEntityRenderer()
    {
        return this.entityRenderer;
    }

    /**
     * @return the matrix stack used for rendering
     */
    public MatrixStack getMatrixStack()
    {
        return this.matrixStack;
    }

    /**
     * @return the rendering buffers
     */
    public IRenderTypeBuffer getRenderTypeBuffer()
    {
        return this.renderTypeBuffer;
    }

    /**
     * @return the amount of packed (sky and block) light for rendering
     */
    public int getPackedLight()
    {
        return this.packedLight;
    }
}
