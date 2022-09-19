package mate.controller.car;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.lib.Injector;
import mate.model.Car;
import mate.model.Manufacturer;
import mate.service.CarService;
import mate.service.ManufacturerService;

@WebServlet(urlPatterns = "/cars/create")
public class CreateCarController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("mate");
    private CarService carService = (CarService) injector.getInstance(CarService.class);
    private ManufacturerService manufacturerService =
            (ManufacturerService) injector.getInstance(ManufacturerService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/car/createCar.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String carModel = req.getParameter("carModel");
        String carManufacturerId = req.getParameter("manufacturer_id");
        Long manufacturerId = Long.parseLong(carManufacturerId);
        Manufacturer manufacturer = manufacturerService.get(manufacturerId);
        Car car = new Car();
        car.setModel(carModel);
        car.setManufacturer(manufacturer);
        car.setDrivers(new ArrayList<>());
        carService.create(car);
        resp.sendRedirect(req.getContextPath() + "/successful");
    }
}