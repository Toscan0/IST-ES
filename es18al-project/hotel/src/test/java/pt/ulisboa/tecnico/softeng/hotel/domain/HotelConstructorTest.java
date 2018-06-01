package pt.ulisboa.tecnico.softeng.hotel.domain;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.hotel.exception.HotelException;

public class HotelConstructorTest {
	private static final String HOTEL_NAME = "Londres";
	private static final String HOTEL_CODE = "XPTO123";
	private static final String NIF = "NIF";
	private static final String IBAN = "IBAN";
	private final double price1 = 124.5;
	private final double price2 = 224.5;

	@Test
	public void success() {
		Hotel hotel = new Hotel(HOTEL_CODE, HOTEL_NAME, NIF, IBAN, price1, price2);

		Assert.assertEquals(HOTEL_NAME, hotel.getName());
		Assert.assertTrue(hotel.getCode().length() == Hotel.CODE_SIZE);
		Assert.assertEquals(0, hotel.getNumberOfRooms());
		Assert.assertEquals(1, Hotel.hotels.size());
	}

	@Test(expected = HotelException.class)
	public void nullCode() {
		new Hotel(null, HOTEL_NAME, NIF, IBAN, price1, price2);
	}

	@Test(expected = HotelException.class)
	public void blankCode() {
		new Hotel("      ", HOTEL_NAME, NIF, IBAN, price1, price2);
	}

	@Test(expected = HotelException.class)
	public void emptyCode() {
		new Hotel("", HOTEL_NAME, NIF, IBAN, price1, price2);
	}

	@Test(expected = HotelException.class)
	public void nullName() {
		new Hotel(HOTEL_CODE, null, NIF, IBAN, price1, price2);
	}

	@Test(expected = HotelException.class)
	public void blankName() {
		new Hotel(HOTEL_CODE, "  ", NIF, IBAN, price1, price2);
	}

	@Test(expected = HotelException.class)
	public void emptyName() {
		new Hotel(HOTEL_CODE, "", NIF, IBAN, price1, price2);
	}

	@Test(expected = HotelException.class)
	public void codeSizeLess() {
		new Hotel("123456", HOTEL_NAME, NIF, IBAN, price1, price2);
	}

	@Test(expected = HotelException.class)
	public void codeSizeMore() {
		new Hotel("12345678", HOTEL_NAME, NIF, IBAN, price1, price2);
	}

	@Test(expected = HotelException.class)
	public void nullNif() {
		new Hotel(HOTEL_CODE, HOTEL_NAME, null, IBAN, price1, price2);
	}
	
	@Test(expected = HotelException.class)
	public void emptyNif() {
		new Hotel(HOTEL_CODE, HOTEL_NAME, "", IBAN, price1, price2);
	}

	@Test(expected = HotelException.class)
	public void blankNif() {
		new Hotel(HOTEL_CODE, HOTEL_NAME, "  ", IBAN, price1, price2);
	}

	@Test(expected = HotelException.class)
	public void nullIban() {
		new Hotel(HOTEL_CODE, HOTEL_NAME, NIF, null, price1, price2);
	}

	@Test(expected = HotelException.class)
	public void emptyIban() {
		new Hotel(HOTEL_CODE, HOTEL_NAME, NIF, "", price1, price2);
	}

	@Test(expected = HotelException.class)
	public void blankIban() {
		new Hotel(HOTEL_CODE, HOTEL_NAME, NIF, "  ", price1, price2);
	}
	
	@Test(expected = HotelException.class)
	public void zeroPriceSingle() {
		new Hotel(HOTEL_CODE, HOTEL_NAME, NIF, IBAN, 0, price2);
	}
	
	@Test(expected = HotelException.class)
	public void zeroPriceDouble() {
		new Hotel(HOTEL_CODE, HOTEL_NAME, NIF, IBAN, price1, 0);
	}
	
	@Test(expected = HotelException.class)
	public void negativePriceSingle() {
		new Hotel(HOTEL_CODE, HOTEL_NAME, NIF, IBAN, -123.4, price2);
	}
	
	@Test(expected = HotelException.class)
	public void negativePriceDouble() {
		new Hotel(HOTEL_CODE, HOTEL_NAME, NIF, IBAN, price1, -123.4);
	}
	
	@Test(expected = HotelException.class)
	public void codeNotUnique() {
		new Hotel(HOTEL_CODE, HOTEL_NAME, NIF, IBAN, price1, price2);
		new Hotel(HOTEL_CODE, HOTEL_NAME + " City", NIF + "2", IBAN, price1, price2);
	}

	@Test(expected = HotelException.class)
	public void nifNotUnique() {
		new Hotel(HOTEL_CODE, HOTEL_NAME, NIF, IBAN, price1, price2);
		new Hotel("1234567", HOTEL_NAME + " City", NIF, IBAN, price1, price2);
	}
	
	@After
	public void tearDown() {
		Hotel.hotels.clear();
	}

}
