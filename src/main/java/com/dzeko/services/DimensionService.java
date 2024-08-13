package com.dzeko.services;

import com.dzeko.model.Dimension;
import com.dzeko.repository.DimensionRepository;
import com.dzeko.repository.OrderRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DimensionService {

    @Autowired
    private DimensionRepository dimensionRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    public void saveDimension(List<Dimension> dimensions, int iduser) throws Exception {

        //save dimensions
        orderRepository.dimensionsOrder(dimensions.get(0).getOrderid(), iduser);

        int[] result = this.dimensionRepository.saveAll(dimensions);

        for (int r : result) {
            if (r != 1) {
                throw new RuntimeException("Error al guardar los datos.");
            }
        }

    }

    public List<Dimension> getDimensionsByIdorder(int idorder) {
        return dimensionRepository.getDimensionsByIdorder(idorder);
    }

    public Dimension getDimensionsByIddimension(int iddimension) {
        return dimensionRepository.getDimensionsByIddimension(iddimension);
    }

    @Transactional
    public void editDimension(Dimension dimension, int iduser) {

        orderRepository.updateUser(iduser, dimension.getOrderid());
        int d = 0;

        //edit dimension
        if (dimension.getIddimension() != null) {

            d = this.dimensionRepository.editOne(dimension);

        } else {

            d = this.dimensionRepository.addOne(dimension);
            
        }

        if (d != 1) {
            throw new RuntimeException("Error al guardar los datos");
        }

        
    }

    public int deleteDimension(Integer iddimension) {
        return this.dimensionRepository.deleteOne(iddimension);
    }

}
