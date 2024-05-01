package sit707_week7;

import org.junit.Assert;
import org.junit.Test;
import static org.mockito.Mockito.*;

public class BodyTemperatureMonitorTest {

	@Test
	public void testStudentIdentity() {
		String studentId = "s223123562";
		Assert.assertNotNull("Student ID is null", studentId);
	}

	@Test
	public void testStudentName() {
		String studentName = "Zainab";
		Assert.assertNotNull("Student name is null", studentName);
	}
	
    
    
    @Test
    public void testReadTemperatureNegative() {
        TemperatureSensor temperatureSensor = mock(TemperatureSensor.class);
        when(temperatureSensor.readTemperatureValue()).thenReturn(-1.0);
        BodyTemperatureMonitor monitor = new BodyTemperatureMonitor(temperatureSensor, null, null);
        double temperature = monitor.readTemperature();
        Assert.assertEquals(-1.0, temperature, 0.01);
    }
    
    @Test
    public void testReadTemperatureZero() {
        TemperatureSensor temperatureSensor = mock(TemperatureSensor.class);
        when(temperatureSensor.readTemperatureValue()).thenReturn(0.0);
        BodyTemperatureMonitor monitor = new BodyTemperatureMonitor(temperatureSensor, null, null);
        double temperature = monitor.readTemperature();
        Assert.assertEquals(0.0, temperature, 0.01);
    }
   
    
    @Test
    public void testReadTemperatureNormal() {
        TemperatureSensor temperatureSensor = mock(TemperatureSensor.class);
        when(temperatureSensor.readTemperatureValue()).thenReturn(36.5);
        BodyTemperatureMonitor monitor = new BodyTemperatureMonitor(temperatureSensor, null, null);
        double temperature = monitor.readTemperature();
        Assert.assertEquals(36.5, temperature, 0.01);
    }

    @Test
    public void testReadTemperatureAbnormallyHigh() {
        TemperatureSensor temperatureSensor = mock(TemperatureSensor.class);
        when(temperatureSensor.readTemperatureValue()).thenReturn(40.0);
        BodyTemperatureMonitor monitor = new BodyTemperatureMonitor(temperatureSensor, null, null);
        double temperature = monitor.readTemperature();
        Assert.assertEquals(40.0, temperature, 0.01);
    }
    

	/*
	 * CREDIT or above level students, Remove comments. 
	 */
	@Test
    public void testReportTemperatureReadingToCloud() {
        CloudService cloudService = mock(CloudService.class);
        TemperatureReading temperatureReading = new TemperatureReading();
        temperatureReading.setTemperature(36.5);
        BodyTemperatureMonitor monitor = new BodyTemperatureMonitor(null, cloudService, null);
        monitor.reportTemperatureReadingToCloud(temperatureReading);
        verify(cloudService, times(1)).sendTemperatureToCloud(temperatureReading);
    }

	
	
	/*
	 * CREDIT or above level students, Remove comments. 
	 */

	@Test
    public void testInquireBodyStatusNormalNotification() {
        CloudService cloudService = mock(CloudService.class);
        NotificationSender notificationSender = mock(NotificationSender.class);
        BodyTemperatureMonitor monitor = new BodyTemperatureMonitor(null, cloudService, notificationSender);
        when(cloudService.queryCustomerBodyStatus(any(Customer.class))).thenReturn("NORMAL");
        monitor.inquireBodyStatus();
        verify(notificationSender, times(1)).sendEmailNotification(any(Customer.class), anyString());
    }
	
	/*
	 * CREDIT or above level students, Remove comments. 
	 */

	@Test
    public void testInquireBodyStatusAbnormalNotification() {
        CloudService cloudService = mock(CloudService.class);
        NotificationSender notificationSender = mock(NotificationSender.class);
        BodyTemperatureMonitor monitor = new BodyTemperatureMonitor(null, cloudService, notificationSender);
        when(cloudService.queryCustomerBodyStatus(any(Customer.class))).thenReturn("ABNORMAL");
        monitor.inquireBodyStatus();
        verify(notificationSender, times(1)).sendEmailNotification(any(FamilyDoctor.class), anyString());
    }
}
