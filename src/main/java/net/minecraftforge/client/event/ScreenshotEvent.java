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

import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.util.ScreenShotHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.LogicalSide;

import java.io.File;
import java.io.IOException;

/**
 * <p>Fired when a screenshot is taken, but before it is written to disk. </p>
 *
 * <p>This event is {@linkplain Cancelable cancelable}, and does not {@linkplain HasResult have a result}. <br/>
 * If this event is cancelled, then the screenshot is not written to disk, and the message in the event will be posted
 * to the player's chat.</p>
 *
 * <p>This event is fired on the {@linkplain MinecraftForge#EVENT_BUS main Forge event bus},
 * only on the {@linkplain LogicalSide#CLIENT logical client}. </p>
 *
 * @see ScreenShotHelper
 */
@Cancelable
public class ScreenshotEvent extends Event
{

    public static final ITextComponent DEFAULT_CANCEL_REASON = new StringTextComponent("Screenshot canceled");

    private NativeImage image;
    private File screenshotFile;

    private ITextComponent resultMessage = null;

    public ScreenshotEvent(NativeImage image, File screenshotFile)
    {
        this.image = image;
        this.screenshotFile = screenshotFile;
        try {
            this.screenshotFile = screenshotFile.getCanonicalFile(); // FORGE: Fix errors on Windows with paths that include \.\
        } catch (IOException e) {}
    }

    /**
     * @return the image in-memory of the screenshot
     */
    public NativeImage getImage()
    {
        return image;
    }

    /**
     * @return the filepath where the screenshot will be saved to
     */
    public File getScreenshotFile()
    {
        return screenshotFile;
    }

    /**
     * Sets the new filepath where the screenshot will be saved to.
     *
     * @param screenshotFile the new filepath
     */
    public void setScreenshotFile(File screenshotFile)
    {
        this.screenshotFile = screenshotFile;
    }

    /**
     * @return the custom cancellation message, or {@code null}
     */
    public ITextComponent getResultMessage()
    {
        return resultMessage;
    }

    /**
     * <p>Sets the new custom cancellation message used to inform the player. <br/>
     * It may be {@code null}, in which case the {@link #DEFAULT_CANCEL_REASON default cancel reason} will be used. </p>
     *
     * @param resultMessage the new result message
     */
    public void setResultMessage(ITextComponent resultMessage)
    {
        this.resultMessage = resultMessage;
    }

    /**
     * <p>Returns the cancellation message to be used in informing the player. </p>
     *
     * <p>If there is no custom message given ({@link #getResultMessage()} is {@code null}), then
     * the messsage will be the {@link #DEFAULT_CANCEL_REASON default cancel reason message}</p>
     *
     * @return the cancel message for the player
     */
    public ITextComponent getCancelMessage()
    {
        return getResultMessage() != null ? getResultMessage() : DEFAULT_CANCEL_REASON;
    }

}
