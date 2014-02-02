/**
 * @author Brad Leege <bleege@gmail.com>
 * Created on 2/1/14 at 10:37 PM
 */
package com.mapbox.mapboxsdk.dimensions;

public class Size
{
	private double width;
	private double height;

	/**
	 * Constructor
	 * @param width Width
	 * @param height Height
	 */
	public Size(double width, double height)
	{
		this.width = width;
		this.height = height;
	}

	public double getWidth()
	{
		return width;
	}

	public void setWidth(double width)
	{
		this.width = width;
	}

	public double getHeight()
	{
		return height;
	}

	public void setHeight(double height)
	{
		this.height = height;
	}
}
