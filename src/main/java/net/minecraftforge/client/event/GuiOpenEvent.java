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

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;
import net.minecraft.client.gui.screen.Screen;
import net.minecraftforge.fml.LogicalSide;

/**
 * <p>Fired before any {@link Screen} will be opened, for changing it or preventing it from being opened.</p>
 *
 * <p>This event is {@linkplain Cancelable cancelable}, and does not {@linkplain HasResult have a result}. <br/>
 * If this event is cancelled, then the {@code Screen} shall be prevented from opening</p>
 *
 * <p>This event is fired on the {@linkplain MinecraftForge#EVENT_BUS main Forge event bus},
 * only on the {@linkplain LogicalSide#CLIENT logical client}. </p>
 *
 * @author jk-5
 */
@Cancelable
public class GuiOpenEvent extends Event
{
    private Screen gui;
    public GuiOpenEvent(Screen gui)
    {
        this.setGui(gui);
    }

    /**
     * @return the screen that will be opened, if the event is not cancelled
     */
    public Screen getGui()
    {
        return gui;
    }

    /**
     * Sets the new {@code Screen} to be opened, if the event is not cancelled.
     * @param gui the new screen
     */
    public void setGui(Screen gui)
    {
        this.gui = gui;
    }
}
