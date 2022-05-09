package br.com.feliciano.mvc.domain.entities;

import java.util.List;

import br.com.feliciano.mvc.controller.InventoryController;
import br.com.feliciano.mvc.dto.InventoryDTO;

public class TestByDescription {

    public static void main(String[] args) {

      InventoryController controller = new InventoryController();
    	  List<InventoryDTO> listInventoriesVo = controller.findByDescription("SP");
    	  listInventoriesVo.forEach(System.out::println);
    }

}
