package cs414.a1.pguharay;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import cs414.a1.pguharay.Coordinate;
import cs414.a1.pguharay.World;

public class WorldTest 
{
	private World world;
	
	@Test
	public void constructWorldWithDifferentValidRowsAndColumns()
	{
		int n = 10;
		int m = 8;
		world = new World(n,m);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void constructWorldWithDifferentNegetiveRowsAndPositiveColumns()
	{
		int n = 10;
		int m = -8;
		
		world = new World(n,m);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void constructWorldWithDifferentPositiveRowsAndNegetiveColumns()
	{
		int n = -10;
		int m = 8;
		
		world = new World(n,m);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void constructWorldWithDifferentNegetiveRowsAndNegetiveColumns()
	{
		int n = -10;
		int m = -8;
		
		world = new World(n,m);
	}
	
	@Test
	public void constructWorldWithSamePositiveRowsAndColumns()
	{
		int n = 5;
		world = new World(n);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void initializeWorldWithZeroPositiveRowsAndColumns()
	{
		int n = 0;
		world = new World(n);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void constructWorldWithSameNegetiveRowsAndColumns()
	{
		int n = -5;
		world = new World(n);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void constructWorldWithMaximumPossibleCells()
	{
		world = new World(Integer.MAX_VALUE);
	}
	
	@Test
	public void getFromWorldWhenNoObjectExistsInside()
	{
		world = new World(10);
		assertNull(world.get(2,3));
	}
	
	@Test
	public void getFromWorldWithValidPositiveXAndYCordinate()
	{
		world = new World(10);
		
		Object object = new Object();
		world.put(2,3,object );
		
		assertNotNull(world.get(2,3));
		assertEquals(object, world.get(2, 3));
	}
	
	@Test(expected=ArrayIndexOutOfBoundsException.class)
	public void getFromWorldWithOutOfBoundPositiveXAndValidPositiveYCordinate()
	{
		world = new World(10);
		world.get(20,3);
	}
	
	@Test(expected=ArrayIndexOutOfBoundsException.class)
	public void getFromWorldWithValidPositiveXAndOutofBoundPositiveYCordinate()
	{
		world = new World(10);
		world.get(2,10);
	}
	
	@Test
	public void putToWorldWithPositiveValidXAndYCordinateAndNotNullObject()
	{
		world = new World(5);
		
		Object object = new Object();
		world.put(0,3,object );
		
		assertNotNull(world.get(0,3));
		assertEquals(object, world.get(0, 3));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void putToWorldWithPositiveValidXAndYCordinateAndtNullObject()
	{
		world = new World(5);
		world.put(0,3,null);
	}
	
	@Test(expected=ArrayIndexOutOfBoundsException.class)
	public void getFromWorldWithNegetiveXAndPositiveYCoordinate()
	{
		world = new World(10);
		world.get(-20,3);
	}
	
	@Test
	public void getFromWorldWithZeroXAndZeroYCoordinate()
	{
		world = new World(10);
		assertNull(world.get(0,0));
	}
	
	@Test(expected=ArrayIndexOutOfBoundsException.class)
	public void putToWorldWithValidPositiveXAndInValidNegetiveYCoordinate()
	{
		world = new World(10);
		Object object = new Object();
		world.put(0,-3,object);
	}

	@Test(expected=ArrayIndexOutOfBoundsException.class)
	public void putToWorldWithValidPositiveXAndInValidPositiveYCoordinate()
	{
		world = new World(10);
		Object object = new Object();
		world.put(1,13,object);
	}
	
	@Test
	public void putToWorldWithNotNullCoordinateInstance()
	{
		world = new World(10);
		int x = 0;
		int y = 0;
		
		world.put(x, y, world);
		
		Coordinate coordinate = new Coordinate(world,x,y);
		assertEquals(world, world.get(coordinate));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void getFromWorldWhenCorodinateConstructedWithNullWorldObject()
	{
		world = new World(10);
		int x = 0;
		int y = 0;
		world.put(x, y, world);
		
		Coordinate coordinate = new Coordinate(null,x,y);
		assertEquals(world, world.get(coordinate));
	}
	
	@Test
	public void assertCallbacksWhenGetAndPutAtWorldViaCoordinate()
	{
		final int xCoordinate = 2;
		final int yCoordinate = 3;
		final String string = "hello";
		
		World world = new World(10)
		{
			@Override
			protected Object get(int x, int y) 
			{
				assertEquals(xCoordinate, x);
				assertEquals(yCoordinate, y);
				return new String("passed");
			}

			@Override
			protected void put(int x, int y, Object object) 
			{
				assertEquals(xCoordinate, x);
				assertEquals(yCoordinate, y);
				assertTrue(object instanceof String);
				
				String s = (String)object;
				assertEquals("hello", s);
				
				super.put(x, y, object);
			}
		};
		
		Coordinate coordinate = new Coordinate(world, xCoordinate, yCoordinate);
		Object o = world.get(coordinate);
		
		assertTrue(o instanceof String);
		assertEquals("passed", o);
		
		world.put(coordinate, string);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void getObjectFromWorldUsingCoordinatesFromAnotherWorld()
	{
		World myWorld = new World(10);
		World notMyWorld = new World(20);
		
		int xCoordinate = 4;
		int yCoordinate = 5;
		Coordinate coordinateFromNotMyWorld = new Coordinate(notMyWorld, xCoordinate, yCoordinate);
		
		myWorld.put(coordinateFromNotMyWorld, "hello");
	}
	
	@Test(expected=RuntimeException.class)
	public void putObjectToWorldWhenCoordinateInstanceThrowsExceptionOnPut()
	{
		final int xCoordinate = 2; 
		final int yCoordinate = 3;
		
		final World world = new World(10);
		
		Coordinate coordinate = new Coordinate(world, xCoordinate, yCoordinate)
		{
			@Override
			public void put(Object object) 
			{
				throw new RuntimeException("Exception is thrown to validate " +
						"that World is not hiding the exception");
			}
			
		};
		
		world.put(coordinate, new Object());
	}
	
	@Test(expected=RuntimeException.class)
	public void getObjectToWorldWhenCoordinateInstanceThrowsExceptionOnGet()
	{
		final int xCoordinate = 2; 
		final int yCoordinate = 3;
		
		final World world = new World(10);
		
		Coordinate coordinateFromAnotherWorld = new Coordinate(world, xCoordinate, yCoordinate)
		{
			@Override
			public Object get() 
			{
				throw new RuntimeException("Exception is thrown to validate " +
						"that World is not hiding the exception");
			}
		};
		
		world.get(coordinateFromAnotherWorld);
	}
	
	@Test
	public void assertStringRepresentationOfWorld()
	{
		world = new World(10,12);
		String worldAsString  = "World(10,12)";
		assertEquals(worldAsString, world.toString());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void getFromWorldWhenNullCordinateIsUsed()
	{
		world = new World(10,12);
		world.get(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void putNullObjectToWorld()
	{
		world = new World(10,12);
		world.put(null, new Object());
	}
}