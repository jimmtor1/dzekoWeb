package com.dzeko.controller;

import com.dzeko.model.Dimension;
import com.dzeko.model.DimensionRequest;
import com.dzeko.model.Order;
import com.dzeko.services.DimensionService;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/dimensions")
public class dimensionController {

    @Autowired
    private orderService orderService;

    @Autowired
    private DimensionService dimensionService;

    @GetMapping("{idorder}")
    public String page(Model model, HttpSession session, @PathVariable int idorder) {

        if (((Integer) session.getAttribute("userId")) == null) {
            return "login";
        }

        Order order = orderService.getOrderById(idorder);
        List<Dimension> dim = dimensionService.getDimensionsByIdorder(idorder);
        Dimension dimension = new Dimension();
        dimension.setQuantity(1);

        if (order.getIdorder() != null) {
            String formattedText = order.getText().replace("\n", "<br>");
            model.addAttribute("idorder", idorder);
            model.addAttribute("text", formattedText);
            model.addAttribute("dimension", dimension);

            if (dim != null && !dim.isEmpty()) {
                model.addAttribute("dimensions", dim);
            }

            return "dimensions";
        } else {
            return "redirect:/order";
        }

    }

    @PostMapping()
    public String saveDimensions(@ModelAttribute DimensionRequest dimensionRequest, RedirectAttributes redirectAttributes, HttpSession session) {

        Integer iduser = (Integer) session.getAttribute("userId");

        if (iduser == null) {
            return "redirect:/login";
        }

        try {

            this.dimensionService.saveDimension(dimensionRequest.getDimensions(), iduser);
            redirectAttributes.addFlashAttribute("success_message", "Dimenzije su uspješno dodane");

        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error_message", "Greška, neki od zapisa nisu mogli biti sačuvani.");
            Logger.getLogger(orderController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "redirect:/order";

    }

    @PostMapping("edit")
    public String editDimension(@ModelAttribute Dimension dimension, RedirectAttributes redirectAttributes, HttpSession session) {

        Integer iduser = (Integer) session.getAttribute("userId");

        if (iduser == null) {
            return "redirect:/login";
        }

        try {

            this.dimensionService.editDimension(dimension, iduser);
            redirectAttributes.addFlashAttribute("success_message", "Dimenzije su uspješno dodane");
            
            
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error_message", "Greška, neki od zapisa nisu mogli biti sačuvani.");
            Logger.getLogger(orderController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "redirect:/dimensions/" + dimension.getOrderid();

    }

    @GetMapping("delete/{iddimension}")
    public String delete(@PathVariable Integer iddimension, HttpSession session, RedirectAttributes redirectAttributes) {

        if (((Integer) session.getAttribute("userId")) == null) {
            return "login";
        }

        Dimension d = this.dimensionService.getDimensionsByIddimension(iddimension);

        if (d != null) {

            if (this.dimensionService.deleteDimension(iddimension) == 1) {
//                redirectAttributes.addFlashAttribute("success_message", "Uspješno uklonjeno");
            } else {
                redirectAttributes.addFlashAttribute("error_message", "Nije uklonjeno.");
            }
            return "redirect:/dimensions/" + d.getOrderid();
        }

        return "redirect:/order";

    }

}
