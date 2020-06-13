// ==================================================================
// This file is part of Smart Moving.
//
// Smart Moving is free software: you can redistribute it and/or
// modify it under the terms of the GNU General Public License as
// published by the Free Software Foundation, either version 3 of the
// License, or (at your option) any later version.
//
// Smart Moving is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with Smart Moving. If not, see <http://www.gnu.org/licenses/>.
// ==================================================================

package net.smart.moving;

import net.minecraft.client.Minecraft;

public class LocalUserNameProvider extends SmartMovingContext implements ILocalUserNameProvider
{
	@Override
	public String getLocalConfigUserName()
	{
		return Options._localUserHasChangeConfigRight.value ? Minecraft.getMinecraft().thePlayer.getGameProfile().getName() : null;
	}

	@Override
	public String getLocalSpeedUserName()
	{
		return Options._localUserHasChangeSpeedRight.value ? Minecraft.getMinecraft().thePlayer.getGameProfile().getName() : null;
	}
}