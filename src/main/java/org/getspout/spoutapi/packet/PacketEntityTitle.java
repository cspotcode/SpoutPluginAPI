/*
 * This file is part of SpoutPluginAPI (http://www.spout.org/).
 *
 * SpoutPluginAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SpoutPluginAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.getspout.spoutapi.packet;

import java.io.IOException;

import org.getspout.spoutapi.io.SpoutInputStream;
import org.getspout.spoutapi.io.SpoutOutputStream;

public class PacketEntityTitle implements SpoutPacket {
	public String title;
	public int entityId;

	public PacketEntityTitle() {

	}

	public PacketEntityTitle(int entityId, String title) {
		this.entityId = entityId;
		this.title = title;
	}

	@Override
	public void readData(SpoutInputStream input) throws IOException {
		entityId = input.readInt();
		title = input.readString();
	}

	@Override
	public void writeData(SpoutOutputStream output) throws IOException {
		output.writeInt(entityId);
		output.writeString(title);
	}

	@Override
	public void run(int id) {

	}

	@Override
	public void failure(int id) {

	}

	@Override
	public PacketType getPacketType() {
		return PacketType.PacketEntityTitle;
	}

	@Override
	public int getVersion() {
		return 0;
	}
}
