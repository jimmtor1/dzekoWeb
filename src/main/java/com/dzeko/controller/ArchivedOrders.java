package com.dzeko.controller;

import com.dzeko.model.Order;
import com.dzeko.services.orderService;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/archived")
public class ArchivedOrders {

    @Autowired
    private orderService orderService;

    @GetMapping
    public String page(Model model, HttpSession session) {

        if (((Integer) session.getAttribute("userId")) == null) {
            return "login";
        }

        List<Order> orders = orderService.getOrderByFinished(true);

        model.addAttribute("orders", orders);

        return "archivedOrders";
    }

    @GetMapping("{idorder}")
    public String unarchive(Model model, HttpSession session, @PathVariable int idorder, RedirectAttributes redirectAttributes) {

        if (((Integer) session.getAttribute("userId")) == null) {
            return "login";
        }

        int r = orderService.archiveOrder(idorder, false);

        if (r > 0) {
            redirectAttributes.addFlashAttribute("success_message", "Narudžba je obnovljena!");
        } else {
            redirectAttributes.addFlashAttribute("error_message", "Greška, narudžbu nije moguće obnoviti.");
        }

        return "redirect:/archived";
    }

    @GetMapping("search")
    public String search(Model model, HttpSession session, @RequestParam("keyword") String keyword, RedirectAttributes redirectAttributes) {

        if (((Integer) session.getAttribute("userId")) == null) {
            return "login";
        }

        List<Order> orders = orderService.searchByCliente(keyword);

        if (orders.isEmpty()) {
            redirectAttributes.addFlashAttribute("error_message", "Nije pronađen nijedan zapis");
            return "redirect:/archived";
        } else {
            model.addAttribute("orders", orders);
            return "archivedOrders";
        }

    }

}
