package com.dzeko.services;

import com.dzeko.model.Order;
import com.dzeko.repository.OrderRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class orderService {

    @Autowired
    private OrderRepository orderRepository;
   
    @Transactional
    public Integer saveOrder(Order order) throws Exception {

        //save order
        Integer orderid = this.orderRepository.save(order);

        if (orderid == null) {
            throw new RuntimeException("Error al guardar los datos en la tabla orders.");
        }

        return orderid;

    }

    @Transactional
    public Integer editOrder(Order order) throws Exception {

        //edit order
        Integer rs = this.orderRepository.edit(order);

        if (rs == null) {
            throw new RuntimeException("Error al guardar los datos en la tabla orders.");
        }

        return rs;

    }

    public int archiveOrder(int idorder, boolean state) {
        //update order
        return this.orderRepository.finishOrder(idorder,state);
    }

    public int approbeOrder(int orderid) {
        return orderRepository.approbeOrder(orderid);
    }

    public List<Order> getOrderByFinished(boolean finished) {
        return orderRepository.getOrderByFinished(finished);
    }

    public List<Order> getOrderByApprove(boolean approved) {
        return orderRepository.getOrderByApprove(approved);
    }

    public Order getOrderById(int idorder) {
        return orderRepository.getOrderByIdorder(idorder);
    }

    public List<Order> searchByCliente(String keyword) {
        return orderRepository.getByClientKeyword(keyword);
    }

}
