package br.com.wellingtoncosta.bankslipsapi.web;

import br.com.wellingtoncosta.bankslipsapi.data.json.BankSlipDetailsJson;
import br.com.wellingtoncosta.bankslipsapi.data.json.BankSlipJson;
import br.com.wellingtoncosta.bankslipsapi.domain.model.BankSlip;
import br.com.wellingtoncosta.bankslipsapi.service.BankSlipService;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * @author Wellington Costa on 17/11/18
 */
@RestController
@RequestMapping("rest/bankslips")
public class BankSlipController {

    private BankSlipService service;

    @Inject @Lazy public void setService(BankSlipService service) {
        this.service = service;
    }

    @GetMapping(produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<BankSlipJson>> listAll() {
        List<BankSlip> bankSlips = service.listAll();
        if(bankSlips.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(
                    bankSlips.stream().map(BankSlip::toJson).collect(toList())
            );
        }
    }

    @GetMapping(value= "/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<BankSlipDetailsJson> findById(@PathVariable("id") UUID uuid) {
        BankSlip bankSlip = service.findById(uuid);
        if(isNull(bankSlip)) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(bankSlip.toDetailedJson());
        }
    }

}
