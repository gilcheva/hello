package com.endava.models;

import static org.junit.jupiter.api.Assertions.*;

import com.endava.models.contracts.Course;
import com.endava.models.contracts.Rent;
import com.endava.models.vehicles.AirplaneImpl;
import com.endava.models.vehicles.contracts.Vehicle;
import org.junit.Before;
import org.junit.jupiter.api.Test;

class RentImplTest {
  private Vehicle testVehicle;
  private Course testCourse;

  @Before
  public void before() {
    testVehicle = new AirplaneImpl(30, 20, true);
    testCourse = new CourseImpl("start","destination",30, testVehicle);
  }

  @Test
  public void constructor_should_throw_when_additionalCostsAreNull() {
    // Act and assert
    assertThrows(NullPointerException.class, () -> {
      Rent rent = new RentImpl(testCourse,null);
    });
  }

  @Test
  public void constructor_should_throw_when_courseIsNull() {
    // Act and assert
    assertThrows(IllegalArgumentException.class, () -> {
      Rent rent = new RentImpl(null,30.0);
    });
  }

}