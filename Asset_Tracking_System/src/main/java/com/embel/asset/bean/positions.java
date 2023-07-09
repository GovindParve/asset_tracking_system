package com.embel.asset.bean;

public class positions 
{
	long x;
	long y;
	public positions() {
		super();
		// TODO Auto-generated constructor stub
	}
	public positions(long x, long y) {
		super();
		this.x = x;
		this.y = y;
	}
	public long getX() {
		return x;
	}
	public void setX(long x) {
		this.x = x;
	}
	public long getY() {
		return y;
	}
	public void setY(long y) {
		this.y = y;
	}
	@Override
	public String toString() {
		return "positions [x=" + x + ", y=" + y + "]";
	}
	

}
