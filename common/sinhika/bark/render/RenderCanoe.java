package sinhika.bark.render;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBoat;
import net.minecraft.client.renderer.entity.RenderBoat;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.util.ResourceLocation;

import sinhika.bark.entity.EntityCanoe;
import sinhika.bark.lib.ModInfo;

@SideOnly(Side.CLIENT)
public class RenderCanoe extends RenderBoat
{
    private static final ResourceLocation textureResource 
    = new ResourceLocation(ModInfo.ID, "textures/entity/birch_canoe.png");

    /** instance of ModelBoat for rendering */
    protected ModelBase modelBoat;

    public RenderCanoe()
    {
        this.shadowSize = 0.5F;
        this.modelBoat = new ModelBoat();
    }

   
    protected ResourceLocation getBoatTextures(EntityCanoe par1EntityBoat)
    {
        return textureResource;
    }

    protected ResourceLocation getEntityTexture(Entity par1Entity)
    {
        return this.getBoatTextures((EntityCanoe)par1Entity);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
    {
        this.renderBoat((EntityBoat)par1Entity, par2, par4, par6, par8, par9);
    }
}
