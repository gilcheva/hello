package com.endava.commands.creation;

import com.endava.commands.Command;
import com.endava.core.repositories.VehiclesRepositorySQLImpl;
import com.endava.core.VehiclesFactory;
import com.endava.core.VehiclesRepository;
import com.endava.core.factories.VehiclesFactoryImpl;
import com.endava.models.courses.impl.CourseImpl;
import com.endava.models.vehicles.impl.AirplaneImpl;
import com.endava.models.vehicles.Airplane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

class CreateRentCommandTest {

    private VehiclesRepository repository;
    private VehiclesFactory factory;
    private Command testCommand;

    @BeforeEach
    public void before() {
        this.factory = new VehiclesFactoryImpl();
        this.repository = new VehiclesRepositorySQLImpl();
        this.testCommand = new CreateRentCommand(factory, repository);
    }

    @Test
    public void execute_should_throwException_when_passedFewerArgumentsThanExpected() {
        // Arrange, act, assert
        assertThrows(IllegalArgumentException.class, () -> {
            testCommand.execute(asList(new String[1]));
        });
    }

    @Test
    public void execute_should_throwException_when_passedMoreArgumentsThanExpected() {
        // Arrange, act, assert
        assertThrows(IllegalArgumentException.class, () -> {
            testCommand.execute(asList(new String[3]));
        });
    }

    @Test
    public void execute_should_throwException_when_passedInvalidIndex() {
        // Arrange
        List<String> arguments = new ArrayList<>();
        arguments.add("f");
        arguments.add("20");
        repository.addCourse(new CourseImpl( "start","destination",30, new AirplaneImpl("С7320В", 30, 20, true)));

        // Act
        assertThrows(IllegalArgumentException.class, () -> {
            testCommand.execute(arguments);
        });
    }

    @Test
    public void execute_should_throwException_when_passedNonexistingIndex() {
        // Arrange
        List<String> arguments = new ArrayList<>();
        arguments.add("1");
        arguments.add("20");
        repository.addCourse(new CourseImpl( "start","destination",30, new AirplaneImpl("С7320В", 30, 20, true)));

        // Act
        assertThrows(IndexOutOfBoundsException.class, () -> {
            testCommand.execute(arguments);
        });
    }

    @Test
    public void execute_should_throwException_when_passedInvalidAdministrativeCosts() {
        // Arrange
        List<String> arguments = new ArrayList<>();
        arguments.add("0");
        arguments.add("2gg");

        // Act
        assertThrows(IllegalArgumentException.class, () -> {
            testCommand.execute(arguments);
        });
    }

    @Test
    public void execute_should_throwException_when_passedNullCourse() {
        // Arrange
        List<String> arguments = new ArrayList<>();
        arguments.add("0");
        arguments.add("20");
        repository.addCourse(null);

        // Act
        assertThrows(IllegalArgumentException.class, () -> {
            testCommand.execute(arguments);
        });
    }

    @Test
    public void execute_should_addNewRent_when_passedValidInput() throws SQLException {
        // Arrange
        Airplane vehicle = new AirplaneImpl("С7320В", 30, 20, true);
        repository.addPlane(vehicle);
        repository.addCourse(new CourseImpl( "start","destination",30, vehicle));

        // Act
        testCommand.execute(asList("0", "20"));

        // Assert
        assertEquals(1, repository.getRents().size());
        assertEquals("RentImpl", repository.getRents().get(0).getClass().getSimpleName());
    }
}