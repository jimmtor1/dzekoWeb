package com.dzeko.controller;

import com.dzeko.model.Order;
import com.dzeko.services.orderService;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("order")
public class orderController {

    @Autowired
    private orderService orderService;

    @GetMapping
    public String home(HttpSession session, Model model) {
        if (((Integer) session.getAttribute("userId")) == null) {
            return "login";
        }

        List<Order> orders = null;
        if (session.getAttribute("role").equals("2")) {
            orders = orderService.getOrderByFinished(false);
        } else if (session.getAttribute("role").equals("12")) {
            orders = orderService.getOrderByApprove(true);
        }

        model.addAttribute("orders", orders);
        model.addAttribute("role", ((String) session.getAttribute("role")));

        return "order";
    }

    @GetMapping("form")
    public String form(HttpSession session, Model model, @ModelAttribute("order") Order order) {
        if (((Integer) session.getAttribute("userId")) == null) {
            return "login";
        }

        if (model.getAttribute("order") == null) {
            Order order2 = new Order();
            order2.setText(text);
            model.addAttribute("order", order2);
        } else {
            order.setText(text);
        }

        return "orderForm";
    }

    @PostMapping
    public String saveOrders(@ModelAttribute Order order, RedirectAttributes redirectAttributes, HttpSession session) {

        Integer userid = (Integer) session.getAttribute("userId");

        if (userid == null) {
            return "redirect:/login";
        }
        try {
            order.setCreator_user_id(userid);

            if (order.getIdorder() == null) {
                this.orderService.saveOrder(order);
            } else {
                this.orderService.editOrder(order);
            }

//            order.setIdorder(idorder);
            redirectAttributes.addFlashAttribute("success_message", "Narudžba je uspješno dodana!");
//            redirectAttributes.addFlashAttribute("order", order);
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error_message", "Greška, neki od zapisa nisu mogli biti sačuvani.");
            Logger.getLogger(orderController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "redirect:/order";

    }

    public List<Order> clientsNotFinished() {
        List<Order> orders = orderService.getOrderByFinished(false);

        return orders;
    }

    @PostMapping("approve")
    public String approveOrder(@RequestParam("idorder") int idorder, HttpSession session) {

        if (((Integer) session.getAttribute("userId")) == null) {
            return "login";
        }

        orderService.approbeOrder(idorder);

        return "redirect:/order";

    }

    @PostMapping("edit")
    public String EditOrder(@RequestParam("idorder") int idorder, HttpSession session, Model model) {

        if (((Integer) session.getAttribute("userId")) == null) {
            return "login";
        }

        Order order = orderService.getOrderById(idorder);
        model.addAttribute("order", order);

        return "orderForm";

    }

    @GetMapping("archive/{idorder}")
    public String archiveOrder(@PathVariable int idorder, RedirectAttributes redirectAttributes) {
        int r = orderService.archiveOrder(idorder, true);

        if (r > 0) {
            redirectAttributes.addFlashAttribute("success_message", "Uspješno arhivirano!");
        } else {
            redirectAttributes.addFlashAttribute("error_message", "Greška, narudžbu nije moguće arhivirati.");
        }

        return "redirect:/order";
    }

    private final String text = """
                                Model: 
                                \u0160tok: 
                                Spoj lajsni:  
                                Boja: 
                                Obrezivanje: 
                                \u0160arke: 
                                Brava: 
                                \u0160teke: 
                                Staklena: 
                                Ispuna krila: 
                                Ventilacijska re\u0161etka: 
                                
                                UGRADNJA:""";

}
