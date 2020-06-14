package ru.shilov.dayz.common.entity;

import java.util.UUID;

import com.mojang.authlib.GameProfile;

import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import ru.shilov.dayz.common.inventories.InventoryDeadBody;

public class EntityDeadBody extends EntityLiving
{
	private String name = "DEADBODY";

	public InventoryDeadBody inventory;

	private static String playerName;

	private static int currentItemID;

	private static GameProfile playerProfile;

	public EntityDeadBody(World par1World)
	{
		super(par1World);
		this.inventory = new InventoryDeadBody(this);
		this.setSize(1.8F, 0.8F);
	}
	

    protected void entityInit()
    {
        super.entityInit();
        this.dataWatcher.addObject(14, new String(""));
    }

	@Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.0D);
    }
    
	@Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        this.rotationPitch = 0;
        this.rotationYaw = 0;
        this.rotationYawHead = 0;
        this.cameraPitch = 0;
		this.renderYawOffset = 0;
		this.prevRenderYawOffset = 0;
		this.prevRotationYaw = 0;
		this.newRotationYaw = 0;
		//this.setRotationYawHead(0.0F);
		
		
        /*
        this.setCurrentItemOrArmor(0, inventory.mainInventory[this.currentItemID]);
        
        this.setCurrentItemOrArmor(1, inventory.armorInventory[0]);
        this.setCurrentItemOrArmor(2, inventory.armorInventory[1]);
        this.setCurrentItemOrArmor(3, inventory.armorInventory[2]);
        this.setCurrentItemOrArmor(4, inventory.armorInventory[3]);
        */
    }
    
	@Override
    protected boolean isAIEnabled()
    {
        return false;
    }
    
	@Override
	protected boolean canDespawn() {
        return false;
    }
	
	//BAD Monkey! Not work!
	@Override
	public boolean canBePushed() {
        return false;
    }
	
	@Override
	public boolean interact(EntityPlayer player)
	{
		if (!this.worldObj.isRemote)
		{
			//System.out.println(FMLCommonHandler.instance().getEffectiveSide());
			//LDPlayerHandler.getServerPlayer(player).setEntityID(this.getEntityId());
			//LDEngine.packet_handler.sendPacketToServer(new LDOpenGuiPacket(6).generatePacket());
			return true;
		}
		else
		{
			//System.out.println(FMLCommonHandler.instance().getEffectiveSide());
			//LDPlayerHandler.getClientPlayer(player.getCommandSenderName(), player).setEntityID(this.getEntityId());
			//LDEngine.packet_handler.sendPacketToServer(new LDOpenGuiPacket(6).generatePacket());
			return true;
		}
		
		
	}


	@Override
	public void readEntityFromNBT(NBTTagCompound tagcompound) 
	{
        NBTTagList nbttaglist = tagcompound.getTagList(name, 10);
		this.inventory.readFromNBT(nbttaglist);
		this.currentItemID = tagcompound.getInteger("currentItemID");
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound tagcompound) 
	{
		tagcompound.setTag(name, this.inventory.writeToNBT(new NBTTagList()));
		tagcompound.setInteger("currentItemID", currentItemID);
	}
	
	public static int getCurrentItem()
	{
		return currentItemID;
	}

	public void setCurrentItemID(int currentItem) 
	{
		currentItemID = currentItem;
	}
	

    public String getPlayerName()
    {
    	DataWatcher dw = getDataWatcher();
    	return dw.getWatchableObjectString(14);
    }

    
    public void setPlayerProfile(GameProfile gp)
    {
		DataWatcher dw = getDataWatcher();
    	
		if (gp.getName() != null)
		{
			dw.updateObject(14, gp.getName());
		} 
		else 
		{
			dw.updateObject(14, "");
		}
	}
	
	public GameProfile getPlayerProfile()
	{
		if (this.getPlayerName().isEmpty() || this.getPlayerName() == null)
		{
			return null;
		}
		else
		{
			GameProfile gp = new GameProfile((UUID)null, this.getPlayerName());
			return gp;
		}

	}


	@Override
	public ItemStack getHeldItem() 
	{
		return this.inventory.armorInventory[4];
	}


	@Override
	public ItemStack getEquipmentInSlot(int slot) 
	{
		return this.inventory.armorInventory[slot];
	}
	
	

	@Override
	public ItemStack func_130225_q(int slot) 
	{
		return this.inventory.armorInventory[slot + 1];
	}


	@Override
	public void setCurrentItemOrArmor(int slot, ItemStack stack) 
	{
		this.inventory.armorInventory[slot] = stack;
	}


	@Override
	public ItemStack[] getLastActiveItems() {
		return this.inventory.mainInventory;
	}
	
	@Override
    protected Item getDropItem()
    {
        return null;
    }
	
	@Override
    protected void dropEquipment(boolean p_82160_1_, int p_82160_2_)
    {
    	
    }
}
