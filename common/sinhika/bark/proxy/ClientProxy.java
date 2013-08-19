package sinhika.bark.proxy;

import sinhika.bark.entity.EntityCanoe;
import sinhika.bark.render.RenderCanoe;
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
