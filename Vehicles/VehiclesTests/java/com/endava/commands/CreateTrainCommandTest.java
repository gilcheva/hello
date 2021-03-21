package com.endava.commands;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.endava.commands.contracts.Command;
import com.endava.commands.creation.CreateTrainCommand;
import com.endava.core.VehiclesRepositoryImpl;
import com.endava.core.contracts.VehiclesFactory;
import com.endava.core.contracts.VehiclesRepository;
import com.endava.core.factories.VehiclesFactoryImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CreateTrainCommandTest {

  private VehiclesRepository repository;
  private VehiclesFactory factory;
  private Command testCommand;

  @BeforeEach
  public void before() {
    this.factory = new VehiclesFactoryImpl();
    this.repository = new VehiclesRepositoryImpl();
    this.testCommand = new CreateTrainCommand(factory, repository);
  }

  @Test
  public void execute_should_throwException_when_passedFewerArgumentsThanExpected() {
    // Arrange, act, assert
    assertThrows(IllegalArgumentException.class, () -> {
      testCommand.execute(asList(new String[2]));
    });
  }

  @Test
  public void execute_should_throwException_when_passedMoreArgumentsThanExpected() {
    // Arrange, act and assert
    assertThrows(IllegalArgumentException.class, () -> {
      testCommand.execute(asList(new String[4]));
    });
  }

  @Test
  public void execute_should_throwException_when_passedInvalidLoadCapacity() {
    // Arrange
    List<String> arguments = new ArrayList<>();
    arguments.add("ff");
    arguments.add("2");
    arguments.add("3");

    // Act and assert
    assertThrows(IllegalArgumentException.class, () -> {
      testCommand.execute(arguments);
    });
  }

  @Test
  public void execute_should_throwException_when_passedInvalidPricePerKgPerKm() {
    // Arrange
    List<String> arguments = new ArrayList<>();
    arguments.add("30");
    arguments.add("f");
    arguments.add("3");

    // Act and assert
    assertThrows(IllegalArgumentException.class, () -> {
      testCommand.execute(arguments);
    });
  }

  @Test
  public void execute_should_addNewShip_when_passedValidInput() {
    // Arrange, Act
    testCommand.execute(asList("30", "2", "3"));

    // Assert
    assertEquals(1, repository.getVehicles().size());
    assertEquals("TrainImpl", repository.getVehicles().get(0).getClass().getSimpleName());
  }

}