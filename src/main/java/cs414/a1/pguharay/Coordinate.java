package cs414.a1.pguharay;

import java.awt.Dimension;

public class Coordinate 
{
	private World world;
	private int locationOnXAxis;
	private int locationOnYAxis;
	
	public Coordinate(World world, int xCoordinate, int yCoordinate) 
	{
		validateWorldExists(world);
		
		this.world = world;
		this.locationOnXAxis = wrapXCoordinate(xCoordinate);
		this.locationOnYAxis = wrapYCoordinate(yCoordinate);
	}
	
	public Object get() 
	{
		return world.get(locationOnXAxis, locationOnYAxis);
	}

	public void put(Object object) 
	{
		world.put(locationOnXAxis, locationOnYAxis, object);
	}

	public Coordinate north() 
	{
		int yCoordinate = 0;
		int maxNorth = (int)(world.getDimension().getHeight() - 1);
		
		if(locationOnYAxis == maxNorth)
		{
			yCoordinate = 0;
		}
		else
		{
			yCoordinate = locationOnYAxis + 1;
		}
		
		return new Coordinate(world, locationOnXAxis, yCoordinate);
	}

	public Coordinate south() 
	{
		int yCoordinate = 0;
		int maxNorth = (int)(world.getDimension().getHeight() - 1);
		
		if(locationOnYAxis == 0)
		{
			yCoordinate = maxNorth;
		}
		else
		{
			yCoordinate = locationOnYAxis - 1;
		}
		
		return new Coordinate(world, locationOnXAxis, yCoordinate);
	}

	public Coordinate east() 
	{
		int xCoordinate = 0;
		int maxEast = (int)(world.getDimension().getWidth()- 1);
		
		if(locationOnXAxis == maxEast)
		{
			xCoordinate = 0;
		}
		else
		{
			xCoordinate = locationOnXAxis + 1;
		}
		
		return new Coordinate(world, xCoordinate, locationOnYAxis);
	}
	
	public Coordinate west() 
	{
		int xCoordinate = 0;
		int maxEast = (int)(world.getDimension().getWidth() - 1);
		
		if(locationOnXAxis == 0)
		{
			xCoordinate = maxEast;
		}
		else
		{
			xCoordinate = locationOnXAxis - 1;
		}
		
		return new Coordinate(world, xCoordinate, locationOnYAxis);
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if(!(obj instanceof Coordinate))
		{
			return false;
		}
		if(obj == this)
		{
			return true;
		}

		Coordinate coordinate = (Coordinate)obj;
		if(getClass() != coordinate.getClass())
		{
			return false;
		}
		
		if(xCoordinateLocationNotEqual(coordinate) || yCoordinateLocationNotEqual(coordinate))
		{
			return false;
		}
		
		if((world != coordinate.world))
		{
			return false;
		}
		
		return true;
	}
	
	@Override
	public String toString() 
	{
		Dimension dimension = world.getDimension();
		int widthOfWorld = (int)dimension.getWidth();
		int heightOfWorld = (int)dimension.getHeight();
		
		return new StringBuilder("Coordinate(").append(locationOnXAxis)
					.append(",").append(locationOnYAxis).append(") in World(")
					.append(widthOfWorld).append(",").append(heightOfWorld)
					.append(")").toString();
	}

	@Override
	public int hashCode() 
	{
		return locationOnXAxis + locationOnYAxis + world.hashCode();
	}
	
	World getReferenceWorld()
	{
		return world;
	}
	
	private void validateWorldExists(World world) 
	{
		if(world == null)
		{
			throw new IllegalArgumentException("World can not be null");
		}
	}
	
	private boolean yCoordinateLocationNotEqual(Coordinate coordinate)
	{
		return locationOnYAxis != coordinate.locationOnYAxis;
	}
	
	private boolean xCoordinateLocationNotEqual(Coordinate coordinate)
	{
		return locationOnXAxis != coordinate.locationOnXAxis;
	}

	private int wrapXCoordinate(int xCoordinate)
	{
		Dimension dimension = world.getDimension();
		int widthOfWorld = (int)dimension.getWidth();
		
		if(xCoordinate < 0)
		{
			xCoordinate = widthOfWorld + (xCoordinate % widthOfWorld);
		}
		
		if(xCoordinate >= widthOfWorld)
		{
			xCoordinate = xCoordinate % widthOfWorld;
		}
		
		return xCoordinate;
	}
	
	private int wrapYCoordinate(int yCoordinate)
	{
		Dimension dimension = world.getDimension();
		int heightOfWorld = (int)dimension.getHeight();
		
		if(yCoordinate < 0)
		{
			yCoordinate = heightOfWorld + (yCoordinate % heightOfWorld);
		}
		
		if(yCoordinate >= heightOfWorld)
		{
			yCoordinate = yCoordinate % heightOfWorld;
		}
		
		return yCoordinate;
	}
}