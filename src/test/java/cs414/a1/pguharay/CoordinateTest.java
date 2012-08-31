package cs414.a1.pguharay;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import cs414.a1.pguharay.Coordinate;
import cs414.a1.pguharay.World;

public class CoordinateTest 
{
	private static final int WIDTH_OF_WORLD = 10;
	private static final int HEIGHT_OF_WORLD = 12;
	private World world;
	
	@Before
	public void setup()
	{
		world = new World(WIDTH_OF_WORLD, HEIGHT_OF_WORLD);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void constructCoordinateWithNullWorldInstance()
	{
		new Coordinate(null , 14, 16);
	}
	
	@Test
	public void constructCoordinateWithNotNullWorldInstance()
	{
		Coordinate coordinate = new Coordinate(world , 14, 16);
		assertNotNull(coordinate);
	}
	
	@Test
	public void assertPutOnCertainCoordinateReplacesExistingObjectFromWorld()
	{
		Coordinate coordinate = new Coordinate(world , 14, 16);
		world.put(4,4,"existing object");
		
		assertEquals("existing object", coordinate.get());
		world.put(coordinate, "new object");
		
		assertFalse("existing object".equals(coordinate.get()));
		assertEquals("new object", coordinate.get());
	}
	
	@Test
	public void putAndGetFromCoordinateUsingOutOfBoundPositiveXandYCoordinate()
	{
		Coordinate coordinate = new Coordinate(world , 14, 16);
		
		world.put(coordinate, new String("hello"));
		assertEquals("hello", world.get(coordinate));
		assertEquals("hello", world.get(4,4));
	}
	
	@Test
	public void putAndGetFromCoordinateUsingOutOfBoundPositiveXandNegetiveYCoordinate()
	{
		Coordinate coordinate = new Coordinate(world , 14, -16);
		
		world.put(coordinate, new String("hello"));
		assertEquals("hello", world.get(coordinate));
		assertEquals("hello", world.get(4,8));
	}
	
	@Test
	public void putAndGetFromCoordinateUsingOutOfBoundNegetiveXandPositiveYCoordinate()
	{
		Coordinate coordinate = new Coordinate(world , -14, 16);
		
		world.put(coordinate, new String("hello"));
		assertEquals("hello", world.get(coordinate));
		assertEquals("hello", world.get(6,4));
	}
	
	@Test
	public void putAndGetFromCoordinateUsingOutOfBoundNegetiveXandYCoordinate()
	{
		Coordinate coordinate = new Coordinate(world , -14, -16);
		
		world.put(coordinate, new String("hello"));
		assertEquals("hello", world.get(coordinate));
		assertEquals("hello", world.get(6,8));
	}
	
	@Test
	public void putAndGetFromCoordinateUsingOutOfBoundMaxPositiveXandYCoordinate()
	{
		Coordinate coordinate = new Coordinate(world , Integer.MAX_VALUE, Integer.MAX_VALUE);
		
		world.put(coordinate, new String("hello"));
		assertEquals("hello", world.get(coordinate));
		
		int xcoordinate = Integer.MAX_VALUE % WIDTH_OF_WORLD; 
		int ycoordinate = Integer.MAX_VALUE % HEIGHT_OF_WORLD;
		
		assertEquals("hello", world.get(xcoordinate,ycoordinate));
	}
	
	@Test
	public void putAndGetFromCoordinateUsingOutOfBoundMinPositiveXandYCoordinate()
	{
		Coordinate coordinate = new Coordinate(world , Integer.MIN_VALUE, Integer.MIN_VALUE);
		
		world.put(coordinate, new String("hello"));
		assertEquals("hello", world.get(coordinate));
		
		int xcoordinate = WIDTH_OF_WORLD + Integer.MIN_VALUE % WIDTH_OF_WORLD; 
		int ycoordinate = HEIGHT_OF_WORLD + Integer.MIN_VALUE % HEIGHT_OF_WORLD;
		
		assertEquals("hello", world.get(xcoordinate,ycoordinate));
	}
	
	@Test
	public void assertWrappingWhenFindNorthInvokedOnEdgeCoodinate()
	{
		world.put(4,0, new String("hello"));
		
		Coordinate coordinate = new Coordinate(world ,14, 11);
		Coordinate northCell = coordinate.north();
		
		assertEquals("hello", world.get(northCell));
	}
	
	@Test
	public void assertNonWrappingWhenFindNorthInvokedOnNonEdgeCoodinate()
	{
		world.put(4,11, new String("hello"));
		
		Coordinate coordinate = new Coordinate(world ,14, 10);
		Coordinate northCell = coordinate.north();
		
		assertEquals("hello", world.get(northCell));
	}
	
	@Test
	public void assertWrappingWhenFindSouthInvokedOnEdgeCoodinate()
	{
		world.put(4,11, new String("hello"));
		
		Coordinate coordinate = new Coordinate(world ,14, 0);
		Coordinate southCell = coordinate.south();
		
		assertEquals("hello", world.get(southCell));
	}
	
	@Test
	public void assertNonWrappingWhenFindSouthInvokedOnNonEdgeCoodinate()
	{
		world.put(4,0, new String("hello"));
		
		Coordinate coordinate = new Coordinate(world ,14, 1);
		Coordinate southCell = coordinate.south();
		
		assertEquals("hello", world.get(southCell));
	}
	
	@Test
	public void assertWrappingWhenFindEastInvokedOnEdgeCoodinate()
	{
		world.put(0,5, new String("hello"));
		
		Coordinate coordinate = new Coordinate(world ,9, 5);
		Coordinate eastCell = coordinate.east();
		
		assertEquals("hello", world.get(eastCell));
	}
	
	@Test
	public void assertNonWrappingWhenFindEastInvokedOnNonEdgeCoodinate()
	{
		world.put(9,5, new String("hello"));
		
		Coordinate coordinate = new Coordinate(world ,8, 5);
		Coordinate eastCell = coordinate.east();
		
		assertEquals("hello", world.get(eastCell));
	}
	
	@Test
	public void assertWrappingWhenFindWestInvokedOnEdgeCoodinate()
	{
		world.put(9,5, new String("hello"));
		
		Coordinate coordinate = new Coordinate(world ,0, 5);
		Coordinate westCell = coordinate.west();
		
		assertEquals("hello", world.get(westCell));
	}
	
	@Test
	public void assertNonWrappingWhenFindWestInvokedOnNonEdgeCoodinate()
	{
		world.put(0,5, new String("hello"));
		
		Coordinate coordinate = new Coordinate(world ,1, 5);
		Coordinate westCell = coordinate.west();
		
		assertEquals("hello", world.get(westCell));
	}
	
	@Test
	public void assertToStringWhenCoordinatedUnwrapped()
	{
		Coordinate coordinate = new Coordinate(world ,7, 5);
		assertEquals("Coordinate(7,5) in World(10,12)", coordinate.toString());
	}
	
	@Test
	public void assertToStringWhenCoordinatesWrapped()
	{
		Coordinate coordinate = new Coordinate(world ,17, 15);
		assertEquals("Coordinate(7,3) in World(10,12)", coordinate.toString());
	}
	
	@Test
	public void assertCoordinatesAreEqual()
	{
		Coordinate coordinateOne = new Coordinate(world ,17, 15);
		Coordinate coordinateTwo = new Coordinate(world ,7, 3);
		//reflexive
		assertEquals(coordinateOne, coordinateOne);
		//symmetric
		assertEquals(coordinateOne, coordinateTwo);
		assertEquals(coordinateTwo, coordinateOne);
		//transitive
		Coordinate coordinateThree = new Coordinate(world ,27, 27);
		assertEquals(coordinateTwo, coordinateThree);
		assertEquals(coordinateOne, coordinateThree);
		//null check
		assertFalse(coordinateOne.equals(null));
	}
	
	@Test
	public void assertCoordinatesAreNotEqualWhenXCoordinateValuesAreNotEqual()
	{
		Coordinate coordinateOne = new Coordinate(world ,5, 15);
		Coordinate coordinateTwo = new Coordinate(world ,7, 3);

		assertEquals(coordinateOne, coordinateOne);

		assertFalse(coordinateOne.equals(coordinateTwo));
		assertFalse(coordinateTwo.equals(coordinateOne));
	}
	
	@Test
	public void assertCoordinatesAreNotEqualWhenYCoordinateValuesAreNotEqual()
	{
		Coordinate coordinateOne = new Coordinate(world ,5, 15);
		
		Coordinate coordinateFour = new Coordinate(world ,5, 7);
		assertFalse(coordinateOne.equals(coordinateFour));
	}
	
	@Test
	public void assertCoordinatesAreNotEqualWhenThoseBelongToDifferentWorlds()
	{
		Coordinate coordinateOne = new Coordinate(world ,5, 15);
		World anotherWorld = new World(10, 12);
		Coordinate coordinateThree = new Coordinate(anotherWorld ,5, 15);

		assertFalse(coordinateOne.equals(coordinateThree));
		assertFalse(coordinateOne.equals(new Object()));
		
		//ensure child class is not equals to parent class
		class MyCoordinate extends Coordinate
		{
			public MyCoordinate() 
			{
				super(world, 5,15); 
			}
		}
		assertFalse(coordinateOne.equals(new MyCoordinate()));
	}
	
	@Test
	public void assertHashCodeEqualityAgainstExpectedHashcode()
	{
		World world = new World(20, 20)
		{
			@Override
			public int hashCode() 
			{
				return 10;
			}
		};
		
		Coordinate coordinateOne = new Coordinate(world ,17, 15);
		
		int expectedHashcode = 17+15+10;
		
		assertEquals(expectedHashcode, coordinateOne.hashCode());
	}
	
	@Test
	public void assertHashCodesAreEqualForCordinates()
	{
		Coordinate coordinateOne = new Coordinate(world ,17, 15);
		Coordinate coordinateTwo = new Coordinate(world ,7, 3);
		Coordinate coordinateThree = new Coordinate(world ,27, 27);
		//reflexive
		assertEquals(coordinateOne.hashCode(), coordinateOne.hashCode());
		//symmetric
		assertEquals(coordinateOne.hashCode(), coordinateTwo.hashCode());
		assertEquals(coordinateTwo.hashCode(), coordinateOne.hashCode());
		//transitive
		assertEquals(coordinateTwo.hashCode(), coordinateThree.hashCode());
		assertEquals(coordinateOne.hashCode(), coordinateThree.hashCode());
	}
	
	@Test
	public void assertHashCodesAreNotEqualForCordinatesWhenLocationValuesAreDifferent()
	{
		Coordinate coordinateOne = new Coordinate(world ,5, 15);
		Coordinate coordinateTwo = new Coordinate(world ,7, 3);
		
		assertFalse(coordinateOne.hashCode() == coordinateTwo.hashCode());
		assertFalse(coordinateTwo.hashCode() == coordinateOne.hashCode());
	}
	
	@Test
	public void assertHashCodesAreNotEqualForCordinatesWhenWorldsAreDifferent()
	{
		Coordinate coordinateOne = new Coordinate(world ,5, 15);
		
		World anotherWorld = new World(WIDTH_OF_WORLD);
		Coordinate coordinateTwo = new Coordinate(anotherWorld ,5, 15);
		
		assertFalse(coordinateOne.hashCode() == coordinateTwo.hashCode());
		assertFalse(coordinateTwo.hashCode() == coordinateOne.hashCode());
	}
}