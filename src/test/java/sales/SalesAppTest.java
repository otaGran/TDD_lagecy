package sales;

import org.junit.Test;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class SalesAppTest {

	@Test
	public void testGenerateReport() {
		
		SalesApp salesApp = new SalesApp();
		salesApp.generateSalesActivityReport("DUMMY", 1000, false, false);
		
	}

	@Test
	public void should_return_true_when_sales_date_is_valid() {

		SalesApp salesApp = new SalesApp();
		Sales sales = spy(new Sales());


		Calendar fromDay = Calendar.getInstance();
		fromDay.set(Calendar.DATE, fromDay.get(Calendar.DATE) - 100);
		doReturn(fromDay.getTime()).when(sales).getEffectiveFrom();
		Calendar toDay = Calendar.getInstance();
		toDay.set(Calendar.DATE, toDay.get(Calendar.DATE) + 100);
		doReturn(toDay.getTime()).when(sales).getEffectiveTo();

		boolean res = salesApp.isValidDate(sales);


		assertTrue(res);
	}

	@Test
	public void should_return_sales_id_sales_name_activity_time_when_nat_trade() {
		List<String> headers = Arrays.asList("Sales ID", "Sales Name", "Activity", "Time");
		SalesApp salesApp = spy(new SalesApp());
		assertEquals(headers, salesApp.getHeaders(true));
	}

	@Test
	public void should_generate_template_list_when_given_maxRows(){
		List<SalesReportData> oriList = Arrays.asList(new SalesReportData(), new SalesReportData(), new SalesReportData());
		int maxRows = 2;
		SalesApp salesApp = spy(new SalesApp());

		assertEquals(oriList.subList(0,1), salesApp.generateTempList());
	}


}
