package parking;

import javafx.beans.binding.BooleanExpression;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;


public class InOrderParkingStrategyTest {
    final private String CAR_NAME_A = "123456";
    final private String CAR_NAME_B = "456789";
    final private String PARKING_NAME_A = "654321";
	@Test
    public void testCreateReceipt_givenACarAndAParkingLog_thenGiveAReceiptWithCarNameAndParkingLotName() {

	    /* Exercise 1, Write a test case on InOrderParkingStrategy.createReceipt()
	    * With using Mockito to mock the input parameter */
        InOrderParkingStrategy inOrderParkingStrategy = new InOrderParkingStrategy();
	    ParkingLot parkingLot = mock(ParkingLot.class);

	    when(parkingLot.getName()).thenReturn(PARKING_NAME_A);
	    Car car = new Car(CAR_NAME_A);

	    Receipt receipt = inOrderParkingStrategy.createReceipt(parkingLot,car);
        Assert.assertEquals(CAR_NAME_A, receipt.getCarName());
        Assert.assertEquals(PARKING_NAME_A, receipt.getParkingLotName());

        //assertThat(inOrderParkingStrategy.createReceipt(new ParkingLot("123",123),new Car("xxx")).getParkingLotName()).isEqualTo("123");
    }


    @Test
    public void testCreateNoSpaceReceipt_givenACar_thenGiveANoSpaceReceipt() {

        /* Exercise 1, Write a test case on InOrderParkingStrategy.createNoSpaceReceipt()
         * With using Mockito to mock the input parameter */

        InOrderParkingStrategy inOrderParkingStrategy = new InOrderParkingStrategy();
        ParkingLot parkingLot = mock(ParkingLot.class);

        when(parkingLot.getName()).thenReturn(PARKING_NAME_A);
        Car car = new Car(CAR_NAME_A);

        Receipt receipt = inOrderParkingStrategy.createReceipt(parkingLot,car);

        verify(car, times(1)).getName();
        Assert.assertEquals(CAR_NAME_A, receipt.getCarName());
        Assert.assertEquals(PARKING_NAME_A, receipt.getCarName());

    }

    @Test
    public void testPark_givenNoAvailableParkingLot_thenCreateNoSpaceReceipt(){

	    /* Exercise 2: Test park() method. Use Mockito.spy and Mockito.verify to test the situation for no available parking lot */
        Car car = new Car(CAR_NAME_A);



       // InOrderParkingStrategy inOrderParkingStrategy = new InOrderParkingStrategy();

        InOrderParkingStrategy inOrderParkingStrategy = spy(new InOrderParkingStrategy());

        Receipt receipt =  inOrderParkingStrategy.park(null, car);
        //Receipt receipt =  inOrderParkingStrategy.park(new ArrayList<ParkingLot>(), car);


        verify(inOrderParkingStrategy, times(1)).park(null, car);

        //Assert.assertEquals(PARKING_NAME_A, receipt.getCarName());

    }

    @Test
    public void testPark_givenThereIsOneParkingLotWithSpace_thenCreateReceipt(){

        /* Exercise 2: Test park() method. Use Mockito.spy and Mockito.verify to test the situation for one available parking lot */
        Car car = new Car(CAR_NAME_A);


        ParkingLot parkingLot = new ParkingLot("AlexParkingLot",1);
        List<ParkingLot> parkingLotArrayList = Arrays.asList(parkingLot);
        // InOrderParkingStrategy inOrderParkingStrategy = new InOrderParkingStrategy();

        InOrderParkingStrategy inOrderParkingStrategy = spy(new InOrderParkingStrategy());

        //Receipt receipt =  inOrderParkingStrategy.park(null, car);
        Receipt receipt =  inOrderParkingStrategy.park(parkingLotArrayList, car);

        assertEquals(car, parkingLot.getParkedCars().get(0));
        verify(inOrderParkingStrategy, times(1)).createReceipt(parkingLot, car);
    }

    @Test
    public void testPark_givenThereIsOneFullParkingLot_thenCreateReceipt(){

        /* Exercise 2: Test park() method. Use Mockito.spy and Mockito.verify to test the situation for one available parking lot but it is full */

        Car car = new Car(CAR_NAME_A);
        Car otherCar = new Car(CAR_NAME_B);


        ParkingLot parkingLot = new ParkingLot("AlexParkingLot",1);
        List<ParkingLot> parkingLotArrayList = Arrays.asList(parkingLot);
        parkingLot.getParkedCars().add( otherCar);
        // InOrderParkingStrategy inOrderParkingStrategy = new InOrderParkingStrategy();

        InOrderParkingStrategy inOrderParkingStrategy = spy(new InOrderParkingStrategy());

        //Receipt receipt =  inOrderParkingStrategy.park(null, car);
        Receipt receipt =  inOrderParkingStrategy.park(parkingLotArrayList, car);


        verify(inOrderParkingStrategy, times(0)).createReceipt(parkingLot, car);
        assertEquals(CAR_NAME_A,receipt.getCarName());
        assertEquals(ParkingStrategy.NO_PARKING_LOT,receipt.getParkingLotName());




    }

    @Test
    public void testPark_givenThereIsMultipleParkingLotAndFirstOneIsFull_thenCreateReceiptWithUnfullParkingLot(){

        /* Exercise 3: Test park() method. Use Mockito.spy and Mockito.verify to test the situation for multiple parking lot situation */
        Car car = new Car(CAR_NAME_A);
        Car otherCar = new Car(CAR_NAME_B);


        ParkingLot parkingLot = new ParkingLot("AlexParkingLot",1);
        ParkingLot otherParkingLot = new ParkingLot("OtherParkingLot",1);
        List<ParkingLot> parkingLotArrayList = Arrays.asList(parkingLot,otherParkingLot);
        parkingLot.getParkedCars().add(otherCar);
        // InOrderParkingStrategy inOrderParkingStrategy = new InOrderParkingStrategy();

        InOrderParkingStrategy inOrderParkingStrategy = spy(new InOrderParkingStrategy());

        //Receipt receipt =  inOrderParkingStrategy.park(null, car);
        Receipt receipt =  inOrderParkingStrategy.park(parkingLotArrayList, car);


        verify(inOrderParkingStrategy, times(1)).createReceipt(otherParkingLot, car);
        assertEquals(CAR_NAME_A,receipt.getCarName());
        assertEquals(otherParkingLot.getName(),receipt.getParkingLotName());
    }


}
