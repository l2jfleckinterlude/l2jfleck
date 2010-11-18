/*
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 *
 * http://www.gnu.org/copyleft/gpl.html
 */
package com.l2jfrozen.gameserver.model.extender;

import com.l2jfrozen.gameserver.model.L2Object;

/**
 * @author Azagthtot BaseExtender - Р±Р°Р·РѕРІС‹Р№ РєР»Р°СЃСЃ СЂР°СЃС€РёСЂРµРЅРёСЏ РІРѕР·РјРѕР¶РЅРѕСЃС‚РµР№<BR>
 * <BR>
 */
public class BaseExtender
{
	public enum EventType
	{
		LOAD("load"), // null
		STORE("store"), // null
		CAST("cast"), // L2Skill , L2Character, L2Character[]
		ATTACK("attack"), // L2Character
		CRAFT("craft"),
		ENCHANT("enchant"),
		SPAWN("spawn"), // null
		DELETE("delete"), // null
		SETOWNER("setwoner"), // int, String
		DROP("drop"), // null		
		DIE("die"), // L2Character
		REVIVE("revive"), // null
		SETINTENTION("setintention"); // CtrlIntention
		public final String name;

		EventType(String name)
		{
			this.name = name;
		}
	}

	/**
	 * @param object as L2Object<br>
	 * @return as boolean<br>
	 */
	public static boolean canCreateFor(L2Object object)
	{
		return true;
	}

	protected L2Object _owner;
	private BaseExtender _next = null;

	/**
	 * @param owner - L2Object
	 */
	public BaseExtender(L2Object owner)
	{
		_owner = owner;
	}

	/**
	 * @return as Object
	 */
	public L2Object getOwner()
	{
		return _owner;
	}

	/**
	 * onEvent - super.onEvent(event,params);<BR>
	 * <BR>
	 * 
	 * @param event as String<br>
	 * @param params as Object[]<br>
	 * @return as Object
	 */
	public Object onEvent(final String event, Object... params)
	{
		if(_next == null)
			return null;
		else
			return _next.onEvent(event, params);
	}

	/**
	 * @param simpleClassName as String<br>
	 * @return as BaseExtender - null
	 */
	public BaseExtender getExtender(final String simpleClassName)
	{
		if(this.getClass().getSimpleName().compareTo(simpleClassName) == 0)
			return this;
		else if(_next != null)
			return _next.getExtender(simpleClassName);
		else
			return null;
	}

	public void removeExtender(BaseExtender ext) {
		if(_next!=null)
			if(_next==ext)
				_next = _next._next;
			else
				_next.removeExtender(ext);
	}
	public BaseExtender getNextExtender() {
		return _next;
	}
	/**
	 * @param newExtender as BaseExtender
	 */
	public void addExtender(BaseExtender newExtender)
	{
		if(_next == null)
		{
			_next = newExtender;
		}
		else
		{
			_next.addExtender(newExtender);
		}
	}
}
