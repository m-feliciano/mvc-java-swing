package br.com.feliciano.mvc.controller;

import java.sql.Connection;
import java.util.List;

import br.com.feliciano.mvc.domain.entities.Inventory;
import br.com.feliciano.mvc.domain.entities.vo.InventoryVO;
import br.com.feliciano.mvc.infra.ConnectionFactory;
import br.com.feliciano.mvc.infra.dao.InventoryDAO;

public class InventoryController {

    private final InventoryDAO inventoryDAO;

    public InventoryController() {
        Connection conn = new ConnectionFactory().getConnection();
        this.inventoryDAO = new InventoryDAO(conn);
    }

    public void save(Inventory inventory) {
        this.inventoryDAO.save(inventory);
    }

    public void delete(int id) {
        this.inventoryDAO.delete(id);
    }

    public List<InventoryVO> list() {
        return this.inventoryDAO.list();
    }

    public void update(Inventory inventory) {
        this.inventoryDAO.update(inventory);
    }
    
    public List<InventoryVO> findByDescription(String description) {
		return this.inventoryDAO.findByDescription(description);
    }

}
