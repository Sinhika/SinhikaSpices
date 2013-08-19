/**
 * 
 */
package sinhika.bark.network;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

/**
 * @author cyhiggin
 * 
 */
public class PacketHandler implements IPacketHandler
{

    /**
	 * 
	 */
    public PacketHandler()
    {
        // TODO Auto-generated constructor stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * cpw.mods.fml.common.network.IPacketHandler#onPacketData(net.minecraft
     * .network.INetworkManager,
     * net.minecraft.network.packet.Packet250CustomPayload,
     * cpw.mods.fml.common.network.Player)
     */
    @Override
    public void onPacketData(INetworkManager manager,
            Packet250CustomPayload packet, Player player)
    {
        // TODO Auto-generated method stub

    }

}
