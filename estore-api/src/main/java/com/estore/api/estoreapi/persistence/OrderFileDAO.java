package com.estore.api.estoreapi.persistence;

import java.util.Map;
import java.util.logging.Logger;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import java.io.File;
import java.util.TreeMap;

import com.estore.api.estoreapi.model.Order;

@Component
public class OrderFileDAO implements OrderDAO {
    private static final Logger LOG = Logger.getLogger(OrderFileDAO.class.getName());
    
    /** Maps ids to their corresponding {@link Order} */
    Map<Integer, Order> orderMap;

    /** JSON serializer/deserializer */
    private ObjectMapper objectMapper;

    /** Next id to assign */
    private static int nextId;

    private String filename;

    /**
     * Build the {@link OrderFileDAO}
     * 
     * @param filename Filename to store the data in
     * @param objectMapper JSON serializer/deserializer
     * 
     * @throws IOException If there is an error reading the file
     */
    public OrderFileDAO(@Value("${order.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();
    }

    /**
     * Gets next id and increments it
     * 
     * Syncronized to prevent multiple threads from accessing nextId concurrently
     * 
     * @return The next id to assign
     */
    private synchronized static int getNextId() {
        return nextId++;
    }

    /**
     * Gets array of {@link Order orders} from the file
     * 
     * @return Array of stored {@link Order orders}, or empty array if none
     */
    private Order[] getOrdersArray() {
        ArrayList<Order> orderList = new ArrayList<>();
        for(Order currentOrder: orderMap.values()) {
            orderList.add(currentOrder);
        }
        Order[] returnValue = new Order[orderList.size()];
        orderList.toArray(returnValue);
        return returnValue;
    }

    /**
     * Saves {@link Order} to the DAO file
     * 
     * @throws IOException If there is an error writing to the file
     */
    private void save() throws IOException{
        Order[] allOrders = getOrdersArray();

        // Uses object mapper to convert to json and write to file
        objectMapper.writeValue(new File(filename), allOrders);
    }

    /**
     * Load all {@link Order} from the DAO file and store in the map
     * 
     * @throws IOException If there is an error reading the file
     */
    private void load() throws IOException {
        orderMap = new TreeMap<>();
        nextId = 0;

        Order[] serializedOrders = objectMapper.readValue(new File(filename), Order[].class);

        // Add all orders and keep track of the greatest id
        for (Order order: serializedOrders) {
            orderMap.put(order.getId(), order);
            if (order.getId() > nextId) {
                nextId = order.getId();
            }
        }

        // Incremement id again so it is ready to return the next id
        getNextId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Order[] getOrders() {
        // Synchronize on OrderMap so that it can't be modified while being read
        synchronized(orderMap) {
            return getOrdersArray();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Order getOrder(int id) {
        synchronized(orderMap) {
            return orderMap.get(id);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Order createOrder(Order order) throws IOException {
        synchronized(orderMap) {
            Order tmpOrd = new Order(getNextId(), order.getTotalPrice(), order.getProducts(), order.isComplete(), order.getDateTime());
            
            // Add to map and save to DAO
            orderMap.put(tmpOrd.getId(), tmpOrd);
            save();

            return tmpOrd;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteOrder(int id) throws IOException {
        synchronized (orderMap) {
            if (!orderMap.containsKey(id)) {
                return false;
            }
            else {
                orderMap.remove(id);
                save();
                return true;
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Order updateOrder(Order order) throws IOException {
        // TODO Auto-generated method stub
        synchronized(orderMap){
            if(!orderMap.containsKey(order.getId())){
                return null;
            }

            orderMap.put(order.getId(), order);
            save();

            return order;
        }
    }
}
