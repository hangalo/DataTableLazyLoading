/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package md;

import facade.ItemFacade;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import lazy.LazyItemDataModel;
import modelo.Item;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author praveen
 */
@ManagedBean
@SessionScoped
public class ItemBeam  implements Serializable{

    @EJB
    private ItemFacade itemFacade;

    private LazyDataModel<Item> lazyModel;
    private Item selectedItem;

    public ItemBeam() {
       
    }
    
    @PostConstruct
    public void init(){
    
    lazyModel = new LazyItemDataModel(itemFacade.findAll());
    }

    public LazyDataModel<Item> getLazyModel() {
        return lazyModel;
    }
   

    public Item getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(Item selectedItem) {
        this.selectedItem = selectedItem;
    }

    public void onRowSelect(SelectEvent event){
    FacesMessage msg = new FacesMessage("Item Selected", ((Item)event.getObject()).getId().toString());
    FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
}
