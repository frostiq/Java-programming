package by.bazhanau.task11;

import Bazhanau.Task8.IRmiServer;
import Bazhanau.Task8.Models.Item;
import Bazhanau.Task8.Models.Storage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "MainServlet", urlPatterns = "/main/*")
public class MainServlet extends HttpServlet {
    IRmiServer storage;

    public MainServlet() throws RemoteException, NotBoundException {
            Registry registry = LocateRegistry.getRegistry("localhost", 16666);
            storage = (IRmiServer) registry.lookup("Server");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        initView(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String operation = request.getRequestURI().substring(6);

        switch (operation) {
            case "CreateEmptyItem": {
                storage.createNewItem();
            }
            break;
            case "UpdateItem": {
                Item item = new Item();
                item.setId(Integer.parseInt(request.getParameter("id")));
                item.setName(request.getParameter("name"));
                item.setPrice(Integer.parseInt(request.getParameter("price")));
                item.setQuantity(Integer.parseInt(request.getParameter("quantity")));
                item.setStorage(new Storage(Integer.parseInt(request.getParameter("storageId"))));

                storage.updateItem(item);
            }
            break;
            case "RemoveItem": {
                int id = Integer.parseInt(request.getParameter("id"));
                storage.removeItem(id);
            }
            break;
        }
        initView(request, response);
    }

    private void initView(HttpServletRequest request, HttpServletResponse response){
        List<Item> items = new ArrayList<>();
        String name = request.getParameter("filter");
        try {
            if (name != null && !name.isEmpty()) {
                items = storage.getItem(name);
            } else {
                for (int id : storage.getItemIds()) {
                    items.add(storage.getItem(id));
                }
            }
            request.setAttribute("items", items);
            getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
