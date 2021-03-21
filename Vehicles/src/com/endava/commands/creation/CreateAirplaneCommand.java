package com.endava.commands.creation;

import static com.endava.commands.Constants.INVALID_NUMBER_OF_ARGUMENTS;
import static com.endava.commands.Constants.VEHICLE_CREATED_MESSAGE;

import com.endava.commands.contracts.Command;
import com.endava.core.contracts.VehiclesFactory;
import com.endava.core.contracts.VehiclesRepository;
import com.endava.models.vehicles.contracts.Airplane;
import com.endava.models.vehicles.contracts.Vehicle;
import java.util.List;

public class CreateAirplaneCommand implements Command {
  private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 3;

  private final VehiclesFactory factory;
  private final VehiclesRepository repository;
  private int loadCapacity;
  private double pricePerKgPerKilometer;
  private boolean isCharter;

  public CreateAirplaneCommand(VehiclesFactory factory, VehiclesRepository repository) {
    this.factory = factory;
    this.repository = repository;
  }

  @Override
  public String execute(List<String> parameters) {
    validateInput(parameters);
    parseParameters(parameters);
    Vehicle airplane = factory.createAirplane(loadCapacity, pricePerKgPerKilometer, isCharter);
    repository.addVehicle(airplane);
    return String.format(VEHICLE_CREATED_MESSAGE, airplane.getVehicleName(), repository.getVehicles().size() - 1);
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
      isCharter = Boolean.parseBoolean(parameters.get(2));
    } catch (Exception e) {
      throw new IllegalArgumentException("Failed to parse CreateAirplane command parameters.");
    }
  }
}