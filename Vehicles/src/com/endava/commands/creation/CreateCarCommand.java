package com.endava.commands.creation;

import static com.endava.commands.Constants.INVALID_NUMBER_OF_ARGUMENTS;
import static com.endava.commands.Constants.VEHICLE_CREATED_MESSAGE;

import com.endava.commands.contracts.Command;
import com.endava.core.contracts.VehiclesFactory;
import com.endava.core.contracts.VehiclesRepository;
import com.endava.models.vehicles.contracts.Car;
import com.endava.models.vehicles.contracts.Vehicle;
import com.endava.models.vehicles.enums.VehicleType;
import java.util.List;

public class CreateCarCommand implements Command {
  private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 3;

  private final VehiclesFactory factory;
  private final VehiclesRepository repository;
  private int loadCapacity;
  private double pricePerKgPerKilometer;
  private VehicleType type;

  public CreateCarCommand(VehiclesFactory factory, VehiclesRepository repository) {
    this.factory = factory;
    this.repository = repository;
  }

  @Override
  public String execute(List<String> parameters) {
    validateInput(parameters);
    parseParameters(parameters);
    Vehicle car = factory.createCar(loadCapacity, pricePerKgPerKilometer, type);
    repository.addVehicle(car);
    return String.format(VEHICLE_CREATED_MESSAGE, car.getVehicleName(), repository.getVehicles().size() - 1);
  }

  private void validateInput(List<String> parameters) {
    if (parameters.size() != EXPECTED_NUMBER_OF_ARGUMENTS) {
      throw new IllegalArgumentException(String.format(INVALID_NUMBER_OF_ARGUMENTS, EXPECTED_NUMBER_OF_ARGUMENTS, parameters.size()));
    }
  }

  private void parseParameters(List<String> parameters) {
    try {
      loadCapacity = Integer.parseInt(parameters.get(0));
      pricePerKgPerKilometer = Double.parseDouble(parameters.get(1));
      type = VehicleType.byOrdinal(Integer.parseInt(parameters.get(2)));
    } catch (Exception e) {
      throw new IllegalArgumentException("Failed to parse CreateCar command parameters.");
    }
  }
}