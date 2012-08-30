package cs414.a1.pguharay;

import java.awt.Dimension;

public class World 
{
	private Object[][] grid;
	private int heightOfWorldIn2DPlane;
	private int widthOfWorldIn2DPlane;
	
	public World(int width, int height) 
	{
		if(width <=0 || height <=0)
		{
			throw new IllegalArgumentException("Invalid values for n=" + width + ", m=" + height);
		}
		
		this.heightOfWorldIn2DPlane = height;
		this.widthOfWorldIn2DPlane = width;
		
		try
		{
			grid = new Object[this.heightOfWorldIn2DPlane][this.widthOfWorldIn2DPlane]; 
		}
		catch(Throwable t)
		{
			if(t instanceof OutOfMemoryError)
			{
				throw new IllegalArgumentException("Not enough memory to create this world");
			}
		}
	}

	public World(int length) 
	{
		this(length,length);
	}

	public Object get(Coordinate coordinate) 
	{
		validateCoordinateIsGenuine(coordinate);
		
		return coordinate.get();
	}


	public void put(Coordinate coordinate, Object object)
	{
		validateCoordinateIsGenuine(coordinate);
		
		coordinate.put(object);
	}
	
	@Override
	public String toString() 
	{
		return new StringBuilder("World(")
					.append(widthOfWorldIn2DPlane).append(",").append(heightOfWorldIn2DPlane)
					.append(")").toString();
	}
	
	Dimension getDimension()
	{
		return new Dimension(widthOfWorldIn2DPlane, heightOfWorldIn2DPlane);
	}
	
	protected Object get(int xCoordinate, int yCoordinate) 
	{
		return grid[yCoordinate][xCoordinate];
	}

	protected void put(int xCoordinate, int yCoordinate, Object object)
	{
		validateObject(object);
		
		grid[yCoordinate][xCoordinate] = object;
	}

	private void validateObject(Object object) 
	{
		if(object == null)
		{
			throw new IllegalArgumentException("Object reference can not be null");
		}
	}

	private void validateCoordinateIsGenuine(Coordinate coordinate)
	{
		validateNotNullCoordinate(coordinate);
		
		validateCorodinateFromSameFrameOfReference(coordinate);
	}
	
	private void validateNotNullCoordinate(Coordinate coordinate) 
	{
		if(coordinate == null)
		{
			throw new IllegalArgumentException("Coordinate can not be null");
		}
	}
	
	private void validateCorodinateFromSameFrameOfReference(
			Coordinate coordinate) 
	{
		if(this != coordinate.getReferenceWorld())
		{
			throw new IllegalArgumentException("Coordinate is from different world");
		}
	}
}