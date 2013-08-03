package sinhika.spices.proxy;

import sinhika.spices.entity.EntityCanoe;
import sinhika.spices.render.RenderCanoe;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy
{
    @Override
    public void registerRenderers()
    {
        RenderingRegistry.registerEntityRenderingHandler(EntityCanoe.class, 
                                                         new RenderCanoe());
    }
} // end class
