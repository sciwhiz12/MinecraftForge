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

import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.LogicalSide;

import javax.annotation.Nullable;
import java.util.UUID;

/**
 * <p>Fired on the client when a chat message is received. <br/>
 * This can be used for filtering and detecting messages with specific words or phrases, and suppressing them. </p>
 *
 * <p>This event is {@linkplain Cancelable cancelable}, and does not {@linkplain HasResult have a result}. <br/>
 * If the event is cancelled, the message is not displayed in the chat message window. </p>
 *
 * <p>This event is fired on the {@linkplain MinecraftForge#EVENT_BUS main Forge event bus},
 * only on the {@linkplain LogicalSide#CLIENT logical client}. </p>
 *
 * @see ChatType
 * @see ForgeEventFactory#onClientChat(ChatType, ITextComponent, UUID)
 */
@Cancelable
public class ClientChatReceivedEvent extends Event
{
    private ITextComponent message;
    private final ChatType type;
    @Nullable
    private final UUID senderUUID;

    public ClientChatReceivedEvent(ChatType type, ITextComponent message, @Nullable UUID senderUUID)
    {
        this.type = type;
        this.message = message;
        this.senderUUID = senderUUID;
    }

    /**
     * @return the message that will be displayed in the chat message window, if the event is not cancelled
     */
    public ITextComponent getMessage()
    {
        return message;
    }

    /**
     * Sets the new message to be displayed in the chat message window, if the event is not cancelled.
     *
     * @param message the new message to be sent
     */
    public void setMessage(ITextComponent message)
    {
        this.message = message;
    }

    /**
     * @return the type of the message
     */
    public ChatType getType()
    {
        return type;
    }

    /**
     * @return the UUID of the sender, or {@code null}
     */
    @Nullable
    public UUID getSenderUUID()
    {
        return senderUUID;
    }
}
