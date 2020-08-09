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
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.FogRenderer.FogType;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.LogicalSide;

/**
 * <p>Fired for hooking into the entity view rendering in {@link GameRenderer}. <br/>
 * This can be used for customizing the visual features visible to the player. <br/>
 * See the various subclasses for listening to different features. </p>
 *
 * <p>These events are fired on the {@linkplain MinecraftForge#EVENT_BUS main Forge event bus},
 * only on the {@linkplain LogicalSide#CLIENT logical client}. </p>
 *
 * @see EntityViewRenderEvent.FogEvent
 * @see EntityViewRenderEvent.FogDensity
 * @see EntityViewRenderEvent.RenderFogEvent
 * @see EntityViewRenderEvent.FogColors
 * @see EntityViewRenderEvent.CameraSetup
 * @see EntityViewRenderEvent.FOVModifier
 */
public abstract class EntityViewRenderEvent extends Event
{
    private final GameRenderer renderer;
    private final ActiveRenderInfo info;
    private final double renderPartialTicks;

    public EntityViewRenderEvent(GameRenderer renderer, ActiveRenderInfo info, double renderPartialTicks)
    {
        this.renderer = renderer;
        this.info = info;
        this.renderPartialTicks = renderPartialTicks;
    }

    /**
     * @return the game renderer instance
     */
    public GameRenderer getRenderer()
    {
        return renderer;
    }

    /**
     * @return the active render information
     */
    public ActiveRenderInfo getInfo()
    {
        return info;
    }

    /**
     * @return the amount of partial ticks
     */
    public double getRenderPartialTicks()
    {
        return renderPartialTicks;
    }

    /**
     * <p>Fired for customizing the different features of the fog visible to the player. <br/>
     * See the various subclasses to listen for specific features. </p>
     *
     * <p>This event is not {@linkplain Cancelable cancelable}, and do not {@linkplain HasResult have a result}. </p>
     *
     * <p>This event is fired on the {@linkplain MinecraftForge#EVENT_BUS main Forge event bus},
     * only on the {@linkplain LogicalSide#CLIENT logical client}. </p>
     *
     * @see EntityViewRenderEvent.FogDensity
     * @see EntityViewRenderEvent.RenderFogEvent
     */
    private static class FogEvent extends EntityViewRenderEvent
    {
        private final FogType type;
        protected FogEvent(FogType type, ActiveRenderInfo info, double renderPartialTicks)
        {
            super(Minecraft.getInstance().gameRenderer, info, renderPartialTicks);
            this.type = type;
        }

        /**
         * @return the type of fog
         */
        public FogType getType() { return type; }
    }

    /**
     * <p>Fired for customizing the <b>density</b> of the fog visible to the player. </p>
     *
     * <p>This event is {@linkplain Cancelable cancelable}, and does not {@linkplain HasResult have a result}. <br/>
     * <em>The event must be cancelled for the custom fog density to take effect. </em></p>
     *
     * <p>This event is fired on the {@linkplain MinecraftForge#EVENT_BUS main Forge event bus},
     * only on the {@linkplain LogicalSide#CLIENT logical client}. </p>
     *
     * @see ForgeHooksClient#getFogDensity(FogType, ActiveRenderInfo, float, float)
     */
    @Cancelable
    public static class FogDensity extends FogEvent
    {
        private float density;

        public FogDensity(FogType type, ActiveRenderInfo info, float partialTicks, float density)
        {
            super(type, info, partialTicks);
            this.setDensity(density);
        }

        /**
         * @return the density of the fog
         */
        public float getDensity()
        {
            return density;
        }

        /**
         * Sets the new density of the fog, which only takes effect if the event is cancelled.
         *
         * @param density the new fog density
         */
        public void setDensity(float density)
        {
            this.density = density;
        }
    }

    /**
     * <p>Fired for customizing the <b>rendering</b> of the fog visible to the player. </p>
     *
     * <p>This event is {@linkplain Cancelable cancelable}, and {@linkplain HasResult has a result}. </p>
     * TODO: review if this event needs a result.
     *
     * <p>This event is fired on the {@linkplain MinecraftForge#EVENT_BUS main Forge event bus},
     * only on the {@linkplain LogicalSide#CLIENT logical client}. </p>
     *
     * @see ForgeHooksClient#onFogRender(FogType, ActiveRenderInfo, float, float)
     */
    @HasResult
    public static class RenderFogEvent extends FogEvent
    {
        private final float farPlaneDistance;

        public RenderFogEvent(FogType type, ActiveRenderInfo info, float partialTicks, float distance)
        {
            super(type, info, partialTicks);
            this.farPlaneDistance = distance;
        }

        // TODO: find out what this means
        public float getFarPlaneDistance()
        {
            return farPlaneDistance;
        }
    }

    /**
     * <p>Fired for customizing the <b>color</b> of the fog visible to the player. </p>
     *
     * <p>This event is not {@linkplain Cancelable cancelable}, and does not {@linkplain HasResult have a result}. </p>
     *
     * <p>This event is fired on the {@linkplain MinecraftForge#EVENT_BUS main Forge event bus},
     * only on the {@linkplain LogicalSide#CLIENT logical client}. </p>
     *
     * @see FogRenderer#updateFogColor(ActiveRenderInfo, float, ClientWorld, int, float)
     */
    public static class FogColors extends EntityViewRenderEvent
    {
        private float red;
        private float green;
        private float blue;

        public FogColors(ActiveRenderInfo info, float partialTicks, float red, float green, float blue)
        {
            super(Minecraft.getInstance().gameRenderer, info, partialTicks);
            this.setRed(red);
            this.setGreen(green);
            this.setBlue(blue);
        }

        /**
         * @return the red color value of the fog
         */
        public float getRed() { return red; }

        /**
         * Sets the new red color value of the fog.
         *
         * @param red the new red color value
         */
        public void setRed(float red) { this.red = red; }

        /**
         * @return the green color value of the fog
         */
        public float getGreen() { return green; }

        /**
         * Sets the new green color value of the fog.
         *
         * @param green the new blue color value
         */
        public void setGreen(float green) { this.green = green; }

        /**
         * @return the blue color value of the fog
         */
        public float getBlue() { return blue; }

        /**
         * Sets the new blue color value of the fog.
         *
         * @param blue the new blue color value
         */
        public void setBlue(float blue) { this.blue = blue; }
    }

    /**
     * <p>Fired for altering the angles of the player's camera. <br/>
     * This can be used to alter the player's view for different effects, such as applying roll. </p>
     *
     * <p>This event is not {@linkplain Cancelable cancelable}, and does not {@linkplain HasResult have a result}. </p>
     *
     * <p>This event is fired on the {@linkplain MinecraftForge#EVENT_BUS main Forge event bus},
     * only on the {@linkplain LogicalSide#CLIENT logical client}. </p>
     *
     * @see ForgeHooksClient#onCameraSetup(GameRenderer, ActiveRenderInfo, float)
     */
    public static class CameraSetup extends EntityViewRenderEvent
    {
        private float yaw;
        private float pitch;
        private float roll;

        public CameraSetup(GameRenderer renderer, ActiveRenderInfo info, double renderPartialTicks, float yaw, float pitch, float roll)
        {
            super(renderer, info, renderPartialTicks);
            this.setYaw(yaw);
            this.setPitch(pitch);
            this.setRoll(roll);
        }

        /**
         * @return the yaw of the player's camera
         */
        public float getYaw() { return yaw; }

        /**
         * Sets the yaw of the player's camera.
         *
         * @param yaw the new yaw
         */
        public void setYaw(float yaw) { this.yaw = yaw; }

        /**
         * @return the pitch of the player's camera
         */
        public float getPitch() { return pitch; }

        /**
         * Sets the pitch of the player's camera.
         *
         * @param pitch the new pitch
         */
        public void setPitch(float pitch) { this.pitch = pitch; }

        /**
         * @return the roll of the player's camera
         */
        public float getRoll() { return roll; }

        /**
         * Sets the roll of the player's camera.
         *
         * @param roll the new roll
         */
        public void setRoll(float roll) { this.roll = roll; }
    }

    /**
     * <p>Fired for altering the raw FOV (field of vision). <br/>
     * This is after the e.g. FOV settings are applied, and before modifiers such as the Nausea effect. </p>
     *
     * <p>This event is not {@linkplain Cancelable cancelable}, and does not {@linkplain HasResult have a result}. </p>
     *
     * <p>This event is fired on the {@linkplain MinecraftForge#EVENT_BUS main Forge event bus},
     * only on the {@linkplain LogicalSide#CLIENT logical client}. </p>
     *
     * @see ForgeHooksClient#getFOVModifier(GameRenderer, ActiveRenderInfo, double, double)
     * @see FOVUpdateEvent
     */
    public static class FOVModifier extends EntityViewRenderEvent
    {
        private double fov;

        public FOVModifier(GameRenderer renderer, ActiveRenderInfo info, double renderPartialTicks, double fov) {
            super(renderer, info, renderPartialTicks);
            this.setFOV(fov);
        }

        /**
         * @return the raw FOV (field of vision) value
         */
        public double getFOV() {
            return fov;
        }

        /**
         * Sets the new raw FOV (field of vision) value
         *
         * @param fov the new FOV value
         */
        public void setFOV(double fov) {
            this.fov = fov;
        }
    }
}
