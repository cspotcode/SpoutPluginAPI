package org.getspout.spoutapi.material.block;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.bukkit.util.BlockVector;
import org.getspout.spoutapi.inventory.BlockDesign;
import org.getspout.spoutapi.packet.PacketUtil;

public class GenericCustomBlock extends GenericBlock implements BlockDesign {
	
	protected boolean reset = false;

	protected float lowXBound;
	protected float lowYBound;
	protected float lowZBound;
	protected float highXBound;
	protected float highYBound;
	protected float highZBound;

	protected String textureURL;
	protected String texturePlugin;

	protected float[][] xPos;
	protected float[][] yPos;
	protected float[][] zPos;

	protected float[][] textXPos;
	protected float[][] textYPos;
	
	protected int[] lightSourceXOffset;
	protected int[] lightSourceYOffset;
	protected int[] lightSourceZOffset;
	
	protected float maxBrightness = 1.0F;
	protected float minBrightness = 0F;
	
	protected float brightness = 0.5F;
	
	protected int renderPass = 0;
	
	public GenericCustomBlock(int id) {
		super(id);
	}

	public GenericCustomBlock(int id, float lowXBound, float lowYBound, float lowZBound, float highXBound, float highYBound, float highZBound, String textureURL, String texturePlugin,
			float[][] xPos, float[][] yPos, float[][] zPos, float[][] textXPos, float[][] textYPos, int renderPass) {
		super(id);
		this.lowXBound = lowXBound;
		this.lowYBound = lowYBound;
		this.lowZBound = lowZBound;
		this.highXBound = highXBound;
		this.highYBound = highYBound;
		this.highZBound = highZBound;
		this.textureURL = textureURL;
		this.texturePlugin = texturePlugin;
		this.xPos = xPos;
		this.yPos = yPos;
		this.zPos = zPos;
		this.textXPos = textXPos;
		this.textYPos = textYPos;
		this.renderPass = renderPass;
	}

	public void setMaxBrightness(float maxBrightness) {
		this.maxBrightness = maxBrightness;
	}
	
	public void setMinBrightness(float minBrightness) {
		this.minBrightness = minBrightness;
	}
	
	public void setBrightness(float brightness) {
		this.brightness = brightness * maxBrightness + (1 - brightness) * minBrightness;
	}
	
	public void setRenderPass(int renderPass) {
		this.renderPass = renderPass;
	}
	
	public int getRenderPass() {
		return renderPass;
	}

	public int getNumBytes() {
		return PacketUtil.getNumBytes(textureURL) + PacketUtil.getNumBytes(texturePlugin) + PacketUtil.getDoubleArrayLength(xPos) + PacketUtil.getDoubleArrayLength(yPos) + PacketUtil.getDoubleArrayLength(zPos)
		+ PacketUtil.getDoubleArrayLength(textXPos) + PacketUtil.getDoubleArrayLength(textYPos) + 9 * 4 + (3 + lightSourceXOffset.length + lightSourceXOffset.length + lightSourceXOffset.length) * 4;
	}
	
	public int getVersion() {
		return 3;
	}

	public void read(DataInputStream input) throws IOException {
		textureURL = PacketUtil.readString(input);
		if (textureURL.equals(resetString)) {
			reset = true;
			return;
		}
		reset = false;
		texturePlugin = PacketUtil.readString(input);
		xPos = PacketUtil.readDoubleArray(input);
		yPos = PacketUtil.readDoubleArray(input);
		zPos = PacketUtil.readDoubleArray(input);
		textXPos = PacketUtil.readDoubleArray(input);
		textYPos = PacketUtil.readDoubleArray(input);
		lowXBound = input.readFloat();
		lowYBound = input.readFloat();
		lowZBound = input.readFloat();
		highXBound = input.readFloat();
		highYBound = input.readFloat();
		highZBound = input.readFloat();
		maxBrightness = input.readFloat();
		minBrightness = input.readFloat();
		renderPass = input.readInt();
		lightSourceXOffset = PacketUtil.readIntArray(input);
		lightSourceYOffset = PacketUtil.readIntArray(input);
		lightSourceZOffset = PacketUtil.readIntArray(input);
	}

	private final static String resetString = "[reset]";
	
	public void writeReset(DataOutputStream output) {
		PacketUtil.writeString(output, resetString);
	}
	
	public int getResetNumBytes() {
		return PacketUtil.getNumBytes(resetString);
	}

	public void write(DataOutputStream output) throws IOException {
		if (reset) {
			PacketUtil.writeString(output, resetString);
			return;
		}
		PacketUtil.writeString(output, textureURL);
		PacketUtil.writeString(output, texturePlugin);
		PacketUtil.writeDoubleArray(output, xPos);
		PacketUtil.writeDoubleArray(output, yPos);
		PacketUtil.writeDoubleArray(output, zPos);
		PacketUtil.writeDoubleArray(output, textXPos);
		PacketUtil.writeDoubleArray(output, textYPos);
		output.writeFloat(lowXBound);
		output.writeFloat(lowYBound);
		output.writeFloat(lowZBound);
		output.writeFloat(highXBound);
		output.writeFloat(highYBound);
		output.writeFloat(highZBound);
		output.writeFloat(maxBrightness);
		output.writeFloat(minBrightness);
		output.writeInt(renderPass);
		PacketUtil.writeIntArray(output, lightSourceXOffset);
		PacketUtil.writeIntArray(output, lightSourceYOffset);
		PacketUtil.writeIntArray(output, lightSourceZOffset);
	}
	
	public void setTexture(String plugin, String textureURL) {
		this.texturePlugin = plugin;
		this.textureURL = textureURL;
	}
	
	public void setBoundingBox(float lowX, float lowY, float lowZ, float highX, float highY, float highZ) {
		this.lowXBound = lowX;
		this.lowYBound = lowY;
		this.lowZBound = lowZ;
		this.highXBound = highX;
		this.highYBound = highY;
		this.highZBound = highZ;
	}
	
	public void setQuadNumber(int quads) {
		xPos = new float[quads][];
		yPos = new float[quads][];
		zPos = new float[quads][];
		textXPos = new float[quads][];
		textYPos = new float[quads][];
		lightSourceXOffset = new int[quads];
		lightSourceYOffset = new int[quads];
		lightSourceZOffset = new int[quads];
		
		for (int i = 0; i < quads; i++) {
			xPos[i] = new float[4];
			yPos[i] = new float[4];
			zPos[i] = new float[4];
			textXPos[i] = new float[4];
			textYPos[i] = new float[4];
			lightSourceXOffset[i] = 0;
			lightSourceYOffset[i] = 0;
			lightSourceZOffset[i] = 0;
		}
	}
	
	public void setQuad(int quadNumber,
			float x1, float y1, float z1, int tx1, int ty1,
			float x2, float y2, float z2, int tx2, int ty2,
			float x3, float y3, float z3, int tx3, int ty3,
			float x4, float y4, float z4, int tx4, int ty4,
			int textureSizeX, int textureSizeY) {
		
		setVertex(quadNumber, 0, x1, y1, z1, tx1, ty1, textureSizeX, textureSizeY);
		setVertex(quadNumber, 1, x2, y2, z2, tx2, ty2, textureSizeX, textureSizeY);
		setVertex(quadNumber, 2, x3, y3, z3, tx3, ty3, textureSizeX, textureSizeY);
		setVertex(quadNumber, 3, x4, y4, z4, tx4, ty4, textureSizeX, textureSizeY);
		
	}
	
	public void setVertex(int quadNumber, int vertexNumber, float x, float y, float z, int tx, int ty, int textureSizeX, int textureSizeY) {
		xPos[quadNumber][vertexNumber] = x;
		yPos[quadNumber][vertexNumber] = y;
		zPos[quadNumber][vertexNumber] = z;
		textXPos[quadNumber][vertexNumber] = (float)tx / (float)textureSizeX;
		textYPos[quadNumber][vertexNumber] = (float)ty / (float)textureSizeY;
	}

	public String getTexureURL() {
		return textureURL;
	}

	public String getTexturePlugin() {
		return texturePlugin;
	}
	
	public boolean getReset() {
		return reset;
	}
	
	public void setLightSource(int quad, int x, int y, int z) {
		lightSourceXOffset[quad] = x;
		lightSourceYOffset[quad] = y;
		lightSourceZOffset[quad] = z;
	}
	
	public BlockVector getLightSource(int quad, int x, int y, int z) {
		BlockVector blockVector = new BlockVector(x + lightSourceXOffset[quad], y + lightSourceYOffset[quad], z + lightSourceZOffset[quad]);
		return blockVector;
	}

}