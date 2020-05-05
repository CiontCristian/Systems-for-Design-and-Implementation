package controller;

import converter.PurchasedConverter;
import dto.PurchasedDTO;
import dto.PurchasedsDTO;
import lab10.core.service.PurchasedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PurchasedController {
    @Autowired
    private PurchasedService purchasedService;

    @Autowired
    private PurchasedConverter purchasedConverter;

    @RequestMapping(value = "/tran", method = RequestMethod.GET)
    PurchasedsDTO getPurchases(){
        return new PurchasedsDTO(purchasedConverter.convertModelsToDtos(purchasedService.getAll()));
    }

    @RequestMapping(value = "/tran", method = RequestMethod.POST)
    PurchasedDTO savePurchased(@RequestBody PurchasedDTO purchasedDTO){
        return purchasedConverter.convertModelToDto(purchasedService.save(purchasedConverter.convertDtoToModel(purchasedDTO)));
    }
}
