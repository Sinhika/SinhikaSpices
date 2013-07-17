package sinhika.spices;

import java.util.ArrayList;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;

public class ItemSpud extends ItemTool {
	
	/** an array of the blocks this bark spud is effective against */
    public static final Block[] blocksEffectiveAgainst = {Block.wood, Spices.barkBlock};


    /** constructor for ItemSpud
     * 
     * @param par1 itemID
     * @param par3EnumToolMaterial material tool is made of.
     */
	public ItemSpud(int par1, EnumToolMaterial par3EnumToolMaterial) 
	{
		super(par1, 2.0F, par3EnumToolMaterial, blocksEffectiveAgainst);
		setCreativeTab(Spices.customTabSpices);
	}
		
	
	/**
	 * @see net.minecraft.item.Item#onBlockStartBreak(net.minecraft.item.ItemStack, int, int, int, net.minecraft.entity.player.EntityPlayer)
	 */
	@Override
	public boolean onBlockStartBreak(ItemStack itemstack, int x, int y, int z,
									 EntityPlayer player) 
	{
	      if (player.worldObj.isRemote) {
	            return false;
	      }
	      int id = player.worldObj.getBlockId(x, y, z);
	      
	      if (id == Block.wood.blockID || id == Spices.barkBlock.blockID) 
	      {
	    	  int metadata = Block.blocksList[id].getDamageValue(player.worldObj, x, y, z);
	    	  
	          ArrayList<ItemStack> drops = 
	        		  getBarkPeeled(player.worldObj, id, metadata, EnchantmentHelper.getEnchantmentLevel(Enchantment.fortune.effectId, itemstack));

	          Random rand = new Random();
              for(ItemStack stack : drops)
              {
                  float f = 0.7F;
                  double d  = (double)(rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
                  double d1 = (double)(rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
                  double d2 = (double)(rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
                  EntityItem entityitem = 
                		  new EntityItem(player.worldObj, (double)x + d, (double)y + d1, (double)z + d2, stack);
                  entityitem.delayBeforeCanPickup = 10;
                  player.worldObj.spawnEntityInWorld(entityitem);
              }

              itemstack.damageItem(1, player);
              player.addStat(StatList.mineBlockStatArray[id], 1);
    	      player.worldObj.destroyBlock(x, y, z, false);
    	      return true;
	      }
	      return false;
	} // end onBlockStartBreak()

	/**
	 * Determine what kind of bark was peeled from which log, and how many.
	 * precondition: must be some kind of bark, or a type of log with a matching bark.
	 * 
	 * @param world current World object
	 * @param id blockID of block being broken
	 * @param metadata metadata value of block being broken
	 * @param fortune fortune factor on tool.
	 * @return list of bark drops
	 */
	protected ArrayList<ItemStack> getBarkPeeled(World world, int id, int metadata, int fortune)
	{
		Item dropItem;
		int hl = this.toolMaterial.getHarvestLevel();
		
		dropItem = Spices.barkItems[metadata];
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();

        int count = Block.blocksList[id].quantityDropped(metadata, fortune, world.rand)
        		+ hl + 2;
        for(int i = 0; i < count; i++)
        {
        	ret.add(new ItemStack(dropItem, 1, metadata));
        }
        return ret;		
	} // end getBarkPeeled()
	
	
//	/* (non-Javadoc)
//	 * @see net.minecraft.item.ItemTool#onBlockDestroyed(net.minecraft.item.ItemStack, net.minecraft.world.World, int, int, int, int, net.minecraft.entity.EntityLivingBase)
//	 */
//	@Override
//	public boolean onBlockDestroyed(ItemStack par1ItemStack, World world,
//			int id, int x, int y, int z,
//			EntityLivingBase player) 
//	{
//	      int metadata = player.worldObj.getBlockMetadata(x, y, z);
//	      super.onBlockDestroyed(par1ItemStack, world, id, x, y, z, player);
//
//	      return false;
//	}


	/* (non-Javadoc)
	 * @see net.minecraft.item.Item#canHarvestBlock(net.minecraft.block.Block)
	 */
	@Override
	public boolean canHarvestBlock(Block par1Block) {
		return false;
	}



	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		this.itemIcon = iconRegister.registerIcon(this.func_111208_A());
	} // end registerIcons()

} // class
