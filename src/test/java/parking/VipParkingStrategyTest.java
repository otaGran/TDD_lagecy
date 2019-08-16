package parking;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class VipParkingStrategyTest {

    @Test
    public void testPark_givenAVipCarAndAFullParkingLog_thenGiveAReceiptWithCarNameAndParkingLotName() {

        /* Exercise 4, Write a test case on VipParkingStrategy.park()
         * With using Mockito spy, verify and doReturn */

        ParkingLot parkingLot = new ParkingLot("ParkingLot 1", 1);

        List<ParkingLot> parkingLotList = Arrays.asList(parkingLot);


        Car car = new Car("AlexVipCar");
        Car otherCar = new Car("OtherCar");
        parkingLot.getParkedCars().add(otherCar);

        VipParkingStrategy spyVipParkingStrategy = spy(new VipParkingStrategy());

        doReturn(true).when(spyVipParkingStrategy).isAllowOverPark(car);

        Receipt receipt = spyVipParkingStrategy.park(parkingLotList, car);

        assertEquals(car.getName(), receipt.getCarName());
        assertEquals(parkingLot.getName(), receipt.getParkingLotName());
    }

    @Test
    public void testPark_givenCarIsNotVipAndAFullParkingLog_thenGiveNoSpaceReceipt() {

        /* Exercise 4, Write a test case on VipParkingStrategy.park()
         * With using Mockito spy, verify and doReturn */

        ParkingLot parkingLot = new ParkingLot("ParkingLot 1", 1);

        List<ParkingLot> parkingLotList = Arrays.asList(parkingLot);


        Car car = new Car("AlexVipCar");
        Car otherCar = new Car("OtherCar");
        parkingLot.getParkedCars().add(otherCar);

        VipParkingStrategy spyVipParkingStrategy = spy(new VipParkingStrategy());

        doReturn(false).when(spyVipParkingStrategy).isAllowOverPark(car);

        Receipt receipt = spyVipParkingStrategy.park(parkingLotList, car);

        assertEquals(car.getName(), receipt.getCarName());
        assertEquals(ParkingStrategy.NO_PARKING_LOT, receipt.getParkingLotName());
    }

    @Test
    public void testIsAllowOverPark_givenCarNameContainsCharacterAAndIsVipCar_thenReturnTrue() {

        /* Exercise 5, Write a test case on VipParkingStrategy.isAllowOverPark()
         * You may refactor the code, or try to use
         * use @RunWith(MockitoJUnitRunner.class), @Mock (use Mockito, not JMockit) and @InjectMocks
         */
        Car car = new Car("AlexCarA");

        ParkingLot parkingLot = new ParkingLot("ParkingLot 1", 1);
        List<ParkingLot> parkingLotList = Arrays.asList(parkingLot);


        CarDaoImpl carDao = mock(CarDaoImpl.class);

        when(carDao.isVip("AlexCarA")).thenReturn(true);
        VipParkingStrategy spyVipParkingStrategy = spy(new VipParkingStrategy());

        doReturn(carDao).when(spyVipParkingStrategy).getCarDao();

        boolean allowOverPark = spyVipParkingStrategy.isAllowOverPark(car);

        assertTrue(allowOverPark);


    }

    @Test
    public void testIsAllowOverPark_givenCarNameDoesNotContainsCharacterAAndIsVipCar_thenReturnFalse() {

        /* Exercise 5, Write a test case on VipParkingStrategy.isAllowOverPark()
         * You may refactor the code, or try to use
         * use @RunWith(MockitoJUnitRunner.class), @Mock (use Mockito, not JMockit) and @InjectMocks
         */
        Car car = new Car("lexCar");

        ParkingLot parkingLot = new ParkingLot("ParkingLot 1", 1);
        List<ParkingLot> parkingLotList = Arrays.asList(parkingLot);


        CarDaoImpl carDao = mock(CarDaoImpl.class);

        when(carDao.isVip("lexCar")).thenReturn(true);
        VipParkingStrategy spyVipParkingStrategy = spy(new VipParkingStrategy());

        doReturn(carDao).when(spyVipParkingStrategy).getCarDao();

        boolean allowOverPark = spyVipParkingStrategy.isAllowOverPark(car);

        assertFalse(allowOverPark);
    }

    @Test
    public void testIsAllowOverPark_givenCarNameContainsCharacterAAndIsNotVipCar_thenReturnFalse() {
        /* Exercise 5, Write a test case on VipParkingStrategy.isAllowOverPark()
         * You may refactor the code, or try to use
         * use @RunWith(MockitoJUnitRunner.class), @Mock (use Mockito, not JMockit) and @InjectMocks
         */
        Car car = new Car("lexCar");

        ParkingLot parkingLot = new ParkingLot("ParkingLot 1", 1);
        List<ParkingLot> parkingLotList = Arrays.asList(parkingLot);


        CarDaoImpl carDao = mock(CarDaoImpl.class);

        when(carDao.isVip("lexCar")).thenReturn(false);
        VipParkingStrategy spyVipParkingStrategy = spy(new VipParkingStrategy());

        doReturn(carDao).when(spyVipParkingStrategy).getCarDao();

        boolean allowOverPark = spyVipParkingStrategy.isAllowOverPark(car);

        assertFalse(allowOverPark);
    }

    @Test
    public void testIsAllowOverPark_givenCarNameDoesNotContainsCharacterAAndIsNotVipCar_thenReturnFalse() {
        /* Exercise 5, Write a test case on VipParkingStrategy.isAllowOverPark()
         * You may refactor the code, or try to use
         * use @RunWith(MockitoJUnitRunner.class), @Mock (use Mockito, not JMockit) and @InjectMocks
         */

        Car car = new Car("lexCar");

        ParkingLot parkingLot = new ParkingLot("ParkingLot 1", 1);
        List<ParkingLot> parkingLotList = Arrays.asList(parkingLot);


        CarDaoImpl carDao = mock(CarDaoImpl.class);

        when(carDao.isVip("lexCar")).thenReturn(false);
        VipParkingStrategy spyVipParkingStrategy = spy(new VipParkingStrategy());

        doReturn(carDao).when(spyVipParkingStrategy).getCarDao();

        boolean allowOverPark = spyVipParkingStrategy.isAllowOverPark(car);

        assertFalse(allowOverPark);
    }

    private Car createMockCar(String carName) {
        Car car = mock(Car.class);
        when(car.getName()).thenReturn(carName);
        return car;
    }
}
