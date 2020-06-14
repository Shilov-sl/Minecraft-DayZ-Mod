package ru.shilov.dayz.common.inventories;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import ru.shilov.dayz.common.entity.EntityDeadBody;

public class InventoryDeadBody implements IInventory
{
    public ItemStack[] armorInventory = new ItemStack[5];
    public ItemStack[] mainInventory = new ItemStack[36];

    public EntityDeadBody deadBody;

    public boolean inventoryChanged;
    
    public boolean checked = false;
    
	private String name = "Dead body iventory";

    public InventoryDeadBody(EntityDeadBody deadBody)
    {
        this.deadBody = deadBody;
        this.checked = false;
    }

    public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_)
    {
        ItemStack[] aitemstack = this.mainInventory;

        if (p_70298_1_ >= this.mainInventory.length)
        {
            aitemstack = this.armorInventory;
            p_70298_1_ -= this.mainInventory.length;
        }

        if (aitemstack[p_70298_1_] != null)
        {
            ItemStack itemstack;

            if (aitemstack[p_70298_1_].stackSize <= p_70298_2_)
            {
                itemstack = aitemstack[p_70298_1_];
                aitemstack[p_70298_1_] = null;
                return itemstack;
            }
            else
            {
                itemstack = aitemstack[p_70298_1_].splitStack(p_70298_2_);

                if (aitemstack[p_70298_1_].stackSize == 0)
                {
                    aitemstack[p_70298_1_] = null;
                }

                return itemstack;
            }
        }
        else
        {
            return null;
        }
    }

    public ItemStack getStackInSlotOnClosing(int p_70304_1_)
    {
        ItemStack[] aitemstack = this.mainInventory;

        if (p_70304_1_ >= this.mainInventory.length)
        {
            aitemstack = this.armorInventory;
            p_70304_1_ -= this.mainInventory.length;
        }

        if (aitemstack[p_70304_1_] != null)
        {
            ItemStack itemstack = aitemstack[p_70304_1_];
            aitemstack[p_70304_1_] = null;
            return itemstack;
        }
        else
        {
            return null;
        }
    }

    public void setInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_)
    {
        ItemStack[] aitemstack = this.mainInventory;

        if (p_70299_1_ >= aitemstack.length)
        {
            p_70299_1_ -= aitemstack.length;
            aitemstack = this.armorInventory;
        }

        aitemstack[p_70299_1_] = p_70299_2_;
    }


    /**
     * Writes the inventory out as a list of compound tags. This is where the slot indices are used (+100 for armor, +80
     * for crafting).
     */
    public NBTTagList writeToNBT(NBTTagList p_70442_1_)
    {
        int i;
        NBTTagCompound nbttagcompound;

        for (i = 0; i < this.mainInventory.length; ++i)
        {
            if (this.mainInventory[i] != null)
            {
                nbttagcompound = new NBTTagCompound();
                nbttagcompound.setByte("Slot", (byte)i);
                this.mainInventory[i].writeToNBT(nbttagcompound);
                p_70442_1_.appendTag(nbttagcompound);
            }
        }

        for (i = 0; i < this.armorInventory.length; ++i)
        {
            if (this.armorInventory[i] != null)
            {
                nbttagcompound = new NBTTagCompound();
                nbttagcompound.setByte("Slot", (byte)(i + 100));
                this.armorInventory[i].writeToNBT(nbttagcompound);
                p_70442_1_.appendTag(nbttagcompound);
            }
        }

        return p_70442_1_;
    }

    /**
     * Reads from the given tag list and fills the slots in the inventory with the correct items.
     */
    public void readFromNBT(NBTTagList p_70443_1_)
    {
        this.mainInventory = new ItemStack[36];
        this.armorInventory = new ItemStack[5];

        for (int i = 0; i < p_70443_1_.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound = p_70443_1_.getCompoundTagAt(i);
            int j = nbttagcompound.getByte("Slot") & 255;
            ItemStack itemstack = ItemStack.loadItemStackFromNBT(nbttagcompound);

            if (itemstack != null)
            {
                if (j >= 0 && j < this.mainInventory.length)
                {
                    this.mainInventory[j] = itemstack;
                }

                if (j >= 100 && j < this.armorInventory.length + 100)
                {
                    this.armorInventory[j - 100] = itemstack;
                }
            }
        }
    }

    /**
     * Returns the number of slots in the inventory.
     */
    public int getSizeInventory()
    {
        return this.mainInventory.length + 4;
    }

    /**
     * Returns the stack in slot i
     */
    public ItemStack getStackInSlot(int p_70301_1_)
    {
        ItemStack[] aitemstack = this.mainInventory;

        if (p_70301_1_ >= aitemstack.length)
        {
            p_70301_1_ -= aitemstack.length;
            aitemstack = this.armorInventory;
        }

        return aitemstack[p_70301_1_];
    }

    /**
     * Returns the name of the inventory
     */
    public String getInventoryName()
    {
        return this.name;
    }

    /**
     * Returns if the inventory is named
     */
    public boolean hasCustomInventoryName()
    {
        return true;
    }

    /**
     * Returns the maximum stack size for a inventory slot.
     */
    public int getInventoryStackLimit()
    {
        return 64;
    }


    /**
     * returns a player armor item (as itemstack) contained in specified armor slot.
     */
    public ItemStack armorItemInSlot(int p_70440_1_)
    {
        return this.armorInventory[p_70440_1_];
    }

    /**
     * Based on the damage values and maximum damage values of each armor item, returns the current armor value.
     */
    public int getTotalArmorValue()
    {
        int i = 0;

        for (int j = 0; j < this.armorInventory.length; ++j)
        {
            if (this.armorInventory[j] != null && this.armorInventory[j].getItem() instanceof ItemArmor)
            {
                int k = ((ItemArmor)this.armorInventory[j].getItem()).damageReduceAmount;
                i += k;
            }
        }

        return i;
    }

    /**
     * For tile entities, ensures the chunk containing the tile entity is saved to disk later - the game won't think it
     * hasn't changed and skip it.
     */
    public void markDirty()
    {
        this.inventoryChanged = true;
    }

    /**
     * Do not make give this method the name canInteractWith because it clashes with Container
     */
    public boolean isUseableByPlayer(EntityPlayer p_70300_1_)
    {
        return this.deadBody.isDead ? false : p_70300_1_.getDistanceSqToEntity(this.deadBody) <= 64.0D;
    }


    public void openInventory() {}

    public void closeInventory() {}

    /**
     * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot.
     */
    public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_)
    {
        return true;
    }

    /**
     * Copy the ItemStack contents from another InventoryPlayer instance
     */
    public void copyInventory(InventoryDeadBody p_70455_1_)
    {
        int i;

        for (i = 0; i < this.mainInventory.length; ++i)
        {
            this.mainInventory[i] = ItemStack.copyItemStack(p_70455_1_.mainInventory[i]);
        }

        for (i = 0; i < this.armorInventory.length; ++i)
        {
            this.armorInventory[i] = ItemStack.copyItemStack(p_70455_1_.armorInventory[i]);
        }
    }
}