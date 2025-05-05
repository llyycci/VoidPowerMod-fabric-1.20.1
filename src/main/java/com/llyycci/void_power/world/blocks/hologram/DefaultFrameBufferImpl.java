package com.llyycci.void_power.world.blocks.hologram;

public class DefaultFrameBufferImpl implements IFrameBuffer{
    protected final int w,h;
    protected final int[] buffer;
    protected int initColor = 0x000000FF;

    public DefaultFrameBufferImpl(int w, int h){
        this.w = w;
        this.h = h;
        this.buffer = new int[w*h];
    }


    @Override
    public int[] getBuffer() {
        return buffer;
    }

    @Override
    public int getWidth() {
        return w;
    }

    @Override
    public int getHeight() {
        return h;
    }

    @Override
    public IFrameBuffer resize(int w, int h) {
		DefaultFrameBufferImpl newBuffer = new DefaultFrameBufferImpl(w, h);
		newBuffer.setInitColor(this.initColor);

		// 复制原始数据到新缓冲区
		int minWidth = Math.min(this.w, w);
		int minHeight = Math.min(this.h, h);

		for (int y = 0; y < minHeight; y++) {
			for (int x = 0; x < minWidth; x++) {
				newBuffer.buffer[y * w + x] = this.buffer[y * this.w + x];
			}
		}

		return newBuffer;
//        return new DefaultFrameBufferImpl(w,h);
    }

    @Override
    public int getInitColor() {
        return initColor;
    }

    @Override
    public void setInitColor(int col) {
        initColor = col;
    }
}
